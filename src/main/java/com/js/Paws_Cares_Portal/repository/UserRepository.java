package com.js.Paws_Cares_Portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.js.Paws_Cares_Portal.Entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
}
