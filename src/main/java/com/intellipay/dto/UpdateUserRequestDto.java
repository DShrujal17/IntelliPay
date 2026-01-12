package com.intellipay.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private String username;

    @Email(message = "Email is not valid")
    private String email;
}
