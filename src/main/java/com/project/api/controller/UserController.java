package com.project.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.project.api.service.UserService;


import com.project.api.model.User;



@RestController 
@RequestMapping(path = "api/users")
public class UserController {
  
  private final UserService userService;
  
  public UserController(UserService userService){
    this.userService = userService;
  }

  @GetMapping
  public List<User> getUsers() {
      return userService.getUsers();
  }

  @PostMapping()
  public ResponseEntity<String> registerNewUser(@RequestBody User users){
    userService.register(users);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"User added successfully\"}");

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

  @PostMapping("/login")
    public ResponseEntity<String> login(
      @RequestBody Map<String, String> userData
      ) {
        String email = userData.get("email");
        String password = userData.get("password");
        String response = userService.login(email, password);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
