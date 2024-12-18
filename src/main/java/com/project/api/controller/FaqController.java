package com.project.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.model.Faq;
import com.project.api.service.FaqService;

@RestController
@RequestMapping(path = "api/faq")
public class FaqController {
  private final FaqService faqService;

  public FaqController(FaqService faqService){
    this.faqService = faqService;
  }

  @GetMapping
  public List<Faq> getFaq(){
    return faqService.getFaq();
  }
}
