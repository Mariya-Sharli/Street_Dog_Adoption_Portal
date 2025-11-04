
package com.js.Paws_Cares_Portal.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.js.Paws_Cares_Portal.Entity.User;
import com.js.Paws_Cares_Portal.repository.DogRepository;
import com.js.Paws_Cares_Portal.service.AdoptionService;
import com.js.Paws_Cares_Portal.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService service;
    
    @Autowired
    private AdoptionService adoptservice;
  


    @GetMapping("/")
    public String loadIndex() {
        return "index";
    }

    @GetMapping("/register")
    public String loadRegister(ModelMap map) {
        map.put("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, ModelMap map) {
        service.registerUser(user);
        map.put("message", "Registered successfully as " + user.getRole() + "! Please login.");
        return "register";
    }

    @GetMapping("/login")
    public String loadLogin() {
        return "login";
    }

    

    // ✅ Step 1: Validate email + password → Generate OTP
    @PostMapping("/login")
    public String generateOtp(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              ModelMap map) {
//        boolean valid = service.validateCredentials(email, password);
//        if (!valid) {
//            map.put("error", "Invalid email or password!");
//            return "login";
//        }

        try {
            service.generateOtp(email);
            session.setAttribute("pendingEmail", email);
            map.put("email", email);
            return "verifyOtp";
        } catch (Exception e) {
            map.put("error", "User not found!");
            return "login";
        }
    }
   

    // ✅ Step 2: Verify OTP and redirect to respective dashboard
    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam String enteredOtp,
                            HttpSession session,
                            ModelMap map) {
        String email = (String) session.getAttribute("pendingEmail");
        if (email == null) {
            map.put("error", "Session expired. Please login again.");
            return "login";
        }

        if (service.verifyOtp(email, enteredOtp)) {
            User user = service.findByEmail(email).get();
            session.removeAttribute("pendingEmail");
            session.setAttribute("loggedInUser", user);

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
            	  
                return "redirect:/user/UserHome";
            }
        } else {
            map.put("error", "Invalid or expired OTP!");
            map.put("email", email);
            return "verifyOtp";
        }
    }
    
   
}

