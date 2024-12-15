package com.project.api.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
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

  // public Users(){

  // }
  // public Users(String name, String password, String email){
  //   this.name = name;
  //   this.password = password;
  //   this.email = email;
  // }
  
}
