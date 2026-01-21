package com.intellipay.service;

import com.intellipay.dto.CustomerProfileDto;
import com.intellipay.dto.UserDto;
import com.intellipay.entity.Customer;
import com.intellipay.entity.User;
import com.intellipay.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    public UserDto getCurrentUserProfile() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User is not authenticated");
        }

        User user = (User) authentication.getPrincipal();

        return modelMapper.map(user, UserDto.class);
    }

    public CustomerProfileDto getCustomerProfile(User user) {

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerProfileDto dto = new CustomerProfileDto();
        dto.setCustomerId(customer.getId());
        dto.setName(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBusinessId(customer.getBusiness().getId());
        dto.setBusinessName(customer.getBusiness().getName());

        return dto;
    }

}
