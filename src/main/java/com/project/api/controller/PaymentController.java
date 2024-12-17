package com.project.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;


import com.project.api.model.Payment;
import com.project.api.service.PaymentService;

@RestController
@RequestMapping(path = "api/payment")
public class PaymentController {
  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService){
    this.paymentService = paymentService;
  }

  @GetMapping
  public List<Payment> getPayments(){
    return paymentService.getPayment();
  }

}
