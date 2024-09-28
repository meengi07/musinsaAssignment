package org.musinsa.assignment.musinsapayments.point.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.musinsa.assignment.musinsapayments.commons.model.BaseEntity;

@Getter
@Entity
@Table(name = "point_balance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointBalance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "total_amount", nullable = false)
    private Long totalAmount;

    public void increaseAmount(Long amount) {
        this.totalAmount += amount;
    }

    public void decreaseAmount(Long amount) {
        this.totalAmount -= amount;
    }

    public PointBalance(Long userId, Long totalAmount) {
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    public PointBalance(Long userId) {
        this.userId = userId;
        this.totalAmount = 0L;
    }

}
