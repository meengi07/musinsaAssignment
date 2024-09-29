package org.musinsa.assignment.musinsapayments.point.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.musinsa.assignment.musinsapayments.point.application.PointFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
public class PointController implements PointControllerDoc {

    private final PointFacade pointFacade;

    @PostMapping("/earn")
    public ResponseEntity<PointEarnResponse> earnPoint(@RequestBody PointEarnRequest earnRequest) {
        log.info("포인트 적립 : {}", earnRequest);
        PointEarnResponse pointEarnResponse = pointFacade.earnPoint(earnRequest);
        log.info("포인트 적립 완료 : {}", pointEarnResponse);
        return ResponseEntity.ok(pointEarnResponse);
    }

    @PostMapping("/earn/{pointId}/cancel")
    public ResponseEntity<PointEarnResponse> cancelPoint(@PathVariable Long pointId) {
        log.info("포인트 적립 취소, pointId: {}", pointId);
        PointEarnResponse pointEarnResponse = pointFacade.cancelPoint(pointId);
        log.info("포인트 적립 취소 완료, pointId: {}", pointId);
        return ResponseEntity.ok(pointEarnResponse);
    }

    @PostMapping("/use")
    public ResponseEntity<PointUseResponse> usePoint(@RequestBody PointUseRequest useRequest) {
        log.info("포인트 사용 : {}", useRequest);
        PointUseResponse pointUseResponse = pointFacade.usePoint(useRequest);
        log.info("포인트 사용 완료 : {}", pointUseResponse);
        return ResponseEntity.ok(pointUseResponse);
    }

    @PostMapping("/use/cancel")
    public ResponseEntity<PointUseResponse> cancelUsedPoint(@RequestBody PointUseRequest useRequest) {
        log.info("포인트 사용 취소 : {}", useRequest);
        PointUseResponse pointUseResponse = pointFacade.cancelUsedPoint(useRequest);
        log.info("포인트 사용 취소 완료 : {}", pointUseResponse);
        return ResponseEntity.ok(pointUseResponse);
    }

}
