package org.musinsa.assignment.musinsapayments.point.infrastructure;

import org.musinsa.assignment.musinsapayments.point.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
