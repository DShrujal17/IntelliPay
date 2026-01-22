package com.intellipay.repository;

import com.intellipay.entity.Business;
import com.intellipay.entity.UsageMetric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsageMetricRepository extends JpaRepository<UsageMetric, UUID> {
    boolean existsByBusinessAndCode(Business business, String code);
}