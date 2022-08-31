package com.blogapplication.blogappapis.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blogapplication.blogappapis.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmailId(String email);
}
