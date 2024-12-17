package com.project.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.project.api.service.PackageService;

import com.project.api.model.Package;



@RestController
@RequestMapping(path = "api/package")
public class PackageController {
  private final PackageService packageService;

  public PackageController(PackageService packageService){
    this.packageService = packageService;
  }

  @GetMapping
  public List<Package> getPackage() {
      return packageService.getPackage();
  }
  
  
}
