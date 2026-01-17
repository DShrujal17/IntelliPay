package com.intellipay.controller;

import com.intellipay.dto.UserDto;
import com.intellipay.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final ProfileService profileService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/profile")
    public UserDto getCustomerProfile() {
        return profileService.getCurrentUserProfile();
    }
}
