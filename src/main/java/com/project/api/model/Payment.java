package com.project.api.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Payment {
   @Id
  @SequenceGenerator(
    name = "payment_sequence",
    sequenceName = "payment_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy= GenerationType.SEQUENCE,
    generator= "payment_sequence"

  )
  private Long id;

  private Double amount;
  private Date date;

  @ManyToOne
  @JoinColumn( name = "subscription_id")
  private Subscription subscription;

  
}
