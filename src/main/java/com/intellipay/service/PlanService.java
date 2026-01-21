package com.intellipay.service;

import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.CreatePlanRequestDto;
import com.intellipay.dto.PlanDto;
import com.intellipay.entity.Business;
import com.intellipay.entity.BusinessStatus;
import com.intellipay.entity.Plan;
import com.intellipay.repository.PlanRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final ModelMapper modelMapper;

    public ApiResponse<PlanDto> createPlan(Business business, @Valid CreatePlanRequestDto dto)
    {
        if (business.getStatus() != BusinessStatus.APPROVED) {
            throw new AccessDeniedException("Business is not approved");
        }

        if (planRepository.existsByBusinessAndName(business, dto.getName())) {
            throw new RuntimeException("Plan name already exists for this business");
        }

        Plan plan = Plan.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .billingCycle(dto.getBillingCycle())
                .business(business)
                .active(true)
                .build();

        Plan savedPlan = planRepository.save(plan);

        return ApiResponse.<PlanDto>builder()
                .message("Plan created successfully")
                .data(modelMapper.map(savedPlan, PlanDto.class))
                .build();
    }
}
