package com.project.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table
public class Packages {
  @Id
  @SequenceGenerator(
    name = "package_sequence",
    sequenceName = "package_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy= GenerationType.SEQUENCE,
    generator= "package_sequence"

  )

  private Long id;
  private String name;
  private Double price;
  private String description;

  @Lob 
  private byte[] image;

}
