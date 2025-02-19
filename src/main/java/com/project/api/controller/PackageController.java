package com.project.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.project.api.model.Packages;
import com.project.api.service.PackageService;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/package")
public class PackageController {
  private final PackageService packageService;

  

  public PackageController(PackageService packageService) {
    this.packageService = packageService;
  }

  @GetMapping
  public List<Packages> getPackage() {
    return packageService.getPackage();
  }

  @PostMapping()
  public ResponseEntity<String> addPackage(
      @RequestParam("name") String name,
      @RequestParam("description") String description,
      @RequestParam("price") Double price,
      @RequestParam(value = "image", required = false) MultipartFile image) {

    try {
      // Create a new Packages object
      Packages pkg = new Packages();
      pkg.setName(name);
      pkg.setPrice(price);
      pkg.setDescription(description);

      // Check if an image was provided
      if (!image.isEmpty()) {
        pkg.setImage(image.getBytes());
      }
      
      packageService.addPackage(pkg);

      return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Package successfully\"}");
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("{\"message\":\"Failed to upload image: " + e.getMessage() + "\"}");
    }
  }

  @PutMapping(path = "{packageId}")
  public ResponseEntity<String> updatePackage(
      @PathVariable("packageId") Long packageId,
      @RequestBody Map<String, String> packageData) {

    String name = packageData.get("name");
    Double price = null;

    if (packageData.get("price") != null) {
      price = Double.parseDouble(packageData.get("price"));
    }

    packageService.updatePackage(packageId, name, price);

    return ResponseEntity.status(HttpStatus.CREATED).body("{\"message\":\"Package updated successfully\"}");
  }

  @DeleteMapping(path = "{packageId}")
  public ResponseEntity<String> deletePackage(@PathVariable("packageId") Long packageId) {
    packageService.deletePackage(packageId);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body("{\"message\":\"Package id " + packageId + " deleted successfully\"}");
  }

  @GetMapping("/{packageId}/details")
  public Packages getDetails(@PathVariable("packageId") Long packageId) {
    return packageService.getDetails(packageId);
  }

      /**
     * Endpoint to retrieve an image by package ID.
     *
     * @param id The ID of the package.
     * @return ResponseEntity containing the image bytes or an error message.
     */
    // @GetMapping("/image/{id}")
    // public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
    //     // Retrieve the package from the service layer
    //     Packages pkg = packageService.getPackageById(id);

    //     if (pkg == null || pkg.getImage() == null || pkg.getImage().length == 0) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //     }

    //     return ResponseEntity.ok()
    //             .header("Content-Type", "image/jpeg") // Adjust content type as needed
    //             .body(pkg.getImage());
    // }
}
