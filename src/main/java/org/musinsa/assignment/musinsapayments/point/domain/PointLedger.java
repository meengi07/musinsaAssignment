package org.musinsa.assignment.musinsapayments.point.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "point_ledger")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointLedger extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "point_id", nullable = false)
    private Long pointId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "point_type", nullable = false)
    private PointType pointType;

    public PointLedger(Long userId, Long pointId, Long orderId, Long amount, PointType pointType) {
        this.userId = userId;
        this.pointId = pointId;
        this.orderId = orderId;
        this.amount = amount;
        this.pointType = pointType;
    }

    public PointLedger(Long userId, Long pointId,Long amount, PointType pointType) {
        this.userId = userId;
        this.pointId = pointId;
        this.amount = amount;
        this.pointType = pointType;
    }

}
