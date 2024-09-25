package org.musinsa.assignment.musinsapayments.point.domain;

import lombok.RequiredArgsConstructor;
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

        pointStore.savePoint(earnPoint);

        pointBalance.increaseAmount(earnPoint.getAmount() + pointBalance.getTotalAmount());
        pointStore.savePointBalance(pointBalance);

        PointLedger pointLeder = PointLedger.builder()
            .userId(earnPoint.getUserId())
            .pointId(earnPoint.getId())
            .amount(earnPoint.getAmount())
            .pointType(PointType.EARN)
            .build();
        pointStore.savePointLedger(pointLeder);

        return earnPoint;
    }

    private void validateEarnAmount(PointPolicy pointPolicy, Long amount) {
        if (amount < pointPolicy.getMinEarnPoint()) {
            throw new IllegalArgumentException("최소 적립가능 포인트보다 적은 포인트입니다.");
        }

        if (amount > pointPolicy.getMaxEarnPoint()) {
            throw new IllegalArgumentException("최대 적립가능 포인트보다 많은 포인트입니다.");
        }
    }

    private void validateTotalPointLimit(PointPolicy pointPolicy, Long amount, Long remainPoint) {
        if (amount + remainPoint > pointPolicy.getLimitPoint()) {
            throw new IllegalArgumentException("최대 보유 가능 포인트를 초과하였습니다.");
        }
    }

}
