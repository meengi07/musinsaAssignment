package org.musinsa.assignment.musinsapayments.point.infrastructure;

import java.util.List;
import org.musinsa.assignment.musinsapayments.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointRepository extends JpaRepository<Point, Long> {

    @Query(
        "SELECT p "
        + "FROM Point p "
        + "WHERE p.userId = :userId "
        + "AND p.expireDate >= CURRENT_DATE "
        + "AND p.remainAmount > 0 "
        + "ORDER BY p.isManually DESC, p.expireDate ASC"
    )
    List<Point> findAllByUserId(Long userId);

}
