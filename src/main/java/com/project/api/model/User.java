package com.project.api.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
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

  @NotBlank(message = "Le prénom est obligatoire")
  private String firstname;

  @NotBlank(message = "Le nom est obligatoire")
  private String lastname;

  @NotBlank(message = "Le mot de passe est obligatoire")
  private String password;

  @NotBlank(message = "L'email est obligatoire")
  @Email(message = "L'email n'est pas valide")
  private String email;

  @Enumerated(EnumType.STRING)
  private Role role;

  // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  // private List<Token> tokens;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private Subscription subscription;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities(){
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword(){
    return password;
  }

  @Override
  public String getUsername(){
    return email;
  }

  @Override
  public boolean isAccountNonExpired(){
    return true;
  }

  @Override
  public boolean isAccountNonLocked(){
    return true;
  }
  
  @Override 
  public boolean isCredentialsNonExpired(){
    return true;
  }
  @Override
  public boolean isEnabled(){
    return true;
  }
}
