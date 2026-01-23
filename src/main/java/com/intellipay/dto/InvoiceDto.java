package com.intellipay.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private UUID invoiceId;
    private YearMonth billingMonth;   // e.g. 2026-01
    private String status;            // GENERATED / PAID / FAILED
    private BigDecimal totalAmount;

    private List<InvoiceLineItemDto> items;
}
