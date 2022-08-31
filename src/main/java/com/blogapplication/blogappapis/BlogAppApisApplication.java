package com.blogapplication.blogappapis;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blogapplication.blogappapis.config.AppConstants;
import com.blogapplication.blogappapis.entities.Role;
import com.blogapplication.blogappapis.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("rahul@123"));

		try {
			Role role1 = new Role();
			role1.setName("ROLE_NORMAL");
			role1.setId(AppConstants.NORMAL_USER);

			Role role2 = new Role();
			role2.setName("ROLE_ADMIN");
			role2.setId(AppConstants.ADMIN_USER);

			List<Role> roles = List.of(role1, role2);

			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(role -> System.out.println(role.getName()));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
