package com.project.api.model;


import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Subscription {
  @Id
  @SequenceGenerator(
    name = "subcription_sequence",
    sequenceName = "subcription_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy= GenerationType.SEQUENCE,
    generator= "subcription_sequence"

  )
  private Long id;

  private String type;
  private LocalDate startDate;
  private LocalDate endDate;
  private String status;

  @ManyToOne
  @JoinColumn( name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn( name = "package_id")
  private Packages packages;

  @OneToMany(mappedBy = "subscription")
  private List<Payment> payments;

}
