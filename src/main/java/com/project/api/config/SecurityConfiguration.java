package com.project.api.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final AuthenticationProvider authenticationProvider;

  private final JwtAuthenticationFilter jwtAuthFilter;

  private final LogoutHandler logoutHandler ;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
    try {
    http
      .csrf()
      .disable()
      .cors()
      .and()
      .authorizeHttpRequests()
      .requestMatchers("/api/v1/auth/**")
      .permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .logout()
      .logoutUrl("/api/v1/auth/logout")
      .addLogoutHandler(logoutHandler)
      .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
      ;
    } catch (Exception e) {
      System.err.println("Error configuring security filter chain: " + e.getMessage());
    }
      
      return  http.build();
  }


  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    try {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOriginPattern("*");
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList(
            "Authorization", 
            "Content-Type", 
            "Accept", 
            "X-Requested-With",
            "Cache-Control",
            "Access-Control-Allow-Headers",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));
    configuration.setExposedHeaders(Arrays.asList("Authorization"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  } catch (Exception e) {
    System.err.println("Error configuring CORS: " + e.getMessage());
    return null;
  }
}

  
}
