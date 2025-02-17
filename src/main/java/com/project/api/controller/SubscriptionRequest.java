package com.project.api.controller;

import java.time.LocalDate;

import com.project.api.model.SubscriptionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRequest {
  private Long userId;
  private Long packageId;
  private SubscriptionStatus status;
  private String type;
  private LocalDate startDate;
  private LocalDate nextBilling;
}
