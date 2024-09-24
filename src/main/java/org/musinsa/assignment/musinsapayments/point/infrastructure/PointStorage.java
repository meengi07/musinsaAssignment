package org.musinsa.assignment.musinsapayments.point.infrastructure;

import lombok.RequiredArgsConstructor;
import org.musinsa.assignment.musinsapayments.point.domain.Point;
import org.musinsa.assignment.musinsapayments.point.domain.PointPolicy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointStorage {

    private final PointRepository pointRepository;
    private final PointLedgerRepository pointLedgerRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PointPolicyRepository pointPolicyRepository;

    public void save(Point point) {
        pointRepository.save(point);
    }

    public Point findPoint(Long id) {
        return pointRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Point not found"));
    }

    public PointPolicy findPointPolicy(Long userId) {
        return pointPolicyRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("PointPolicy not found"));
    }

    public void delete(Point point) {
        pointRepository.delete(point);
    }

}
