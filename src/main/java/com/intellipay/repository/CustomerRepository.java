package com.intellipay.repository;

import com.intellipay.entity.Business;
import com.intellipay.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByBusiness(Business business);
}