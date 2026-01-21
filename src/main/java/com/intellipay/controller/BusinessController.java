package com.intellipay.controller;

import com.intellipay.dto.*;
import com.intellipay.entity.Business;
import com.intellipay.entity.User;
import com.intellipay.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
@PreAuthorize("hasRole('BUSINESS')")
public class BusinessController {

    private final ProfileService profileService;
    private final BusinessService businessService;
    private final BusinessCustomerService businessCustomerService;
    private final PlanService planService;
    private final SubscriptionService subscriptionService;

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

    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<List<CustomerDto>>> getAllCustomers(
            @AuthenticationPrincipal User businessUser) {

        Business business = businessService.getBusinessForUser(businessUser);

        return ResponseEntity.ok(
                businessCustomerService.getAllCustomers(business)
        );
    }

    @PostMapping("/plans")
    public ResponseEntity<ApiResponse<PlanDto>> createPlan(
            @AuthenticationPrincipal User businessUser,
            @Valid @RequestBody CreatePlanRequestDto dto) {

        Business business = businessService.getBusinessForUser(businessUser);

        return ResponseEntity.ok(
                planService.createPlan(business, dto)
        );
    }

    @PostMapping("/customers/{customerId}/subscribe")
    public ResponseEntity<ApiResponse<SubscriptionDto>> subscribeCustomer(
            @AuthenticationPrincipal User businessUser,
            @PathVariable UUID customerId,
            @Valid @RequestBody SubscribeCustomerRequestDto dto) {

        Business business = businessService.getBusinessForUser(businessUser);

        return ResponseEntity.ok(
                subscriptionService.subscribeCustomer(business, customerId, dto)
        );
    }
}
