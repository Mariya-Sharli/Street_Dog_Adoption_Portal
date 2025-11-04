package com.js.Paws_Cares_Portal.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.js.Paws_Cares_Portal.Entity.Dog;
import com.js.Paws_Cares_Portal.Entity.Volunter;
import com.js.Paws_Cares_Portal.repository.DonationRepository;
import com.js.Paws_Cares_Portal.repository.VolunterRepository;
import com.js.Paws_Cares_Portal.repository.sponsorDogRepository;
import com.js.Paws_Cares_Portal.service.AdoptionService;
import com.js.Paws_Cares_Portal.service.DogService;

@Controller
public class AdminController {
	
	@Autowired
	private DogService service;
	
	@Autowired
	private VolunterRepository volrepo;
	
	@Autowired
	private AdoptionService adservice;
	 
	@Autowired
	private sponsorDogRepository sporepo;
	
	@Autowired
	private DonationRepository drepo;
	
	@GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }
	
	@GetMapping("/admin/addDogs")
    public String addDog() {
        return "/admin/addDogs";  // corresponds to admin/pendingAdoption.html
    }
	 @PostMapping("/admin/addDogs")
	    public String createDog(@ModelAttribute Dog dog,ModelMap map)
	    {
	    	service.addDog(dog, map);
	    	map.put("pass", "Dog added Successfully");
	    	return "/admin/DogCrud";
	    }
	
	@GetMapping("/admin/viewDog")
	public String view(ModelMap map) {
		service.getAllDogs(map);
		return "/admin/viewDog";		
	}
	 
    @GetMapping("/admin/DogCrud")
    public String adminDogCrud() {
        return "admin/DogCrud";
    }
    
    

	@GetMapping("/delete/{id}")
		
			public String delete(@PathVariable Long id,ModelMap map)
			{
				service.deleteDog(id, map);
				return view(map);
				
			}
	@GetMapping("/admin/edit/{id}")
	public String edit(@PathVariable Long id, ModelMap map) {
	    service.edit(id, map);
	    return "admin/edit";  // ✅ Correct view name
	}

	@PostMapping("/admin/edit")
	public String edit(@ModelAttribute Dog dog, ModelMap map) {
	    service.updateDog(dog, map);
	    return view(map);
	}

   
    
    
    @GetMapping("admin/viewSponsors")
    public String  viewSponsors(ModelMap map)
    {
    	map.put("sponsors", sporepo.findAll());
    	return "admin/viewSponsors";
    }
    	
//    @GetMapping("admin/viewVolunteer")
//    public String viewVolunteer()
//    {
//    	return "admin/viewVolunteer";
//    }
    
    
    @GetMapping("admin/viewDonation")
    public String viewVolunteerPage(ModelMap map) {
    	map.put("donations", drepo.findAll());
        return "admin/viewDonation"; // corresponds to admin/pendingVolunteer.html
    }
    
    //view volunteer
    @GetMapping("/admin/viewVolunteer")
    public String viewVolunteers(ModelMap map)
    {
    	List<Volunter> list = volrepo.findAll();
    	map.put("volunteer", list);
    	return "/admin/viewVolunteer";
    }
    
    @GetMapping("/admin/acceptVolunteer/{id}")
    public String acceptVolunteer(@PathVariable Long id,ModelMap map) {
        Volunter v = volrepo.findById(id).orElse(null);
        if (v != null) {
            v.setStatus("Accepted");
            volrepo.save(v);
            map.put("message", "Volunteer request Successfully");
        }
        List<Volunter> list = volrepo.findAll();
        map.put("volunteer", list);
        return "redirect:/admin/viewVolunteer";
    }
    
    @GetMapping("admin/rejectVolunteer/{id}")
    
    public String rejectVolunteer(@PathVariable Long id,ModelMap map)
    {
    	Volunter v = volrepo.findById(id).orElse(null);
    	if(v!=null)
    	{
    		v.setStatus("Reject");
    		volrepo.save(v);
    		map.put("message", "Volunteer request Rejected");
    	}
    	List<Volunter> list = volrepo.findAll();
        map.put("volunteer", list);
        return "redirect:/admin/viewVolunteer";
    }
    
   @GetMapping("admin/viewAdoption")
   public String viewAdoption(ModelMap map)
   {
	   adservice.fetchAllAdoption(map);
	   return "admin/viewAdoption";
   }
   
   @GetMapping("admin/acceptAdoption/{id}")
   public String AcceptAdoption(@PathVariable Long id,ModelMap map)
   {
	   adservice.approveAdoption(id, map);
	   return "redirect:/admin/viewAdoption"; 
   }
   
   @GetMapping("admin/rejectAdoption/{id}")
   public String RejectAdoption(@PathVariable Long id,ModelMap map)
   {
	   adservice.rejectAdoption(id, map);
	   return "redirect:/admin/viewAdoption";
   }
    
   @GetMapping("user/work")
   public String viewadoptioninwork(ModelMap map)
   {
	   adservice.fetchApprovedAdoption(map);
	   return "user/work";
   }

}
