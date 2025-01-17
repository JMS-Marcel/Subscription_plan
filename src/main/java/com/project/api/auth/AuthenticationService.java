package com.project.api.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.api.config.JwtService;
import com.project.api.model.Role;
import com.project.api.model.Token;
import com.project.api.model.User;
import com.project.api.model.TokenType;
import com.project.api.repository.TokenRepository;
import com.project.api.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;

  private final TokenRepository tokenRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;
  
  public AuthenticationResponse register(RegisterRequest request){
    var user = com.project.api.model.User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);

    return AuthenticationResponse.builder()
    .token(jwtToken)
    .build();
  }

  public void saveUserToken(User user, String jwtToken){
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .revoked(false)
        .expired(false)
        .build();
    tokenRepository.save(token);
  }

  public void revokeAllUserToken(User user){
    var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
    if(validUserTokens.isEmpty())
      return;
      validUserTokens.forEach(t -> {
        t.setExpired(true);
        t.setRevoked(true);
      });
      tokenRepository.saveAll(validUserTokens);
    
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request){
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword()
      )
    );
    var user = repository.findByEmail(request.getEmail())
          .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserToken(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
          .token(jwtToken)
          .build();
  }
}
