package com.project.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.api.model.Subscription;
import com.project.api.repository.SubscriptionRepository;

@Service
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;

  public SubscriptionService(SubscriptionRepository subscriptionRepository)
  {
    this.subscriptionRepository = subscriptionRepository;
  }

  public List<Subscription> getSubscription(){
    return subscriptionRepository.findAll();
  }
}
