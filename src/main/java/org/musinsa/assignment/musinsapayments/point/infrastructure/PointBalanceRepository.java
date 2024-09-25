package org.musinsa.assignment.musinsapayments.point.infrastructure;

import java.util.Optional;
import org.musinsa.assignment.musinsapayments.point.domain.PointBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointBalanceRepository extends JpaRepository<PointBalance, Long> {
    Optional<PointBalance> findByUserId(Long userId);

}
