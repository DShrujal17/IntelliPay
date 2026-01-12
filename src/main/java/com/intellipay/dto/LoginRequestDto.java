    package com.intellipay.dto;

    import jakarta.validation.constraints.NotBlank;
    import lombok.*;

    import java.util.UUID;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public class LoginRequestDto {
        @NotBlank(message = "Email is required")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;
    }
