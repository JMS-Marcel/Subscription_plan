package com.project.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import com.project.api.model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{
  
  @Query("""
      select t from Token t inner join User u on t.user.id = u.id
      where u.id = :userId and (t.expired = false or t.revoked = false)
      """)
  List<Token> findAllValidTokensByUser(Long userId);

  Optional<Token> findByToken(String token);
}
