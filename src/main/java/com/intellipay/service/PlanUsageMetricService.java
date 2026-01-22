package com.intellipay.service;

import com.intellipay.dto.*;
import com.intellipay.entity.*;
import com.intellipay.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanUsageMetricService {
    private final PlanRepository planRepository;
    private final UsageMetricRepository usageMetricRepository;
    private final PlanUsageMetricRepository planUsageMetricRepository;

    public ApiResponse<PlanUsageMetricDto> assignMetricToPlan(Business business, UUID planId, CreatePlanUsageMetricRequestDto dto)
    {
        if (business.getStatus() != BusinessStatus.APPROVED) {
            throw new AccessDeniedException("Business is not approved");
        }

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        if (!plan.getBusiness().getId().equals(business.getId())) {
            throw new AccessDeniedException("Plan does not belong to this business");
        }

        UsageMetric metric = usageMetricRepository.findById(dto.getUsageMetricId())
                .orElseThrow(() -> new RuntimeException("Usage metric not found"));

        if (!metric.getBusiness().getId().equals(business.getId())) {
            throw new AccessDeniedException("Usage metric does not belong to this business");
        }

        if (planUsageMetricRepository.existsByPlanAndUsageMetric(plan, metric)) {
            throw new RuntimeException("Usage metric already assigned to this plan");
        }

        PlanUsageMetric saved = planUsageMetricRepository.save(
                PlanUsageMetric.builder()
                        .plan(plan)
                        .usageMetric(metric)
                        .freeLimit(dto.getFreeLimit())
                        .pricePerUnit(dto.getPricePerUnit())
                        .build()
        );

        PlanUsageMetricDto response = PlanUsageMetricDto.builder()
                .id(saved.getId())
                .planId(plan.getId())
                .usageMetricId(metric.getId())
                .metricCode(metric.getCode())
                .freeLimit(saved.getFreeLimit())
                .pricePerUnit(saved.getPricePerUnit())
                .build();

        return ApiResponse.<PlanUsageMetricDto>builder()
                .message("Usage metric assigned to plan successfully")
                .data(response)
                .build();
    }
}
