package com.project.api.controller;

import java.time.LocalDate;

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
  private String status;
  private String type;
  private LocalDate startDate;
  private LocalDate nextBilling;
}
