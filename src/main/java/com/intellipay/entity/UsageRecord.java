package com.intellipay.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usage_records")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsageRecord {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_metric_id")
    private UsageMetric metric;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false, updatable = false)
    private LocalDateTime recordedAt;

    @PrePersist
    public void onCreate() {
        this.recordedAt = LocalDateTime.now();
    }
}
