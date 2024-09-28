package org.musinsa.assignment.musinsapayments.point.infrastructure;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.musinsa.assignment.musinsapayments.commons.exceptions.ExceptionsType;
import org.musinsa.assignment.musinsapayments.commons.exceptions.PointException;
import org.musinsa.assignment.musinsapayments.point.domain.Point;
import org.musinsa.assignment.musinsapayments.point.domain.PointBalance;
import org.musinsa.assignment.musinsapayments.point.domain.PointLedger;
import org.musinsa.assignment.musinsapayments.point.domain.PointPolicy;
import org.musinsa.assignment.musinsapayments.point.domain.PointReader;
import org.musinsa.assignment.musinsapayments.point.domain.PointStore;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointStorage implements PointReader, PointStore {

    private final PointRepository pointRepository;
    private final PointLedgerRepository pointLedgerRepository;
    private final PointPolicyRepository pointPolicyRepository;
    private final PointBalanceRepository pointBalanceRepository;

    @Override
    public Point findPoint(Long pointId) {
        return pointRepository.findById(pointId)
            .orElseThrow(() -> new PointException(ExceptionsType.POINT_NOT_FOUND));
    }

    @Override
    public List<Point> findAllPointByUserId(Long userId) {
        List<Point> userPoints = pointRepository.findAllByUserId(userId);
        if (userPoints.isEmpty()) {
            throw new PointException(ExceptionsType.POINT_NOT_FOUND);
        }
        return userPoints;
    }

    @Override
    public PointBalance findPointBalanceByUserId(Long userId) {
        return pointBalanceRepository.findByUserId(userId)
            .orElseGet(() -> new PointBalance(userId));
    }

    @Override
    public PointPolicy findPointPolicy(Long userId) {
        return pointPolicyRepository.findByUserId(userId)
            .orElseGet(() -> {
                PointPolicy pointPolicy = new PointPolicy(userId);
                pointPolicyRepository.save(pointPolicy);
                return pointPolicy;
            });
    }

    @Override
    public List<PointLedger> findPointLedgerByOrderId(Long userId, Long orderId) {
        List<PointLedger> pointLedgers = pointLedgerRepository.findAllByUserIdAndOrderId(userId, orderId);
        if (pointLedgers.isEmpty()) {
            throw new PointException(ExceptionsType.POINT_LEDGER_NOT_FOUND);
        }
        return pointLedgers;
    }

    @Override
    public Point savePoint(Point point) {
        return pointRepository.save(point);
    }

    @Override
    public PointBalance savePointBalance(PointBalance pointBalance) {
        return pointBalanceRepository.save(pointBalance);
    }

    @Override
    public PointLedger savePointLedger(PointLedger pointLedger) {
        return pointLedgerRepository.save(pointLedger);
    }

    @Override
    public void saveAllPoint(List<Point> updatedPoints) {
        pointRepository.saveAll(updatedPoints);
    }

    @Override
    public void saveAllPointLedger(List<PointLedger> pointLedgers) {
        pointLedgerRepository.saveAll(pointLedgers);
    }
}
