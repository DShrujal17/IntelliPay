package com.intellipay.controller;

import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.BusinessDto;
import com.intellipay.dto.CreateBusinessRequestDto;
import com.intellipay.dto.UserDto;
import com.intellipay.service.BusinessService;
import com.intellipay.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProfileService profileService;
    private final BusinessService businessService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getAdminProfile()
    {
        return ResponseEntity.ok(profileService.getCurrentUserProfile());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/businesses")
    public ResponseEntity<ApiResponse<BusinessDto>> createBusiness(
            @Valid @RequestBody CreateBusinessRequestDto dto) {

        return ResponseEntity.ok(
                businessService.createBusiness(dto)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/businesses/{id}/approve")
    public ResponseEntity<ApiResponse<BusinessDto>> approveBusiness(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                businessService.approveBusiness(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/businesses/{id}/block")
    public ResponseEntity<ApiResponse<BusinessDto>> blockBusiness(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                businessService.blockBusiness(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/businesses")
    public ResponseEntity<ApiResponse<List<BusinessDto>>> getAllBusinesses() {

        return ResponseEntity.ok(
                businessService.getAllBusinesses()
        );
    }
}
