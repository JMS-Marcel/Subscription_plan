package com.project.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.project.api.model.Packages;
import com.project.api.service.PackageService;




@RestController
@RequestMapping(path = "api/package")
public class PackageController {
  private final PackageService packageService;

  public PackageController(PackageService packageService){
    this.packageService = packageService;
  }

  @GetMapping
  public List<Packages> getPackage() {
      return packageService.getPackage();
  }

  @PostMapping()
  public ResponseEntity<String> addPackage(@RequestBody Packages packages){

    packageService.addPackage(packages);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Package successfully\"}");
  }

  @PutMapping(path = "{packageId}")
  public ResponseEntity<String> updatePackage(
    @PathVariable("packageId") Long packageId, 
    @RequestBody Map<String, String> packageData
  ){

    String name = packageData.get("name");
    Double price = null;

    if (packageData.get("price") != null) {
      price = Double.parseDouble(packageData.get("price"));
    }

    packageService.updatePackage(packageId, name, price);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Package updated successfully\"}");
  }

  @DeleteMapping(path = "{packageId}")
  public ResponseEntity<String> deletePackage(@PathVariable("packageId") Long packageId){
    packageService.deletePackage(packageId);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Package id " + packageId +" deleted successfully\"}");
  }

  @GetMapping("/{packageId}/details")
  public Packages getDetails(@PathVariable("packageId") Long packageId){
    return packageService.getDetails(packageId);
  }
}
  
  
  


