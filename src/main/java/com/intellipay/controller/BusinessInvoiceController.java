package com.intellipay.controller;

import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.InvoiceDto;
import com.intellipay.entity.Business;
import com.intellipay.entity.Customer;
import com.intellipay.entity.Invoice;
import com.intellipay.entity.User;
import com.intellipay.repository.CustomerRepository;
import com.intellipay.repository.InvoiceRepository;
import com.intellipay.service.BusinessService;
import com.intellipay.service.InvoiceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/business/invoices")
@RequiredArgsConstructor
@PreAuthorize("hasRole('BUSINESS')")
public class BusinessInvoiceController {

    private final InvoiceQueryService invoiceQueryService;
    private final BusinessService businessService;
    private final CustomerRepository customerRepository;
    private final InvoiceRepository invoiceRepository;

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<List<InvoiceDto>>> getInvoicesForCustomer(
            @AuthenticationPrincipal User businessUser,
            @PathVariable UUID customerId
    ) {

        Business business = businessService.getBusinessForUser(businessUser);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!customer.getBusiness().getId().equals(business.getId())) {
            throw new AccessDeniedException("Customer does not belong to this business");
        }

        List<InvoiceDto> invoices =
                invoiceQueryService.getInvoicesForCustomer(customer);

        return ResponseEntity.ok(
                ApiResponse.<List<InvoiceDto>>builder()
                        .message("Invoices fetched successfully")
                        .data(invoices)
                        .build()
        );
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<ApiResponse<InvoiceDto>> getInvoiceById(
            @AuthenticationPrincipal User businessUser,
            @PathVariable UUID invoiceId
    ) {

        Business business = businessService.getBusinessForUser(businessUser);

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (!invoice.getCustomer().getBusiness().getId().equals(business.getId())) {
            throw new AccessDeniedException("Invoice does not belong to this business");
        }

        InvoiceDto invoiceDto =
                invoiceQueryService.getInvoiceById(invoiceId);

        return ResponseEntity.ok(
                ApiResponse.<InvoiceDto>builder()
                        .message("Invoice fetched successfully")
                        .data(invoiceDto)
                        .build()
        );
    }
}
