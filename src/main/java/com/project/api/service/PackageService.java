package com.project.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.api.repository.PackageRepository;

import com.project.api.model.Package;
@Service
public class PackageService {
  private final PackageRepository packageRepository;

  public PackageService(PackageRepository packageRepository){
    this.packageRepository = packageRepository;
  }
  public List<Package> getPackage(){
    return packageRepository.findAll();
  }
}
