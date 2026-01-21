package com.intellipay.controller;

import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.CustomerProfileDto;
import com.intellipay.dto.UserDto;
import com.intellipay.entity.User;
import com.intellipay.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@PreAuthorize("hasRole('CUSTOMER')")
@RequiredArgsConstructor
public class CustomerController {

    private final ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<CustomerProfileDto>> getCustomerProfile(
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                ApiResponse.<CustomerProfileDto>builder()
                        .message("Customer profile fetched successfully")
                        .data(profileService.getCustomerProfile(user))
                        .build()
        );
    }
}
