package com.intellipay.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceLineItemDto {
    private String description;   // e.g. "Monthly plan fee", "API Calls usage"
    private Long quantity;         // e.g. 1, 200, 2
    private BigDecimal unitPrice;  // e.g. 999, 0.2, 10
    private BigDecimal amount;
}
