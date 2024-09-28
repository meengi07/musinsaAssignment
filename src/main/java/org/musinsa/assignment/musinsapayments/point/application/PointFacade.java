package org.musinsa.assignment.musinsapayments.point.application;

import lombok.RequiredArgsConstructor;
import org.musinsa.assignment.musinsapayments.point.domain.Point;
import org.musinsa.assignment.musinsapayments.point.domain.PointUseCase;
import org.musinsa.assignment.musinsapayments.point.presentation.PointEarnRequest;
import org.musinsa.assignment.musinsapayments.point.presentation.PointEarnResponse;
import org.musinsa.assignment.musinsapayments.point.presentation.PointUseRequest;
import org.musinsa.assignment.musinsapayments.point.presentation.PointUseResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointFacade {

    private final PointUseCase pointUseCase;

    public PointEarnResponse earnPoint(PointEarnRequest earnRequest) {
        Point point = new Point(earnRequest.userId(), earnRequest.amount(), earnRequest.expireDate(), earnRequest.manually());
        Point savedPoint = pointUseCase.savePoint(point);
        return new PointEarnResponse(savedPoint.getId(), savedPoint.getRemainAmount());
    }

    public PointEarnResponse cancelPoint(Long pointId) {
        Point canceledPoint = pointUseCase.cancelPoint(pointId);
        return new PointEarnResponse(canceledPoint.getId(), canceledPoint.getRemainAmount());
    }

    public PointUseResponse usePoint(PointUseRequest useRequest) {
        Long remainPoint = pointUseCase.usePoint(useRequest.userId(), useRequest.orderId(), useRequest.amount());
        return new PointUseResponse(remainPoint);
    }

    public PointUseResponse cancelUsedPoint(PointUseRequest useRequest) {
        Long remainPoint = pointUseCase.cancelUsedPoint(useRequest.userId(), useRequest.orderId(), useRequest.amount());
        return new PointUseResponse(remainPoint);
    }
}
