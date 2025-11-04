package com.js.Paws_Cares_Portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.js.Paws_Cares_Portal.Entity.Dog;
import com.js.Paws_Cares_Portal.Entity.Donation;
import com.js.Paws_Cares_Portal.Entity.SponsorDog;
import com.js.Paws_Cares_Portal.Entity.User;
import com.js.Paws_Cares_Portal.repository.UserRepository;
import com.js.Paws_Cares_Portal.repository.sponsorDogRepository;
import com.js.Paws_Cares_Portal.service.DogService;

import jakarta.servlet.http.HttpSession;

@Controller
public class SponsorController {
	
	@Autowired
	private sponsorDogRepository sponrepo;
	
	@Autowired
	private UserRepository repo;
	
	 @Autowired
	 private DogService dService;
	 
	 @GetMapping("/user/sponsorDog")
	    public String LoadSponsor(HttpSession session,ModelMap map) {
//	    	
	    	 User user = (User) session.getAttribute("loggedInUser");

		        if (user == null) {
		            map.put("error", "Please login first!");
		            return "redirect:/login";
		        }
		        
		        map.put("user", user);
		        map.put("sponsorDog", new SponsorDog());
	        return "user/sponsorDog";
	    }
	 @PostMapping("user/SponsorDog")

		public String ConfirmDonation(
				@ModelAttribute SponsorDog sponsorr,
		                              @RequestParam double amount, 
		                              HttpSession session, 
		                              ModelMap map) {
		    
		    User user = (User) session.getAttribute("loggedInUser");
		    
		    
	    	//safe check
	    	if(user == null)
	    	{
	    		map.put("error", "Please login");
	    		return "user/login";
	    	}
	    	//get dog id
	    	Long dogId = sponsorr.getDog().getId();
	    	 if (dogId == null) {
	    	        map.put("error", "Invalid Dog ID selected");
	    	        return "user/sponsorDog";
	    	    }
	    	 
	    	  // Fetch the Dog object from DB
	    	    Dog dog = dService.findDogById(dogId);
	    	    if (dog == null) {
	    	        map.put("error", "Dog not found!");
	    	        return "user/sponsorDog";
	    	    }
	    	
	    	
		
		         
		         sponsorr.setUser(user);
		         sponsorr.setAmount(amount); // 'amt' from HTML now maps correctly
		        sponsorr.setDog(dog);
		         sponrepo.save(sponsorr);

		        map.put("user", user);
		        map.put("message", "Payment Successful! Sponsor saved.");
		    
		    return "user/SponsorDog"; // stay on the same page after save
		}


}
