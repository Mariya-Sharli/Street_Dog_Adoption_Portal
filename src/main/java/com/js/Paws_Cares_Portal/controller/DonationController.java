package com.js.Paws_Cares_Portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.js.Paws_Cares_Portal.Entity.Donation;
import com.js.Paws_Cares_Portal.Entity.User;
import com.js.Paws_Cares_Portal.repository.DonationRepository;
import com.js.Paws_Cares_Portal.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class DonationController {
	
	@Autowired
	private DonationRepository dorepo;
	
	@Autowired
	private UserRepository userrepo;

	@GetMapping("/user/donation")
    public String LoadDonation(HttpSession session,ModelMap map) {
    	
    	 User user = (User) session.getAttribute("loggedInUser");

	        if (user == null) {
	            map.put("error", "Please login first!");
	            return "redirect:/login";
	        }
	       
	        map.put("user", user);
        return "user/donation";
    }


	
	@PostMapping("user/donation")

	public String ConfirmDonation(@RequestParam String type, 
	                              @RequestParam("amt") double amount, 
	                              HttpSession session, 
	                              ModelMap map) {
	    
	    User user = (User) session.getAttribute("loggedInUser");
	    Donation donation = new Donation();
	    if (user != null) {
	       
	        donation.setUser(user);
	        donation.setAmount(amount); // 'amt' from HTML now maps correctly
	        donation.setType(type);
	        dorepo.save(donation);

	        map.put("user", user);
	        map.put("message", "Payment Successful! Donation saved.");
	    } else {
	        map.put("error", "Please login first!");
	        return "redirect:/login";
	    }
	   

	    return "user/donation"; // stay on the same page after save
	}


}
