package org.musinsa.assignment.musinsapayments.point.infrastructure;

import org.musinsa.assignment.musinsapayments.point.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
