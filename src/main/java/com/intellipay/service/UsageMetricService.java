package com.intellipay.service;

import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.CreateUsageMetricRequestDto;
import com.intellipay.dto.UsageMetricDto;
import com.intellipay.entity.Business;
import com.intellipay.entity.BusinessStatus;
import com.intellipay.entity.UsageMetric;
import com.intellipay.repository.UsageMetricRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsageMetricService{
    private final UsageMetricRepository usageMetricRepository;
    private final ModelMapper modelMapper;

    public ApiResponse<UsageMetricDto> createUsageMetric(Business business, CreateUsageMetricRequestDto dto)
    {
        if (business.getStatus() != BusinessStatus.APPROVED) {
            throw new AccessDeniedException("Business is not approved");
        }

        if (usageMetricRepository.existsByBusinessAndCode(business, dto.getCode())) {
            throw new RuntimeException("Usage metric code already exists for this business");
        }

        UsageMetric metric = UsageMetric.builder()
                .code(dto.getCode())
                .name(dto.getName())
                .unit(dto.getUnit())
                .business(business)
                .active(true)
                .build();

        UsageMetric saved = usageMetricRepository.save(metric);

        return ApiResponse.<UsageMetricDto>builder()
                .message("Usage metric created successfully")
                .data(modelMapper.map(saved, UsageMetricDto.class))
                .build();
    }
}
