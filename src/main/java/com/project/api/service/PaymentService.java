package com.project.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.api.model.Payment;
import com.project.api.repository.PaymentRepository;

@Service
public class PaymentService {
  private final PaymentRepository paymentRepository;
  
  public PaymentService(PaymentRepository paymentRepository){
    this.paymentRepository = paymentRepository;
  }

  public List<Payment> getPayment(){
    return paymentRepository.findAll();
  }

  public void addPayment(Payment payment){
    paymentRepository.save(payment);
  }
}
