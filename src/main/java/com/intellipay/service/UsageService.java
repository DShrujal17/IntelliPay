package com.intellipay.service;

import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.RecordUsageRequestDto;
import com.intellipay.dto.UsageRecordDto;
import com.intellipay.entity.*;
import com.intellipay.repository.CustomerRepository;
import com.intellipay.repository.SubscriptionRepository;
import com.intellipay.repository.UsageMetricRepository;
import com.intellipay.repository.UsageRecordRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsageService {

    private final CustomerRepository customerRepository;
    private final UsageMetricRepository usageMetricRepository;
    private final UsageRecordRepository usageRecordRepository;
    private final SubscriptionRepository subscriptionRepository;

    public ApiResponse<UsageRecordDto> recordUsage(Business business, RecordUsageRequestDto dto)
    {
        if (business.getStatus() != BusinessStatus.APPROVED) {
            throw new AccessDeniedException("Business is not approved");
        }

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!customer.getBusiness().getId().equals(business.getId())) {
            throw new AccessDeniedException("Customer does not belong to this business");
        }

        UsageMetric metric = usageMetricRepository.findById(dto.getMetricId())
                .orElseThrow(() -> new RuntimeException("Usage metric not found"));

        if (!metric.getBusiness().getId().equals(business.getId())) {
            throw new AccessDeniedException("Usage metric does not belong to this business");
        }

        boolean hasActiveSubscription =
                subscriptionRepository.existsByCustomerAndStatus(
                        customer,
                        SubscriptionStatus.ACTIVE
                );

        if (!hasActiveSubscription) {
            throw new AccessDeniedException("Customer has no active subscription");
        }

        UsageRecord saved = usageRecordRepository.save(
                UsageRecord.builder()
                        .customer(customer)
                        .metric(metric)
                        .quantity(dto.getQuantity())
                        .build()
        );

        UsageRecordDto response = UsageRecordDto.builder()
                .id(saved.getId())
                .customerId(customer.getId())
                .metricId(metric.getId())
                .metricCode(metric.getCode())
                .quantity(saved.getQuantity())
                .build();

        return ApiResponse.<UsageRecordDto>builder()
                .message("Usage recorded successfully")
                .data(response)
                .build();
    }
}
