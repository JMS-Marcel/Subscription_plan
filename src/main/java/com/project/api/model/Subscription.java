package com.project.api.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
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
  private Date startDate;
  private Date endDate;
  private String status;

  @ManyToOne
  @JoinColumn( name = "user_id")
  private Users user;

  @OneToMany(mappedBy = "subscription")
  private List<Payment> payments;

}
