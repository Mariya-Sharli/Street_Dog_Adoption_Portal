package com.js.Paws_Cares_Portal.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.js.Paws_Cares_Portal.Entity.Dog;
import com.js.Paws_Cares_Portal.repository.DogRepository;

@Service
public class DogService {
	
	@Autowired
	private DogRepository repo;
	//fetch
	public void getAllDogs(ModelMap map)
	{
		map.put("dogs", repo.findAll());
		
	}
	
	//add
	public void addDog(Dog dog,ModelMap map)
	{
		 repo.save(dog);
		 map.put("pass", "Dog added Successfully!");
	}
	
	//delete
	public void deleteDog(Long id,ModelMap map)
	{
		
		  repo.deleteById(id);
		  map.put("pass", "Dog deleted successfully");
	}
	//edit
	public void edit(Long id, ModelMap map) {
	    Dog dog = repo.findById(id).orElse(null);
	    map.put("dog", dog);
	}

	//update
	public void updateDog(Dog dog,ModelMap map)
	{
		 repo.save(dog);
		 map.put("pass", "Dog updated successfully");
	}
	public Dog findDogById(Long id) {
	    return repo.findById(id).orElse(null);
	}
	
	//for the saveingg dog after adoption=true
	public void saveDog(Dog dog) {
	    repo.save(dog);
	}

	
	

}
