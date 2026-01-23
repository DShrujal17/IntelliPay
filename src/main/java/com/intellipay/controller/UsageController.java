package com.intellipay.controller;

import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.RecordUsageRequestDto;
import com.intellipay.dto.UsageRecordDto;
import com.intellipay.entity.Business;
import com.intellipay.entity.User;
import com.intellipay.service.BusinessService;
import com.intellipay.service.UsageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usage")
@RequiredArgsConstructor
public class UsageController {

    private final UsageService usageService;
    private final BusinessService businessService;

    @PostMapping
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<ApiResponse<UsageRecordDto>> recordUsage(
            @AuthenticationPrincipal User businessUser,
            @Valid @RequestBody RecordUsageRequestDto dto) {

        Business business = businessService.getBusinessForUser(businessUser);

        return ResponseEntity.ok(
                usageService.recordUsage(business, dto)
        );
    }
}