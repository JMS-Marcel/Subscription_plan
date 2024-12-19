package com.project.api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.api.model.Subscription;
import com.project.api.service.SubscriptionService;


@RestController
@RequestMapping( path = "api/subscription" )
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  public SubscriptionController(SubscriptionService subscriptionService){
    this.subscriptionService = subscriptionService;
  }

  @GetMapping
  public List<Subscription> getSubscription(){
    return subscriptionService.getSubscription();
  }

  @PostMapping()
  public ResponseEntity<String> createSubscription(@RequestBody Subscription subscription){
    subscriptionService.addNewSubscription(subscription);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Subscription added successfully\"}");
  }

  @PutMapping(path = "{subscriptionId}")
  public ResponseEntity<String> updateSubscription(
    @PathVariable("subscriptionId") Long subscriptionId,
    @RequestBody Map<String, Object> subscriptionData

   )
  {

    String type = (String) subscriptionData.get("type");
    LocalDate startDate = LocalDate.parse(subscriptionData.get("startDate").toString());
    LocalDate endDate = LocalDate.parse(subscriptionData.get("endDate").toString());
    String status = (String) subscriptionData.get("status");

    subscriptionService.updateSubscription(subscriptionId, type, startDate, endDate, status);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Subscription updated successfully\"}");
  }

  @DeleteMapping(path = "{subscriptionId}")
  public ResponseEntity<String> deleteSubscription(@PathVariable("subscriptionId") Long subscriptionId){
    subscriptionService.deleteSubscription(subscriptionId);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Subscription id : " + subscriptionId + " is deleted successfully\"}");
  }
}
