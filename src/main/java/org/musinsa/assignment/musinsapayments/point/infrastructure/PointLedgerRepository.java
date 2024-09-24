package org.musinsa.assignment.musinsapayments.point.infrastructure;

import org.musinsa.assignment.musinsapayments.point.domain.PointLedger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLedgerRepository extends JpaRepository<PointLedger, Long> {

}
