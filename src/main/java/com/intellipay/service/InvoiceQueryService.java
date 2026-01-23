package com.intellipay.service;

import com.intellipay.dto.InvoiceDto;
import com.intellipay.dto.InvoiceLineItemDto;
import com.intellipay.entity.Customer;
import com.intellipay.entity.Invoice;
import com.intellipay.entity.InvoiceLineItem;
import com.intellipay.repository.InvoiceLineItemRepository;
import com.intellipay.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceQueryService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceLineItemRepository invoiceLineItemRepository;

    public InvoiceDto getInvoiceById(UUID invoiceId)
    {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        List<InvoiceLineItem> lineItems =
                invoiceLineItemRepository.findByInvoice(invoice);

        List<InvoiceLineItemDto> itemDtos = lineItems.stream()
                .map(item -> InvoiceLineItemDto.builder()
                        .description(item.getDescription())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .amount(item.getLineAmount())
                        .build())
                .collect(Collectors.toList());

        return InvoiceDto.builder()
                .invoiceId(invoice.getId())
                .billingMonth(invoice.getBillingMonth())
                .status(invoice.getStatus().name())
                .totalAmount(invoice.getTotalAmount())
                .items(itemDtos)
                .build();
    }

    public List<InvoiceDto> getInvoicesForCustomer(Customer customer)
    {
        return invoiceRepository.findByCustomer(customer)
                .stream()
                .map(invoice -> InvoiceDto.builder()
                        .invoiceId(invoice.getId())
                        .billingMonth(invoice.getBillingMonth())
                        .status(invoice.getStatus().name())
                        .totalAmount(invoice.getTotalAmount())
                        .build())
                .collect(Collectors.toList());
    }
}
