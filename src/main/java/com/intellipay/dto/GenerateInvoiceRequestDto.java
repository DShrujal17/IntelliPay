package com.intellipay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.YearMonth;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateInvoiceRequestDto {
    @NotNull(message = "Customer id is required")
    private UUID customerId;

    @NotNull(message = "Billing month is required")
    private YearMonth billingMonth;
}
