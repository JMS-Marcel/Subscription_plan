package com.project.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.project.api.controller.SubscriptionRequest;
import com.project.api.model.Packages;
import com.project.api.model.Subscription;
import com.project.api.model.User;
import com.project.api.repository.PackageRepository;
import com.project.api.repository.SubscriptionRepository;
import com.project.api.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class SubscriptionService {
  private final SubscriptionRepository subscriptionRepository;

  private final UserRepository userRepository;

  private final PackageRepository packageRepository;

  public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository, PackageRepository packageRepository)
  {
    this.subscriptionRepository = subscriptionRepository;
    this.userRepository = userRepository;
    this.packageRepository = packageRepository;
  }

  public List<Subscription> getSubscription(){
    return subscriptionRepository.findAll();
  }

  public void addNewSubscription(SubscriptionRequest request){
    // Optional<Subscription> subscriptionOptional = subscriptionRepository.findByType(subscription.getType());
    // if(subscriptionOptional.isPresent()){
    //   throw new IllegalStateException("This Type is already exist");
    // }
    // subscriptionRepository.save(subscription);
      // Récupérer l'utilisateur par son ID
      User user = userRepository.findById(request.getUserId())
      .orElseThrow(() -> new RuntimeException("User not found"));

      // Récupérer le package par son ID
      Packages pkg = packageRepository.findById(request.getPackageId())

      .orElseThrow(() -> new RuntimeException("Package not found"));
    var subscription = com.project.api.model.Subscription.builder()
      .user(user)
      .packages(pkg)
      .type(request.getType())
      .startDate(request.getStartDate())
      .nextBilling(request.getNextBilling())
      .status(request.getStatus())
      .build();
    subscriptionRepository.save(subscription);

  }

  @Transactional
  public void updateSubscription(Long suscriptionId, String type,
                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate, 
                                @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate nextBilling, 
                                String status ){
    Subscription subscription = subscriptionRepository.findById(suscriptionId).orElseThrow(
      ()-> new IllegalStateException("Subscription with id " + suscriptionId + " does not exists")
    );
    if( type != null && type.length() > 0 && !Objects.equals(subscription.getType(), type)){
      Optional<Subscription> subscriptionOptional = subscriptionRepository.findByType(type);
      if(subscriptionOptional.isPresent()){
        throw new IllegalStateException("This Type is already exist");
      }
      subscription.setType(type);
    }
    if(startDate != null && !Objects.equals(subscription.getStartDate(), startDate)){
      subscription.setStartDate(startDate);
    }
    if(nextBilling != null && !Objects.equals(subscription.getNextBilling(), nextBilling)){
      subscription.setNextBilling(nextBilling);
    }
    if(status != null && status.length() > 0 && !Objects.equals(subscription.getStatus(), status)){
      subscription.setStatus(status);
    }
  }

  public void deleteSubscription(Long subscriptionId){
    boolean exists = subscriptionRepository.existsById(subscriptionId);
    if(!exists){
      throw new IllegalStateException("Subscription with id :" + subscriptionId + " does not exists");
    }
    subscriptionRepository.deleteById(subscriptionId);
  }

  @Transactional
  public void activated(Long subscriptionId) {
    Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(
        () -> new IllegalStateException("Subscription with id " + subscriptionId + " does not exist")
    );

    if (!"ACTIVE".equals(subscription.getStatus())) {
        subscription.setStatus("ACTIVE");
        subscriptionRepository.save(subscription);
    }
  }

  @Transactional
  public void cancel(Long subscriptionId) {
      Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(
          () -> new IllegalStateException("Subscription with id " + subscriptionId + " does not exist")
      );
  
      if (!"CANCELLED".equals(subscription.getStatus())) {
          subscription.setStatus("CANCELLED");
          subscriptionRepository.save(subscription);
      }
  }
}
