package org.musinsa.assignment.musinsapayments.point.domain;

public interface PointStore {

    Point savePoint(Point point);

    PointBalance savePointBalance(PointBalance pointBalance);

    PointLedger savePointLedger(PointLedger pointLedger);

}
