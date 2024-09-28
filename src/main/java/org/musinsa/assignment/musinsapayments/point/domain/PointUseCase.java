package org.musinsa.assignment.musinsapayments.point.domain;

public interface PointUseCase {

    Point savePoint(Point point);

    Point cancelPoint(Long pointId);

    Long usePoint(Long userId, Long orderId, Long amount);

    Long cancelUsedPoint(Long userId, Long orderId, Long amount);
}
