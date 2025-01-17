package com.project.api.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.api.model.User;
import com.project.api.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final SessionService sessionService;

  public UserService(UserRepository userRepository, SessionService sessionService){
    this.userRepository = userRepository;
    this.sessionService = sessionService;
  }

  public List<User> getUsers(){
    return userRepository.findAll();
  }

  public void register(User users){
    Optional<User> userOptional = userRepository.findByEmail(users.getEmail());
    if(userOptional.isPresent()){
      throw new IllegalStateException("This email is already taken");
    }
    userRepository.save(users); 

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

  public String login(String email, String password) {
    User user = userRepository.findByEmail(email).orElseThrow(
        () -> new IllegalStateException("User with email " + email + " does not exist")
    );

    if (!user.getPassword().equals(password)) {
        throw new IllegalStateException("Incorrect password");
    }

    // Return a success message or a token (for simplicity, returning a message here)
    return "Login successful";
}
public String logout(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(
        () -> new IllegalStateException("User with email " + email + " does not exist")
    );
    sessionService.invalidateSession(user.getId());

    return "User with email " + email + " has been logged out successfully";
}
}
