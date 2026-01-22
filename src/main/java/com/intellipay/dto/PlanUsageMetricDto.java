package com.intellipay.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanUsageMetricDto {
    private UUID id;
    private UUID planId;
    private UUID usageMetricId;
    private String metricCode;
    private Long freeLimit;
    private BigDecimal pricePerUnit;
}
