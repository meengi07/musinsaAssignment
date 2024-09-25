package org.musinsa.assignment.musinsapayments.point.application;

import lombok.RequiredArgsConstructor;
import org.musinsa.assignment.musinsapayments.point.domain.Point;
import org.musinsa.assignment.musinsapayments.point.domain.PointUseCase;
import org.musinsa.assignment.musinsapayments.point.presentation.PointEarnRequest;
import org.musinsa.assignment.musinsapayments.point.presentation.PointEarnResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointFacade {

    private final PointUseCase pointUseCase;

    public PointEarnResponse earnPoint(PointEarnRequest request) {
        Point point = PointEarnRequest.toEntity(request);
        Point savedPoint = pointUseCase.savePoint(point);
        return new PointEarnResponse(savedPoint.getId(), savedPoint.getRemainAmount());
    }
}
