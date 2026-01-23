package com.intellipay.service;

import com.intellipay.entity.*;
import com.intellipay.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceGenerationService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceLineItemRepository invoiceLineItemRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UsageRecordRepository usageRecordRepository;
    private final PlanUsageMetricRepository planUsageMetricRepository;

    public void generateInvoice(Customer customer, YearMonth billingMonth)
    {
        Subscription subscription = subscriptionRepository
                .findByCustomerAndStatus(customer, SubscriptionStatus.ACTIVE)
                .orElseThrow(() ->
                        new RuntimeException("Customer has no active subscription"));

        if (invoiceRepository.existsByCustomerAndBillingMonth(customer, billingMonth)) {
            throw new RuntimeException("Invoice already exists for this month");
        }

        Plan plan = subscription.getPlan();

        Invoice invoice = Invoice.builder()
                .customer(customer)
                .billingMonth(billingMonth)
                .status(InvoiceStatus.GENERATED)
                .totalAmount(BigDecimal.ZERO)
                .build();

        Invoice savedInvoice = invoiceRepository.save(invoice);

        BigDecimal totalAmount = BigDecimal.ZERO;

        //base plan fee
        BigDecimal planPrice = plan.getPrice();

        InvoiceLineItem planItem = InvoiceLineItem.builder()
                .invoice(savedInvoice)
                .description("Monthly plan fee")
                .quantity(1L)
                .unitPrice(planPrice)
                .lineAmount(planPrice)
                .build();

        invoiceLineItemRepository.save(planItem);
        totalAmount = totalAmount.add(planPrice);

        //Usage time
        LocalDateTime start = billingMonth.atDay(1).atStartOfDay();
        LocalDateTime end = billingMonth.atEndOfMonth().atTime(23, 59, 59);

        List<UsageRecord> usageRecords =
                usageRecordRepository.findByCustomerAndRecordedAtBetween(customer, start, end);

        //Apply usage pricing
        List<PlanUsageMetric> pricingRules =
                planUsageMetricRepository.findByPlan(plan);

        for (PlanUsageMetric rule : pricingRules)
        {
            UsageMetric metric = rule.getUsageMetric();

            long totalUsed = usageRecords.stream()
                    .filter(u -> u.getMetric().getId().equals(metric.getId()))
                    .mapToLong(UsageRecord::getQuantity)
                    .sum();

            long billableUsage =
                    Math.max(totalUsed - rule.getFreeLimit(), 0);

            if (billableUsage > 0)
            {
                BigDecimal usageCost = rule.getPricePerUnit().multiply(BigDecimal.valueOf(billableUsage));
                InvoiceLineItem usageItem = InvoiceLineItem.builder()
                        .invoice(savedInvoice)
                        .description(metric.getName() + " usage")
                        .quantity(billableUsage)
                        .unitPrice(rule.getPricePerUnit())
                        .lineAmount(usageCost)
                        .build();

                invoiceLineItemRepository.save(usageItem);
                totalAmount = totalAmount.add(usageCost);
            }
        }
        savedInvoice.setTotalAmount(totalAmount);
        invoiceRepository.save(savedInvoice);
    }
}
