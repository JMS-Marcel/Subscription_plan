package com.project.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

  @PostMapping()
  public ResponseEntity<String> addNewFaq(@RequestBody Faq faq){
    faqService.addNewFaq(faq);
    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Faq added successfully\"}");
  }

  @PutMapping(path = "{faqId}")
  public ResponseEntity<String> updateFaq(
    @PathVariable("faqId") Long faqId,
    @RequestParam(required = false) String question,
    @RequestParam(required = false) String answer
  ){
    faqService.updateFaq(faqId, question, answer);
    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Faq updated successfully\"}");
  }
}
