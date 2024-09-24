package org.musinsa.assignment.musinsapayments.point.presentation;

import java.time.LocalDate;

public record PointEarnRequest(
    Long userId,
    Long amount,
    boolean manually,
    LocalDate expireDate
) {

}
