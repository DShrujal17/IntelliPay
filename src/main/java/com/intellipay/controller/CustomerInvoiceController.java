package com.intellipay.controller;

import com.intellipay.dto.ApiResponse;
import com.intellipay.dto.InvoiceDto;
import com.intellipay.entity.Customer;
import com.intellipay.entity.Invoice;
import com.intellipay.entity.User;
import com.intellipay.repository.CustomerRepository;
import com.intellipay.repository.InvoiceRepository;
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
@RequestMapping("/customer/invoices")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerInvoiceController {

    private final InvoiceQueryService invoiceQueryService;
    private final CustomerRepository customerRepository;
    private final InvoiceRepository invoiceRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceDto>>> getMyInvoices(
            @AuthenticationPrincipal User user
    ) {

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

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
    public ResponseEntity<ApiResponse<InvoiceDto>> getMyInvoiceById(
            @AuthenticationPrincipal User user,
            @PathVariable UUID invoiceId
    ) {

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (!invoice.getCustomer().getId().equals(customer.getId())) {
            throw new AccessDeniedException("Invoice does not belong to this customer");
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
