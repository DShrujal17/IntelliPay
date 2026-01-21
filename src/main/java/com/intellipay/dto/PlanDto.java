package com.intellipay.dto;

import com.intellipay.entity.BillingCycle;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private BillingCycle billingCycle;
    private boolean active;
}
