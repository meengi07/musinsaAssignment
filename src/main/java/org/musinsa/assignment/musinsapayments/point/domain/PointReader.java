package org.musinsa.assignment.musinsapayments.point.domain;

public interface PointReader {

    PointBalance findPointBalanceByUserId(Long userId);

    PointPolicy findPointPolicy(Long userId);

}
