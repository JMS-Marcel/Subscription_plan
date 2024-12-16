package com.project.api.controller;

import java.util.List;

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
}
