package com.intellipay.repository;

import com.intellipay.entity.Customer;
import com.intellipay.entity.Subscription;
import com.intellipay.entity.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    boolean existsByCustomerAndStatus(Customer customer, SubscriptionStatus status);
    Optional<Subscription> findByCustomerAndStatus(Customer customer, SubscriptionStatus status);
}