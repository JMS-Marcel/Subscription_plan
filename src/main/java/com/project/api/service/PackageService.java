package com.project.api.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.project.api.model.Packages;
import com.project.api.repository.PackageRepository;

import jakarta.transaction.Transactional;

@Service
public class PackageService {
  private final PackageRepository packageRepository;

  public PackageService(PackageRepository packageRepository){
    this.packageRepository = packageRepository;
  }
  public List<Packages> getPackage(){
    return packageRepository.findAll();
  }

  public void addPackage(Packages packages){
    packageRepository.save(packages);
  }

  @Transactional
  public void updatePackage(Long packageId, String name, Double price){
    Packages packages = packageRepository.findById(packageId).orElseThrow(
    ()-> new IllegalStateException("Package with id " + packageId +" does not exists")
    );
    if(name != null && name.length() > 0 && !Objects.equals(packages.getName(), name)){
      packages.setName(name);
    }
    if(price != null && !price.isNaN() && !Objects.equals(packages.getPrice(), price)){
      packages.setPrice(price);
    }
  }

  public void deletePackage(Long packageId){
    boolean exists = packageRepository.existsById(packageId);
    if(!exists){
      throw new IllegalStateException("PAckage with id : "+ packageId +" does not exists");
    }
    packageRepository.deleteById(packageId);
  }

  public Packages getDetails(Long packageId){
    Packages packages = packageRepository.findById(packageId).orElseThrow(
      ()-> new IllegalStateException("Package with id " + packageId +" does not exists")
    );

    return packages;

    
  }

  public Packages getPackageById(Long id) {
    return packageRepository.findById(id).orElse(null);
}
 
}
