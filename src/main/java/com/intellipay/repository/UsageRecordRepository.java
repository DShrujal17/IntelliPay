package com.intellipay.repository;

import com.intellipay.entity.Customer;
import com.intellipay.entity.UsageMetric;
import com.intellipay.entity.UsageRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface UsageRecordRepository extends JpaRepository<UsageRecord, UUID> {
    List<UsageRecord> findByCustomerAndRecordedAtBetween(Customer customer, LocalDateTime start, LocalDateTime end);
    List<UsageRecord> findByCustomerAndMetric(Customer customer, UsageMetric metric);
}