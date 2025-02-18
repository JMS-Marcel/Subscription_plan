package com.project.api.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

// import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.api.auth.RegisterRequest;
import com.project.api.model.Role;
import com.project.api.model.User;
import com.project.api.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class UserService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public List<User> getUsers(){
    return userRepository.findAll();
  }


  public User register(RegisterRequest request){
    var user = com.project.api.model.User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
    return userRepository.save(user);
  }

  @Transactional
  public void updateProfil(Long userId, String firstname,String lastname, String password, String email ){
    User users = userRepository.findById(userId).orElseThrow(
      ()-> new IllegalStateException("User with id " + userId + " does not exists")
    );

    if(firstname != null && firstname.length() > 0 && !Objects.equals(users.getFirstname(), firstname)){
      users.setFirstname(firstname);
    }
    if(lastname != null && lastname.length() > 0 && !Objects.equals(users.getLastname(), lastname)){
      users.setLastname(lastname);
    }

    if(password!= null && password.length() > 0 && !Objects.equals(users.getPassword(), password)){
      users.setPassword(password);
    }

    if(email != null && email.length() > 0 && !Objects.equals(users.getEmail(),email)){
      Optional<User> userOptional = userRepository.findByEmail(email);
			if(userOptional.isPresent()){
				throw new IllegalStateException("This email is already taken");
			}
      users.setEmail(email);
    }
  }

  public void deleteUser(Long userId){
    boolean exists = userRepository.existsById(userId);
    if(!exists){
      throw new IllegalStateException("Student with id " + userId + " does not exists");
    }
    userRepository.deleteById(userId);
  }

}
