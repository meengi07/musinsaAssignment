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

@Getter
@Entity
@Table(name = "point_policy")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_policy_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "limit_point", nullable = false)
    private Long limitPoint;

    @Column(name = "min_earn_point", nullable = false)
    private Long minEarnPoint;

    @Column(name = "max_earn_point", nullable = false)
    private Long maxEarnPoint;

    public PointPolicy(Long userId, Long limitPoint) {
        this.userId = userId;
        this.limitPoint = limitPoint == null ? 1000000L : limitPoint;
        this.minEarnPoint = 1L;
        this.maxEarnPoint = 100000L;
    }

    public PointPolicy(Long userId) {
        this.userId = userId;
        this.limitPoint = 1000000L;
        this.minEarnPoint = 1L;
        this.maxEarnPoint = 100000L;
    }

    public PointPolicy(Long userId, Long limitPoint, Long minEarnPoint, Long maxEarnPoint) {
        this.userId = userId;
        this.limitPoint = limitPoint == null ? 1000000L : limitPoint;
        this.minEarnPoint = minEarnPoint == null ? 1L : minEarnPoint;
        this.maxEarnPoint = maxEarnPoint == null ? 100000L : maxEarnPoint;
    }

}
