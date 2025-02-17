package com.project.api.model;


import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Subscription {
  @Id
  @SequenceGenerator(
    name = "subcription_sequence",
    sequenceName = "subscription_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy= GenerationType.SEQUENCE,
    generator= "subcription_sequence"

  )
  private Long id;

  private String type;
  private LocalDate startDate;
  private LocalDate nextBilling;

  @Enumerated(EnumType.STRING)
  private SubscriptionStatus status;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn( name = "user_id")
  @JsonIgnoreProperties("subscription")
  private User user;

  @ManyToOne
  @JoinColumn( name = "package_id")
  @JsonIgnoreProperties("subscription")
  private Packages packages;

  @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties("subscription")
  private List<Payment> payments;

}
