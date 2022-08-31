package com.blogapplication.blogappapis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapplication.blogappapis.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
