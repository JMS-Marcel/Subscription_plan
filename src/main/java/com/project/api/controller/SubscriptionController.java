package com.project.api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.api.model.Subscription;
import com.project.api.model.SubscriptionStatus;
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
  public ResponseEntity<String> createSubscription(@RequestBody SubscriptionRequest subscription){
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
    LocalDate nextBilling = LocalDate.parse(subscriptionData.get("nextBilling").toString());

    String statusString = (String) subscriptionData.get("status");
    SubscriptionStatus status = SubscriptionStatus.valueOf(statusString);

    subscriptionService.updateSubscription(subscriptionId, type, startDate, nextBilling, status);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Subscription updated successfully\"}");
  }

  @DeleteMapping(path = "{subscriptionId}")
  public ResponseEntity<String> deleteSubscription(@PathVariable("subscriptionId") Long subscriptionId){
    subscriptionService.deleteSubscription(subscriptionId);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Subscription id : " + subscriptionId + " is deleted successfully\"}");
  }

  @PutMapping(path = "{subscriptionId}/activate")
    public ResponseEntity<String> activateSubscription(@PathVariable("subscriptionId") Long subscriptionId) {
        subscriptionService.activated(subscriptionId);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Subscription activated successfully\"}");
    }

    @PutMapping(path = "{subscriptionId}/cancel")
    public ResponseEntity<String> cancelSubscription(@PathVariable("subscriptionId") Long subscriptionId) {
        subscriptionService.cancel(subscriptionId);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"Subscription cancelled successfully\"}");
    } 
}
