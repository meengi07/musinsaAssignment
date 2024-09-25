package org.musinsa.assignment.musinsapayments.point.presentation;

import lombok.RequiredArgsConstructor;
import org.musinsa.assignment.musinsapayments.point.application.PointFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointController {

    private final PointFacade pointFacade;

    @PostMapping("/earn")
    public ResponseEntity<PointEarnResponse> earnPoint(@RequestBody PointEarnRequest request) {
        PointEarnResponse pointEarnResponse = pointFacade.earnPoint(request);
        return ResponseEntity.ok(pointEarnResponse);
    }


}
