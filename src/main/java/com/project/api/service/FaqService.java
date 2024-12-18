package com.project.api.service;

import org.springframework.stereotype.Service;

import com.project.api.repository.FaqRepository;

import java.util.List;
import java.util.Objects;

import com.project.api.model.Faq;

import jakarta.transaction.Transactional;
@Service
public class FaqService {
  private final FaqRepository faqRepository;

  public FaqService(FaqRepository faqRepository){
    this.faqRepository = faqRepository;
  }

  public List<Faq> getFaq(){
    return faqRepository.findAll();
  }

  public void addNewFaq(Faq faq){
    faqRepository.save(faq);
  }

  @Transactional
  public void updateFaq(Long faqId, String question, String answer){
    Faq faq = faqRepository.findById(faqId).orElseThrow(
    ()-> new IllegalStateException("Faq with id " + faqId +" does not exists")
    );

    if(question != null && question.length() > 0 && !Objects.equals(faq.getQuestion(), question)){
      faq.setQuestion(question);
    }
    if(answer != null && answer.length() > 0 && !Objects.equals(faq.getAnswer(), answer)){
      faq.setAnswer(answer);
    }
  }
}
