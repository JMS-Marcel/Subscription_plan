package com.project.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.api.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
  
}
