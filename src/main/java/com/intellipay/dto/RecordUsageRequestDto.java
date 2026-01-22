package com.intellipay.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordUsageRequestDto {
    @NotNull(message = "Customer id is required")
    private UUID customerId;

    @NotNull(message = "Metric id is required")
    private UUID metricId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private Long quantity;
}
