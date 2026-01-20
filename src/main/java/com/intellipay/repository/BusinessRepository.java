package com.intellipay.repository;

import com.intellipay.entity.Business;
import com.intellipay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BusinessRepository extends JpaRepository<Business, UUID> {
    Optional<Business> findByOwner(User owner);
}