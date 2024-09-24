package org.musinsa.assignment.musinsapayments.point.infrastructure;

import org.musinsa.assignment.musinsapayments.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {

}
