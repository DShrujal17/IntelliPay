package com.intellipay.repository;

import com.intellipay.entity.Plan;
import com.intellipay.entity.PlanUsageMetric;
import com.intellipay.entity.UsageMetric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanUsageMetricRepository extends JpaRepository<PlanUsageMetric, UUID> {
    boolean existsByPlanAndUsageMetric(Plan plan, UsageMetric usageMetric);
}