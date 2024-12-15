package com.project.api.model;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Users {
   @Id
  @SequenceGenerator(
    name = "user_sequence",
    sequenceName = "user_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy= GenerationType.SEQUENCE,
    generator= "user_sequence"

  )

  private Long id;
  private String name;
  private String password;
  private String email;

  @OneToOne(mappedBy = "user")
  private Subscription subscription;

  // public Users(){

  // }
  // public Users(String name, String password, String email){
  //   this.name = name;
  //   this.password = password;
  //   this.email = email;
  // }
  
}
