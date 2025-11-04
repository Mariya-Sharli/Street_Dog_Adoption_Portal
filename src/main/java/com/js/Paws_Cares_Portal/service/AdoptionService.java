package com.js.Paws_Cares_Portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.js.Paws_Cares_Portal.Entity.Adoption;
import com.js.Paws_Cares_Portal.Entity.Dog;
import com.js.Paws_Cares_Portal.repository.AdoptionRepository;

@Service
public class AdoptionService {
	
	@Autowired
	private AdoptionRepository repo;
	@Autowired
	private DogService dogservice;
	
	//save adoption request
	public void requestadoption(Adoption adoption,ModelMap map)
	{
		repo.save(adoption);
		if(adoption.getDog() !=null)
		{
			Dog dog = adoption.getDog();
			dog.setAdopted(true);
			dogservice.saveDog(dog);
			
		}
		map.put("pass", "Adoption Request submitted successfully");
	}
	
	//fetch all adoption
	public void fetchAllAdoption(ModelMap map)
	{
		map.put("adoptions", repo.findAll());
	}
	
	//fetch pending adoption
	public void fetchPendingAdoption(ModelMap map)
	{
		map.put("pending",repo.findByStatus("Pending"));
	}
	
	//approve adoption
	
	public void approveAdoption(Long id,ModelMap map)
	{
		Adoption adoption = repo.findById(id).orElse(null);
		if(adoption !=null)
		{
			adoption.setStatus("Approved");
			repo.save(adoption);
			map.put("pass", "Adoption Approved!");
		}
	}
	
	//request Adoption
	
	public void rejectAdoption(Long id,ModelMap map)
	{
		Adoption adoption = repo.findById(id).orElse(null);
		if(adoption !=null)
		{
			adoption.setStatus("Rejected");
			repo.save(adoption);
			map.put("pass", "Adoption Rejected");
		}
	}
	
//	public void viewApprovedAdoptionInWork(Long id,ModelMap map)
//	{
//		Adoption adopt = repo.findById(id).orElse(null);
////		adopt.setStatus("Approved");
//		if(adopt.getStatus() == "Approved")
//		{
//			repo.save(adopt);
//			map.put("pass", "user can view only the Accepted adoption in work");
//		}
//		
//	}
	public void fetchApprovedAdoption(ModelMap map)
	{
		map.put("app",repo.findByStatus("Approved"));
	}

}
