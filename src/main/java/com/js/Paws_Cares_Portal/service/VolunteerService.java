package com.js.Paws_Cares_Portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.js.Paws_Cares_Portal.Entity.Volunter;
import com.js.Paws_Cares_Portal.repository.VolunterRepository;

@Service
public class VolunteerService {
	
	@Autowired
	private VolunterRepository repo;
	
	public void registerVaolunteer(Volunter volunteer,ModelMap map)
	{
		volunteer.setStatus("Pending");
		repo.save(volunteer);
		map.put("message", "Volunteer Form Submitted Successfully");
	}
	

}
