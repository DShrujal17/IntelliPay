package com.intellipay.service;


import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.CreateCustomerRequestDto;
import com.intellipay.dto.CustomerDto;
import com.intellipay.entity.*;
import com.intellipay.repository.CustomerRepository;
import com.intellipay.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessCustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    public ApiResponse<CustomerDto> createCustomer(Business business, @Valid CreateCustomerRequestDto dto)
    {
        if (business.getStatus() != BusinessStatus.APPROVED) {
            throw new AccessDeniedException("Business is not approved");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Customer email already exists");
        }

        User customerUser = User.builder()
                .username(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(customerUser);

        Customer customer = Customer.builder()
                .user(customerUser)
                .business(business)
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return ApiResponse.<CustomerDto>builder()
                .message("Customer created successfully")
                .data(toDto(savedCustomer))
                .build();
    }

    public ApiResponse<List<CustomerDto>> getAllCustomers(Business business)
    {
        if (business.getStatus() != BusinessStatus.APPROVED) {
            throw new AccessDeniedException("Business is not approved");
        }

        List<CustomerDto> customers = customerRepository.findByBusiness(business)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return ApiResponse.<List<CustomerDto>>builder()
                .message("Customers fetched successfully")
                .data(customers)
                .build();
    }

    private CustomerDto toDto(Customer customer) {
        CustomerDto dto = modelMapper.map(customer, CustomerDto.class);
        dto.setName(customer.getUser().getUsername());
        dto.setEmail(customer.getUser().getEmail());
        dto.setBusinessName(customer.getBusiness().getName());
        return dto;
    }
}
