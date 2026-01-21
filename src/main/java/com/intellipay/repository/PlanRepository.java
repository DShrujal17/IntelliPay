package com.intellipay.repository;

import com.intellipay.entity.Business;
import com.intellipay.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
    boolean existsByBusinessAndName(Business business, String name);
    List<Plan> findByBusiness(Business business);
}