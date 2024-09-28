package org.musinsa.assignment.musinsapayments.point.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.musinsa.assignment.musinsapayments.commons.exceptions.ExceptionsType;
import org.musinsa.assignment.musinsapayments.commons.exceptions.PointException;

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
        PointPolicy pointPolicy = new PointPolicy(1L, 1000L, 10L, 100L);
        Point point = new Point(1L, 50L, false);
        PointBalance pointBalance = new PointBalance(1L, 200L);

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);
        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);
        pointService.savePoint(point);

        Mockito.verify(pointStore).savePoint(point);
        Mockito.verify(pointStore).savePointBalance(pointBalance);
        Mockito.verify(pointStore).savePointLedger(Mockito.any(PointLedger.class));
    }

    @Test
    void 최소_적립가능_포인트_미만_실패_테스트() {
        PointPolicy pointPolicy = new PointPolicy(1L, 1000L, 10L, 100L);
        Point point = new Point(1L, 5L, false);

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);

        assertThrows(IllegalArgumentException.class, () -> pointService.savePoint(point));
    }

    @Test
    void 최대_적립가능_포인트_초과_실패_테스트() {
        PointPolicy pointPolicy = new PointPolicy(1L, 1000L, 10L, 100L);
        Point point = new Point(1L, 200L, false);

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);

        assertThrows(IllegalArgumentException.class, () -> pointService.savePoint(point));
    }

    @Test
    void 최대_보유_가능_포인트_초과_실패_테스트() {
        PointPolicy pointPolicy = new PointPolicy(1L, 1000L, 10L, 100L);
        Point point = new Point(1L, 500L, false);
        PointBalance pointBalance = new PointBalance(1L, 800L);

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);
        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);

        assertThrows(IllegalArgumentException.class, () -> pointService.savePoint(point));
    }

    @Test
    void 포인트_적립_취소_테스트() {
        Point point = new Point(1L, 50L, false);
        PointBalance pointBalance = new PointBalance(1L, 200L);

        when(pointReader.findPoint(1L)).thenReturn(point);
        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);
        pointService.cancelPoint(1L);

        Mockito.verify(pointStore).savePoint(point);
        Mockito.verify(pointStore).savePointBalance(pointBalance);
        Mockito.verify(pointStore).savePointLedger(Mockito.any(PointLedger.class));
    }

    @Test
    void 포인트_찾기_실패_테스트() {
        when(pointReader.findPoint(1L)).thenThrow(new PointException(ExceptionsType.POINT_NOT_FOUND));

        assertThrows(PointException.class, () -> pointService.cancelPoint(1L));
    }

    @Test
    void 포인트_사용_테스트() {
        PointBalance pointBalance = new PointBalance(1L, 200L);
        Point point = new Point(1L, 50L, false);
        List<Point> userPoints = List.of(point);

        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);
        when(pointReader.findAllPointByUserId(1L)).thenReturn(userPoints);
        pointService.usePoint(1L, 1L, 50L);

        Mockito.verify(pointStore).saveAllPoint(userPoints);
        Mockito.verify(pointStore).saveAllPointLedger(Mockito.anyList());
        Mockito.verify(pointStore).savePointBalance(pointBalance);
    }

    @Test
    void 포인트_사용_실패_잔액부족_테스트() {
        PointBalance pointBalance = new PointBalance(1L, 20L);

        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);

        assertThrows(PointException.class, () -> pointService.usePoint(1L, 1L, 50L));
    }

    @Test
    void 포인트_사용_취소_테스트() {
        PointPolicy pointPolicy = new PointPolicy(1L, 1000L, 10L, 100L);
        PointBalance pointBalance = new PointBalance(1L, 200L);
        PointLedger pointLedger = new PointLedger(1L, 1L, 1L, 50L, PointType.USE);
        List<PointLedger> pointLedgers = List.of(pointLedger);
        Point point = new Point(1L, 50L, false);

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);
        when(pointReader.findPointLedgerByOrderId(1L, 1L)).thenReturn(pointLedgers);
        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);
        when(pointReader.findPoint(1L)).thenReturn(point);
        pointService.cancelUsedPoint(1L, 1L, 50L);

        Mockito.verify(pointStore).saveAllPoint(Mockito.anyList());
        Mockito.verify(pointStore).saveAllPointLedger(Mockito.anyList());
        Mockito.verify(pointStore).savePointBalance(pointBalance);
    }

    @Test
    void 포인트_사용_취소_최대보유_가능_한도_초과_실퍠_테스트() {
        PointPolicy pointPolicy = new PointPolicy(1L, 1000L, 10L, 100L);
        PointBalance pointBalance = new PointBalance(1L, 950L);

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);
        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);

        assertThrows(PointException.class, () -> pointService.cancelUsedPoint(1L, 1L, 100L));
    }

}