
package com.js.Paws_Cares_Portal.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.js.Paws_Cares_Portal.Entity.User;
import com.js.Paws_Cares_Portal.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // Register user or admin
    public User registerUser(User user) {
        if (user.getRole() == null || user.getRole().isEmpty())
            user.setRole("USER");
        return repo.save(user);
    }

    // Validate email + password before generating OTP
//    public boolean validateCredentials(String email, String password) {
//        return repo.findByEmail(email)
//                   .map(u -> u.getPassword().equals(password))
//                   .orElse(false);
//    }

    // Generate OTP only for valid email
    public String generateOtp(String email) {
        User user = repo.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        repo.save(user);

        System.out.println("OTP for " + email + " is " + otp);
        return otp;
    }

    // Verify OTP
    public boolean verifyOtp(String email, String enteredOtp) {
        User user = repo.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getOtp() != null && user.getOtp().equals(enteredOtp)) {
            LocalDateTime now = LocalDateTime.now();
            if (user.getOtpGeneratedTime() != null &&
                user.getOtpGeneratedTime().isAfter(now.minusMinutes(2))) {
                return true;
            }
        }
        return false;
    }

    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }
    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }
}

