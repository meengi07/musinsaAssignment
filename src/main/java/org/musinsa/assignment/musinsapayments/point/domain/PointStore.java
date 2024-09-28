package org.musinsa.assignment.musinsapayments.point.domain;

import java.util.List;

public interface PointStore {

    Point savePoint(Point point);

    PointBalance savePointBalance(PointBalance pointBalance);

    PointLedger savePointLedger(PointLedger pointLedger);

    void saveAllPoint(List<Point> updatedPoints);

    void saveAllPointLedger(List<PointLedger> pointLedgers);

}
