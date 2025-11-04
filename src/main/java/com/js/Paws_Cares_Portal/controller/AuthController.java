
package com.js.Paws_Cares_Portal.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.js.Paws_Cares_Portal.Entity.Adoption;
import com.js.Paws_Cares_Portal.Entity.Dog;
import com.js.Paws_Cares_Portal.Entity.SponsorDog;
import com.js.Paws_Cares_Portal.Entity.User;
import com.js.Paws_Cares_Portal.Entity.Volunter;
import com.js.Paws_Cares_Portal.repository.DogRepository;
import com.js.Paws_Cares_Portal.service.AdoptionService;
import com.js.Paws_Cares_Portal.service.DogService;
import com.js.Paws_Cares_Portal.service.UserService;
import com.js.Paws_Cares_Portal.service.VolunteerService;

import jakarta.servlet.http.HttpSession;
@Controller
public class AuthController {

	@Autowired
	private AdoptionService  adoptionService;
	 @Autowired
	    private DogRepository dogRepo;
	 
	 @Autowired
	 private DogService dogService;
	 
	 @Autowired
	 private UserService userservice;
	 
	 @Autowired
	 private VolunteerService volservice;
	 
	 @GetMapping("/user/UserHome")
		public String LoadUserHome()
		{
			
			return "user/UserHome";
		}
	    @GetMapping("/user/about")
	    public String LoadAbout() {
	        return "user/about";
	    }

//	    @GetMapping("/user/work")
//	    public String LoadWork() {
//	        return "user/about";
//	    }
	    
//	    @GetMapping("/user/adopt")
//	    public String LoadAdopt() {
//	        return "user/adopt";
//	    }
	    
	    @GetMapping("/user/adopt")
	    public String showAvailableDogs(ModelMap map)
	    {
	    	map.put("dogs", dogRepo.findByAdoptedFalse());
	    	return "user/adopt";
	    }
	   
	    @GetMapping("/user/adoptForm/{dogId}")
	    public String showAdoptionForm(@PathVariable Long dogId, HttpSession session, ModelMap map) {
	        Dog dog = dogService.findDogById(dogId);
	        User user = (User) session.getAttribute("loggedInUser");
	        

	        if (user == null) {
	            map.put("error", "Please login first!");
	            return "redirect:/login";
	        }
	        map.put("dog", dog);
	        map.put("user", user);
	        map.put("adoption", new Adoption());
	        
	        return "user/adoptForm";
	    }


	    @PostMapping("/user/adopt")
	    public String submitAdoption(@ModelAttribute Adoption adoption, ModelMap map) {
	    	//fetch the full Dog Entity
	    	Long dogId = adoption.getDog().getId();
	    	//safe check
	    	if(dogId == null)
	    	{
	    		map.put("error", "Invalid Dog Selected");
	    		return "user/adoptForm";
	    	}
	    	Dog dog=dogService.findDogById(dogId);
	    	
	    	//set the dog in adoption
	    	adoption.setDog(dog);
	    	//save Adoption and updated Dog
	        adoptionService.requestadoption(adoption, map);
	    
	        return "user/adoptSuccess";
	    }

	    
	   
	    @GetMapping("/user/volunteer")
	    public String LoadVolunteer(HttpSession session,ModelMap map) {
	    	User user = (User) session.getAttribute("loggedInUser");
	    	 if (user == null) {
		            map.put("error", "Please login first!");
		            return "redirect:/login";
		        }
		       
		        map.put("user", user);
	        return "user/volunteer";
	    }
	    
	    @PostMapping("/user/volunteer")
	    public  String submitVolunteerForm(@ModelAttribute Volunter volunteer,HttpSession session,ModelMap map)
	    {
	    	User user = (User) session.getAttribute("loggedInUser");
	    	if(user == null)
	    	{
	    		map.put("error", "Please Login!");
	    		return "redirect:/login";
	    		
	    	}
	    	volunteer.setUser(user);
	    	volservice.registerVaolunteer(volunteer, map);
	    	return "user/volunteerSuccess";
	    	
	    }

}





   

