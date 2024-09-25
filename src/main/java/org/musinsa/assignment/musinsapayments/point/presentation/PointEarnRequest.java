package org.musinsa.assignment.musinsapayments.point.presentation;

import java.time.LocalDate;
import org.musinsa.assignment.musinsapayments.point.domain.Point;

public record PointEarnRequest(
    Long userId,
    Long amount,
    boolean manually,
    LocalDate expireDate
) {

    public static Point toEntity(PointEarnRequest request) {
        return Point.builder()
            .userId(request.userId())
            .amount(request.amount())
            .isManually(request.manually())
            .expireDate(request.expireDate())
            .build();
    }

}
