package com.project.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.api.model.Packages;
public interface  PackageRepository extends JpaRepository<Packages, Long> {
  
}
