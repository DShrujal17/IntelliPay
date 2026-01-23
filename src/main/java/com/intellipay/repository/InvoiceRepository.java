package com.intellipay.repository;

import com.intellipay.entity.Customer;
import com.intellipay.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    boolean existsByCustomerAndBillingMonth(Customer customer, YearMonth billingMonth);
    Optional<Invoice> findByCustomerAndBillingMonth(Customer customer, YearMonth billingMonth);
    List<Invoice> findByCustomer(Customer customer);
}