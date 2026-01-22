package com.intellipay.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlanUsageMetricRequestDto {
    @NotNull(message = "Usage metric id is required")
    private UUID usageMetricId;

    @NotNull(message = "Free limit is required")
    @PositiveOrZero(message = "Free limit must be zero or greater")
    private Long freeLimit;

    @NotNull(message = "Price per unit is required")
    @Positive(message = "Price per unit must be greater than zero")
    private BigDecimal pricePerUnit;
}
