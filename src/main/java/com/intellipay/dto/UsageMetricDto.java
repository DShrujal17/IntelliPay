package com.intellipay.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsageMetricDto {
    private UUID id;
    private String code;
    private String name;
    private String unit;
    private boolean active;
}
