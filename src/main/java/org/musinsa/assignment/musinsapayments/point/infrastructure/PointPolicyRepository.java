package org.musinsa.assignment.musinsapayments.point.infrastructure;

import java.util.Optional;
import org.musinsa.assignment.musinsapayments.point.domain.PointPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointPolicyRepository extends JpaRepository<PointPolicy, Long> {

    Optional<PointPolicy> findByUserId(Long userId);

}
