package com.project.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.api.model.Faq;

@Repository
public interface  FaqRepository extends JpaRepository<Faq, Long> {
  
}
