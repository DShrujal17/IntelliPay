package com.intellipay.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerProfileDto {
    private UUID customerId;
    private String name;
    private String email;
    private UUID businessId;
    private String businessName;
}
