package com.project.api.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.api.model.Users;
import com.project.api.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  public List<Users> getUsers(){
    return userRepository.findAll();
  }

  public void addNewUser(Users users){
    Optional<Users> userOptional = userRepository.findByEmail(users.getEmail());
    if(userOptional.isPresent()){
      throw new IllegalStateException("This email is already taken");
    }
    userRepository.save(users); 

  }

  @Transactional
  public void updateUser(Long userId, String name, String password, String email ){
    Users users = userRepository.findById(userId).orElseThrow(
      ()-> new IllegalStateException("User with id " + userId + " does not exists")
    );

    if(name != null && name.length() > 0 && !Objects.equals(users.getName(), name)){
      users.setName(name);
    }

    if(password!= null && password.length() > 0 && !Objects.equals(users.getPassword(), password)){
      users.setPassword(password);
    }

    if(email != null && email.length() > 0 && !Objects.equals(users.getEmail(),email)){
      Optional<Users> userOptional = userRepository.findByEmail(email);
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
