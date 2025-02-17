package com.project.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.api.auth.RegisterRequest;
import com.project.api.config.JwtService;
import com.project.api.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import com.project.api.model.User;



@RestController 
@RequestMapping(path = "api/user")
public class UserController {
  
  private final UserService userService;

  private final JwtService jwtUtil;
  
  public UserController(UserService userService,JwtService jwtUtil ){
    this.userService = userService;
    this.jwtUtil = jwtUtil;
  }

  @GetMapping
  public List<User> getUsers() {
      return userService.getUsers();
  }

  @PostMapping()
  public ResponseEntity<User> registerNewUser(@RequestBody RegisterRequest request){
    
    return ResponseEntity.ok(userService.register(request));

    // return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"User added successfully\"}");

  }

  @PutMapping(path = "{userId}")
  public ResponseEntity<String> updateUser(@PathVariable("userId") Long userId,
    @RequestBody Map<String, String> userData
  ){
    String firstname = userData.get("firstname");
    String lastname = userData.get("lastname");
    String password = userData.get("password");
    String email = userData.get("email");

      userService.updateProfil(userId, firstname, lastname, password, email);

      return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"User updated successfully\"}");
  }

  @DeleteMapping(path = "{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId){
    
    userService.deleteUser(userId);
    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"User " + userId + " is deleted successfully\"}");
  }

  @GetMapping("/me")
  public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
      String token = request.getHeader("Authorization").substring(7); // Remove "Bearer "
      String email = jwtUtil.extractUsername(token);
      String firstname = jwtUtil.extractFirstname(token);
      String lastname = jwtUtil.extractLastname(token);
      String role = jwtUtil.extractRole(token);

      Map<String, String> response = new HashMap<>();
      response.put("username", email);
      response.put("firstname", firstname);
      response.put("lastname", lastname);
      response.put("role", role);

      return ResponseEntity.ok(response);
  }
  
}
