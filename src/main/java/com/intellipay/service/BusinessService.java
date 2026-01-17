package com.intellipay.service;

import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.BusinessDto;
import com.intellipay.dto.CreateBusinessRequestDto;
import com.intellipay.entity.Business;
import com.intellipay.entity.BusinessStatus;
import com.intellipay.entity.Role;
import com.intellipay.entity.User;
import com.intellipay.repository.BusinessRepository;
import com.intellipay.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public ApiResponse<BusinessDto> createBusiness(@Valid CreateBusinessRequestDto dto)
    {
        if (userRepository.existsByEmail(dto.getOwnerEmail())) {
            throw new RuntimeException("Owner email already exists");
        }

        User owner = User.builder()
                .username(dto.getOwnerUsername())
                .email(dto.getOwnerEmail())
                .password(passwordEncoder.encode(dto.getOwnerPassword()))
                .role(Role.BUSINESS)
                .build();

        userRepository.save(owner);

        Business business = Business.builder()
                .name(dto.getBusinessName())
                .owner(owner)
                .status(BusinessStatus.PENDING)
                .build();

        Business savedBusiness = businessRepository.save(business);

        return ApiResponse.<BusinessDto>builder()
                .message("Business created successfully and pending approval")
                .data(toDto(savedBusiness))
                .build();
    }

    private BusinessDto toDto(Business business) {
        BusinessDto dto = modelMapper.map(business, BusinessDto.class);
        dto.setStatus(business.getStatus().name());
        dto.setOwnerEmail(business.getOwner().getEmail());
        return dto;
    }
}
