package org.musinsa.assignment.musinsapayments.point.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.musinsa.assignment.musinsapayments.commons.exceptions.ExceptionsType;
import org.musinsa.assignment.musinsapayments.commons.exceptions.PointException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService implements PointUseCase {

    private final PointReader pointReader;
    private final PointStore pointStore;

    @Override
    @Transactional
    public Point savePoint(Point earnPoint) {
        PointPolicy pointPolicy = pointReader.findPointPolicy(earnPoint.getUserId());
        validateEarnAmount(pointPolicy, earnPoint.getAmount());

        PointBalance pointBalance = pointReader.findPointBalanceByUserId(earnPoint.getUserId());
        validateTotalPointLimit(pointPolicy, earnPoint.getAmount(), pointBalance.getTotalAmount());
        pointBalance.increaseAmount(earnPoint.getAmount() + pointBalance.getTotalAmount());
        pointStore.savePointBalance(pointBalance);

        Point savePoint = pointStore.savePoint(earnPoint);
        PointLedger pointLedger = new PointLedger(savePoint.getUserId(), savePoint.getId(), savePoint.getAmount(), PointType.EARN);
        pointStore.savePointLedger(pointLedger);

        return earnPoint;
    }

    @Override
    @Transactional
    public Point cancelPoint(Long pointId) {
        Point point = pointReader.findPoint(pointId);
        validateCancelEarnAmount(point);
        point.decreaseRemainAmount(point.getAmount());
        pointStore.savePoint(point);

        PointBalance pointBalance = pointReader.findPointBalanceByUserId(point.getUserId());
        validateCancelTotalAmount(pointBalance, point);
        pointBalance.decreaseAmount(point.getAmount());
        pointStore.savePointBalance(pointBalance);

        PointLedger pointLedger = new PointLedger(point.getUserId(), point.getId(), point.getAmount(), PointType.EARN_CANCEL);
        pointStore.savePointLedger(pointLedger);

        return point;
    }

    @Override
    @Transactional
    public Long usePoint(Long userId, Long orderId, Long amount) {
        PointBalance pointBalance = pointReader.findPointBalanceByUserId(userId);
        validateUseAmount(amount, pointBalance.getTotalAmount());

        List<Point> userPoints = pointReader.findAllPointByUserId(userId);
        Long remainPoint = amount;
        List<Point> usedPoints = new ArrayList<>();
        List<PointLedger> usedPointLedgers = new ArrayList<>();
        for (Point point : userPoints) {
            if (remainPoint <= 0) break;

            Long pointsToUse = Math.min(point.getRemainAmount(), remainPoint);
            point.decreaseRemainAmount(pointsToUse);
            remainPoint -= pointsToUse;
            usedPoints.add(point);

            PointLedger pointLedger = new PointLedger(point.getUserId(), point.getId(), orderId, pointsToUse, PointType.USE);
            usedPointLedgers.add(pointLedger);
        }

        pointStore.saveAllPoint(usedPoints);
        pointStore.saveAllPointLedger(usedPointLedgers);

        pointBalance.decreaseAmount(amount);
        pointStore.savePointBalance(pointBalance);

        return pointBalance.getTotalAmount();
    }

    @Override
    @Transactional
    public Long cancelUsedPoint(Long userId, Long orderId, Long amount) {
        PointPolicy pointPolicy = pointReader.findPointPolicy(userId);
        validateEarnAmount(pointPolicy, amount);

        List<PointLedger> pointLedgers = pointReader.findPointLedgerByOrderId(userId, orderId);
        PointBalance pointBalance = pointReader.findPointBalanceByUserId(userId);
        validateTotalPointLimit(pointPolicy, amount, pointBalance.getTotalAmount());

        Long cancelPoints = amount;
        List<Point> updatedPoints = new ArrayList<>();
        List<PointLedger> updatedPointLedgers = new ArrayList<>();

        for (PointLedger pointLedger : pointLedgers) {
            if (cancelPoints <= 0) break;

            Point point = pointReader.findPoint(pointLedger.getPointId());
            Long pointsToCancel = Math.min(pointLedger.getAmount(), cancelPoints);
            cancelPoints -= pointsToCancel;

            if (point.getExpireDate().isBefore(LocalDate.now())) {
                Point newPoint = new Point(point.getUserId(), point.getAmount(), point.isManually());
                updatedPoints.add(newPoint);

                PointLedger updatedPointLedger = new PointLedger(newPoint.getUserId(), newPoint.getId(), orderId, pointsToCancel, PointType.USE_CANCEL);
                updatedPointLedgers.add(updatedPointLedger);
            } else {
                point.increaseRemainAmount(pointLedger.getAmount());
                updatedPoints.add(point);

                PointLedger updatedPointLedger = new PointLedger(point.getUserId(), point.getId(), orderId, pointsToCancel, PointType.USE_CANCEL);
                updatedPointLedgers.add(updatedPointLedger);
            }
        }

        pointStore.saveAllPoint(updatedPoints);
        pointStore.saveAllPointLedger(updatedPointLedgers);

        pointBalance.increaseAmount(amount);
        pointStore.savePointBalance(pointBalance);

        return pointBalance.getTotalAmount();
    }

    private void validateUseAmount(Long amount, Long remainPoint) {
        if (amount > remainPoint) {
            throw new PointException(ExceptionsType.POINT_USE_EXCEED);
        }
    }

    private void validateCancelTotalAmount(PointBalance pointBalance, Point point) {
        if (pointBalance.getTotalAmount() < point.getAmount()) {
            throw new PointException(ExceptionsType.POINT_CANCEL_EXCEED);
        }
    }

    private void validateCancelEarnAmount(Point point) {
        if (point.getAmount() != point.getRemainAmount()) {
            throw new PointException(ExceptionsType.POINT_ALREADY_USED);
        }
    }

    private void validateEarnAmount(PointPolicy pointPolicy, Long amount) {
        if (amount < pointPolicy.getMinEarnPoint()) {
            throw new PointException(ExceptionsType.POINT_EARN_MINIMUM);
        }

        if (amount > pointPolicy.getMaxEarnPoint()) {
            throw new PointException(ExceptionsType.POINT_EARN_MAXIMUM);
        }
    }

    private void validateTotalPointLimit(PointPolicy pointPolicy, Long amount, Long remainPoint) {
        if (amount + remainPoint > pointPolicy.getLimitPoint()) {
            throw new PointException(ExceptionsType.POINT_MAXIMUM);
        }
    }

}
