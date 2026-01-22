package com.intellipay.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "plan_usage_metrics",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"plan_id", "usage_metric_id"})
        }
)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanUsageMetric {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_metric_id")
    private UsageMetric usageMetric;

    @Column(nullable = false)
    private Long freeLimit;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal pricePerUnit;
}
