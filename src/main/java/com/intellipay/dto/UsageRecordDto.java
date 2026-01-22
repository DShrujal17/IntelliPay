package com.intellipay.dto;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsageRecordDto {
    private UUID id;
    private UUID customerId;
    private UUID metricId;
    private String metricCode;
    private Long quantity;
}
