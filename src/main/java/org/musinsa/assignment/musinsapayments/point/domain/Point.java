package org.musinsa.assignment.musinsapayments.point.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.musinsa.assignment.musinsapayments.commons.model.BaseEntity;

@Getter
@Entity
@Table(name = "point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "point_amount", nullable = false)
    private Long amount;

    @Column(name = "expire_date", nullable = false)
    private LocalDate expireDate;

    @Column(name = "is_manually", nullable = false)
    private boolean isManually;

    @Column(name = "remain_amount", nullable = false)
    private Long remainAmount;

    public void decreaseRemainAmount(Long amount) {
        this.remainAmount -= amount;
    }

    public void increaseRemainAmount(Long amount) {
        this.remainAmount += amount;
    }

    public Point(Long userId, Long amount, LocalDate expireDate, boolean isManually) {
        this.userId = userId;
        this.amount = amount;
        this.expireDate = expireDate == null ? LocalDate.now().plusDays(30) : expireDate;
        this.isManually = isManually;
        this.remainAmount = amount;
    }

    public Point(Long userId, Long amount, boolean isManually) {
        this.userId = userId;
        this.amount = amount;
        this.expireDate = LocalDate.now().plusDays(30);
        this.isManually = isManually;
        this.remainAmount = amount;
    }

}
