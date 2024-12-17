package com.project.api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PostMapping()
  public ResponseEntity<String> addPayment(@RequestBody Payment payment){
    paymentService.addPayment(payment);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Payment successfully\"}");
  }

  @PutMapping(path = "{paymentId}")
  public ResponseEntity<String> updatePayment(
    @PathVariable("paymentId") Long paymentId, 
    @RequestParam(required = false) Double amount,
    @RequestParam(required = false) LocalDate date

  ){
    paymentService.updatePayment(paymentId, amount, date);
    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Payment updated successfully\"}");
  }
}
