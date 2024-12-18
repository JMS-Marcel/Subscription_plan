package com.project.api.service;

import org.springframework.stereotype.Service;

import com.project.api.repository.FaqRepository;

import java.util.List;

import com.project.api.model.Faq;
@Service
public class FaqService {
  private final FaqRepository faqRepository;

  public FaqService(FaqRepository faqRepository){
    this.faqRepository = faqRepository;
  }

  public List<Faq> getFaq(){
    return faqRepository.findAll();
  }
}
