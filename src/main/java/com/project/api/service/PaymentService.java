package com.project.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.project.api.model.Payment;
import com.project.api.repository.PaymentRepository;

import jakarta.transaction.Transactional;

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

  @Transactional
  public void updatePayment(Long paymentId, Double amount,@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
    Payment payment = paymentRepository.findById(paymentId).orElseThrow(()-> new IllegalStateException("Payment with id " + paymentId + " does not exists"));

    if(amount != null && !amount.isNaN() && !Objects.equals(payment.getAmount(), amount)){
      payment.setAmount(amount);
    }
    if(date != null && !Objects.equals(payment.getDate(), date)){
      payment.setDate(date);
    }
  }
  
  public void deletePayment(Long paymentId){
    boolean exists = paymentRepository.existsById(paymentId);
    if(!exists){
      throw new IllegalStateException("Subscription with id :" + paymentId + " does not exists");
    }
    paymentRepository.deleteById(paymentId);
  }
}
