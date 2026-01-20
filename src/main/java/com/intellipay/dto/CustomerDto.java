package com.intellipay.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private UUID id;
    private String name;
    private String email;
    private String businessName;
}
