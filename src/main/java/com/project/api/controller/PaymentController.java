package com.project.api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    @RequestBody Map<String, String> paymentData 
  ){

    Double amount = Double.parseDouble(paymentData.get("amount"));
    LocalDate date = LocalDate.parse(paymentData.get("date").toString());
    String status = paymentData.get("status");

    paymentService.updatePayment(paymentId, amount, date, status);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Payment updated successfully\"}");
  }

  @DeleteMapping(path = "{paymentId}")
  public ResponseEntity<String> deletePayment(@PathVariable("paymentId") Long paymentId){
    paymentService.deletePayment(paymentId);
    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Payment id " + paymentId +" deleted successfully\"}");
  }
}
