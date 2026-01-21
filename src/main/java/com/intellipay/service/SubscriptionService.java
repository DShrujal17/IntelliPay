package com.intellipay.service;

import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.SubscribeCustomerRequestDto;
import com.intellipay.dto.SubscriptionDto;
import com.intellipay.entity.*;
import com.intellipay.repository.CustomerRepository;
import com.intellipay.repository.PlanRepository;
import com.intellipay.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final CustomerRepository customerRepository;
    private final PlanRepository planRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ModelMapper modelMapper;

    public ApiResponse<SubscriptionDto> subscribeCustomer(Business business, UUID customerId, SubscribeCustomerRequestDto dto)
    {

        if (business.getStatus() != BusinessStatus.APPROVED) {
            throw new AccessDeniedException("Business is not approved");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!customer.getBusiness().getId().equals(business.getId())) {
            throw new AccessDeniedException("Customer does not belong to this business");
        }

        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        if (!plan.getBusiness().getId().equals(business.getId())) {
            throw new AccessDeniedException("Plan does not belong to this business");
        }

        subscriptionRepository.findByCustomerAndStatus(customer, SubscriptionStatus.ACTIVE)
                .ifPresent(existing -> {
                    existing.setStatus(SubscriptionStatus.CANCELLED);
                    existing.setEndDate(LocalDateTime.now());
                    subscriptionRepository.save(existing);
                });

        Subscription subscription = Subscription.builder()
                .customer(customer)
                .plan(plan)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDateTime.now())
                .build();

        Subscription saved = subscriptionRepository.save(subscription);

        SubscriptionDto response = SubscriptionDto.builder()
                .id(saved.getId())
                .customerId(customer.getId())
                .planId(plan.getId())
                .status(saved.getStatus())
                .startDate(saved.getStartDate())
                .endDate(saved.getEndDate())
                .build();

        return ApiResponse.<SubscriptionDto>builder()
                .message("Customer subscribed successfully")
                .data(response)
                .build();
    }
}
