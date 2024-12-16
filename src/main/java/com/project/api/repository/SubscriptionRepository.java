package com.project.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.api.model.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
  Optional<Subscription> findByType(String type);
}
