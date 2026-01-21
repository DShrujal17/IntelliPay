package com.intellipay.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeCustomerRequestDto {
    @NotNull(message = "Plan id is required")
    private UUID planId;
}
