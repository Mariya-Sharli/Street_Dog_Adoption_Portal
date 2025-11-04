package com.js.Paws_Cares_Portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.js.Paws_Cares_Portal.Entity.Adoption;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
	
	List<Adoption> findByStatus(String status);

}
