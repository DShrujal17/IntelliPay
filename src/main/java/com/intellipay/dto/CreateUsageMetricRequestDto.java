package com.intellipay.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUsageMetricRequestDto {
    @NotBlank(message = "Metric code is required")
    private String code;

    @NotBlank(message = "Metric name is required")
    private String name;

    @NotBlank(message = "Metric unit is required")
    private String unit;
}
