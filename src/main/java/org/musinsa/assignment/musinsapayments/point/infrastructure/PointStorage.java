package org.musinsa.assignment.musinsapayments.point.infrastructure;

import lombok.RequiredArgsConstructor;
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

    public PointBalance findPointBalanceByUserId(Long userId) {
        return pointBalanceRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("Point not found"));
    }

    public PointPolicy findPointPolicy(Long userId) {
        return pointPolicyRepository.findByUserId(userId)
            .orElseGet(() -> PointPolicy.builder()
                .userId(userId)
                .limitPoint(1000000L)
                .minEarnPoint(1L)
                .maxEarnPoint(100000L)
                .build()
            );
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
}
