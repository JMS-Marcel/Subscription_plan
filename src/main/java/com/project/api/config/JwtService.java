package com.project.api.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.project.api.model.User;


@Service
public class JwtService {

  public static final String SECRET_KEY = "0bab10776348a8fc4a5923df2b85f0cfa32429c37a22dc1017f736e480280c19";

public String extractFirstname(String token) {
    return extractClaim(token, claims -> claims.get("firstname", String.class));
}

public String extractLastname(String token) {
    return extractClaim(token, claims -> claims.get("lastname", String.class));
}

public String extractRole(String token) {
    return extractClaim(token, claims -> claims.get("role", String.class));
}
  
  public String extractUsername(String token){
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaims(token);

    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails){
     // Caster UserDetails en User pour accéder aux propriétés supplémentaires
     User user = (User) userDetails;

     // Créer une map pour les claims supplémentaires
     Map<String, Object> extraClaims = new HashMap<>();
     extraClaims.put("firstname", user.getFirstname());
     extraClaims.put("lastname", user.getLastname());
     extraClaims.put("role", user.getRole());

    return generateToken(extraClaims, userDetails);
  }

  public String generateToken(
    Map<String, Object> extraClaims,
    UserDetails userDetails
  ){
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails){
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  public boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token){
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token){
    return Jwts
    .parser()
    .setSigningKey(getSignInKey())
    .build()
    .parseClaimsJws(token)
    .getBody();
  }
  
  private Key getSignInKey(){
     byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
