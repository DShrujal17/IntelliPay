package com.intellipay.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CreateBusinessRequestDto {
    @NotBlank(message = "Business Name is required")
    private String businessName;

    @NotBlank(message = "Owner Name is required")
    private String ownerUsername;

    @Email(message = "Email is not in proper formate")
    @NotBlank(message = "Email is required")
    private String ownerEmail;

    @NotBlank(message = "Password is required")
    private String ownerPassword;
}
