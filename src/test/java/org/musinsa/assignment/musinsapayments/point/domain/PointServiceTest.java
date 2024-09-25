package org.musinsa.assignment.musinsapayments.point.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PointServiceTest {

    private PointReader pointReader;
    private PointStore pointStore;
    private PointService pointService;

    @BeforeEach
    void setUp() {
        pointReader = Mockito.mock(PointReader.class);
        pointStore = Mockito.mock(PointStore.class);
        pointService = new PointService(pointReader, pointStore);
    }

    @Test
    void 포인트_적립_테스트() {
        PointPolicy pointPolicy = PointPolicy.builder()
            .userId(1L)
            .minEarnPoint(10L)
            .maxEarnPoint(100L)
            .limitPoint(1000L)
            .build();

        Point point = Point.builder()
            .id(1L)
            .userId(1L)
            .amount(50L)
            .build();

        PointBalance pointBalance = PointBalance.builder()
            .userId(1L)
            .totalAmount(200L)
            .build();

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);
        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);

        pointService.savePoint(point);

        Mockito.verify(pointStore).savePoint(point);
        Mockito.verify(pointStore).savePointBalance(pointBalance);
        Mockito.verify(pointStore).savePointLedger(Mockito.any(PointLedger.class));
    }

    @Test
    void 최소_적립가능_포인트_미만_실패_테스트() {
        PointPolicy pointPolicy = PointPolicy.builder()
            .userId(1L)
            .minEarnPoint(10L)
            .maxEarnPoint(100L)
            .limitPoint(1000L)
            .build();

        Point point = Point.builder()
            .id(1L)
            .userId(1L)
            .amount(5L)
            .build();

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);

        assertThrows(IllegalArgumentException.class, () -> pointService.savePoint(point));
    }

    @Test
    void 최대_적립가능_포인트_초과_실패_테스트() {
        PointPolicy pointPolicy = PointPolicy.builder()
            .userId(1L)
            .minEarnPoint(10L)
            .maxEarnPoint(100L)
            .limitPoint(1000L)
            .build();

        Point point = Point.builder()
            .id(1L)
            .userId(1L)
            .amount(200L)
            .build();

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);

        assertThrows(IllegalArgumentException.class, () -> pointService.savePoint(point));
    }

    @Test
    void 최대_보유_가능_포인트_초과_실패_테스트() {
        PointPolicy pointPolicy = PointPolicy.builder()
            .userId(1L)
            .minEarnPoint(10L)
            .maxEarnPoint(100L)
            .limitPoint(1000L)
            .build();

        Point point = Point.builder()
            .id(1L)
            .userId(1L)
            .amount(500L)
            .build();

        PointBalance pointBalance = PointBalance.builder()
            .userId(1L)
            .totalAmount(800L)
            .build();

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);
        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);

        assertThrows(IllegalArgumentException.class, () -> pointService.savePoint(point));
    }

}