package com.intellipay.controller;

import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.CreateCustomerRequestDto;
import com.intellipay.dto.CustomerDto;
import com.intellipay.dto.UserDto;
import com.intellipay.entity.Business;
import com.intellipay.entity.User;
import com.intellipay.service.BusinessCustomerService;
import com.intellipay.service.BusinessService;
import com.intellipay.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
@PreAuthorize("hasRole('BUSINESS')")
public class BusinessController {

    private final ProfileService profileService;
    private final BusinessService businessService;
    private final BusinessCustomerService businessCustomerService;

    @PreAuthorize("hasRole('BUSINESS')")
    @GetMapping("/profile")
    public UserDto getBusinessProfile() {
        return profileService.getCurrentUserProfile();
    }

    @PostMapping("/customers")
    public ResponseEntity<ApiResponse<CustomerDto>> createCustomer(
            @AuthenticationPrincipal User businessUser,
            @Valid @RequestBody CreateCustomerRequestDto dto) {

        Business business = businessService.getBusinessForUser(businessUser);

        return ResponseEntity.ok(
                businessCustomerService.createCustomer(business, dto)
        );
    }
}
