package org.musinsa.assignment.musinsapayments.point.domain;

import java.util.List;

public interface PointReader {

    Point findPoint(Long pointId);

    List<Point> findAllPointByUserId(Long userId);

    PointBalance findPointBalanceByUserId(Long userId);

    PointPolicy findPointPolicy(Long userId);

    List<PointLedger> findPointLedgerByOrderId(Long userId, Long orderId);

}
