package com.project.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.api.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
  List<Payment> findByStatus(String status);
}
