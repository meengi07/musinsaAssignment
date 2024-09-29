package org.musinsa.assignment.musinsapayments.point.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.musinsa.assignment.musinsapayments.commons.exceptions.ExceptionsType;
import org.musinsa.assignment.musinsapayments.commons.exceptions.PointException;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    @Mock
    private PointReader pointReader;

    @Mock
    private PointStore pointStore;

    @InjectMocks
    private PointService pointService;

    @Test
    void 포인트_적립_테스트() {
        PointPolicy pointPolicy = new PointPolicy(1L, 1000L, 10L, 100L);
        Point point = new Point(1L, 50L, false);
        PointBalance pointBalance = new PointBalance(1L, 200L);

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);
        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);
        when(pointStore.savePoint(any(Point.class))).thenAnswer(invocation -> {
            Point savedPoint = invocation.getArgument(0);
            setPrivateField(savedPoint, "id", 1L);
            return savedPoint;
        });
        pointService.savePoint(point);

        verify(pointStore).savePoint(point);
        verify(pointStore).savePointBalance(pointBalance);
        verify(pointStore).savePointLedger(any(PointLedger.class));
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    void 최소_적립가능_포인트_미만_실패_테스트() {
        PointPolicy pointPolicy = new PointPolicy(1L, 1000L, 10L, 100L);
        Point point = new Point(1L, 5L, false);

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);

        assertThrows(PointException.class, () -> pointService.savePoint(point));
    }

    @Test
    void 최대_적립가능_포인트_초과_실패_테스트() {
        PointPolicy pointPolicy = new PointPolicy(1L, 1000L, 10L, 100L);
        Point point = new Point(1L, 200L, false);

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);

        assertThrows(PointException.class, () -> pointService.savePoint(point));
    }

    @Test
    void 최대_보유_가능_포인트_초과_실패_테스트() {
        PointPolicy pointPolicy = new PointPolicy(1L, 1000L, 10L, 100L);
        Point point = new Point(1L, 50L, false);
        PointBalance pointBalance = new PointBalance(1L, 980L);

        when(pointReader.findPointPolicy(1L)).thenReturn(pointPolicy);
        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);

        assertThrows(PointException.class, () -> pointService.savePoint(point));

        verify(pointReader).findPointPolicy(1L);
        verify(pointReader).findPointBalanceByUserId(1L);
    }

    @Test
    void 포인트_적립_취소_테스트() {
        Point point = new Point(1L, 50L, false);
        PointBalance pointBalance = new PointBalance(1L, 200L);

        when(pointReader.findPoint(1L)).thenReturn(point);
        when(pointReader.findPointBalanceByUserId(1L)).thenReturn(pointBalance);
        pointService.cancelPoint(1L);

        verify(pointStore).savePoint(point);
        verify(pointStore).savePointBalance(pointBalance);
        verify(pointStore).savePointLedger(any(PointLedger.class));
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

        verify(pointStore).saveAllPoint(userPoints);
        verify(pointStore).saveAllPointLedger(anyList());
        verify(pointStore).savePointBalance(pointBalance);
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

        verify(pointStore).saveAllPoint(anyList());
        verify(pointStore).saveAllPointLedger(anyList());
        verify(pointStore).savePointBalance(pointBalance);
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