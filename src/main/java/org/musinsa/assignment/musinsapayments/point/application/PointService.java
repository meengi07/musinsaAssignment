package org.musinsa.assignment.musinsapayments.point.application;

import lombok.RequiredArgsConstructor;
import org.musinsa.assignment.musinsapayments.point.domain.PointPolicy;
import org.musinsa.assignment.musinsapayments.point.infrastructure.PointStorage;
import org.musinsa.assignment.musinsapayments.point.presentation.PointEarnRequest;
import org.musinsa.assignment.musinsapayments.point.presentation.PointEarnResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointStorage pointStorage;

    public PointEarnResponse earnPoint(PointEarnRequest request) {
        PointPolicy pointPolicy = pointStorage.findPointPolicy(request.userId());

        return null;
    }
}
