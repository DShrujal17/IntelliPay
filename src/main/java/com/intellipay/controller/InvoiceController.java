package com.intellipay.controller;


import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.GenerateInvoiceRequestDto;
import com.intellipay.entity.Business;
import com.intellipay.entity.Customer;
import com.intellipay.entity.User;
import com.intellipay.service.BusinessService;
import com.intellipay.service.InvoiceGenerationService;
import com.intellipay.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceGenerationService invoiceGenerationService;
    private final BusinessService businessService;
    private final CustomerRepository customerRepository;

    @PostMapping("/generate")
    @PreAuthorize("hasRole('BUSINESS')")
    public ResponseEntity<ApiResponse<String>> generateInvoice(
            @AuthenticationPrincipal User businessUser,
            @Valid @RequestBody GenerateInvoiceRequestDto dto) {

        Business business = businessService.getBusinessForUser(businessUser);

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Security: customer must belong to this business
        if (!customer.getBusiness().getId().equals(business.getId())) {
            throw new AccessDeniedException("Customer does not belong to this business");
        }

        invoiceGenerationService.generateInvoice(
                customer,
                dto.getBillingMonth()
        );

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .message("Invoice generated successfully")
                        .data("Invoice created for " + dto.getBillingMonth())
                        .build()
        );
    }
}
