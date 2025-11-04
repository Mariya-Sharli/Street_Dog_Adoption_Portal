package com.js.Paws_Cares_Portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.js.Paws_Cares_Portal.Entity.Dog;

public interface DogRepository extends JpaRepository<Dog, Long>{

	  List<Dog> findByAdoptedFalse();

}
