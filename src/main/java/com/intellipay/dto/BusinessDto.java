package com.intellipay.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDto {
    private UUID id;
    private String name;
    private String status;
    private String ownerEmail;
}
