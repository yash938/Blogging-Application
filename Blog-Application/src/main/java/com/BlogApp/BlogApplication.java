package com.BlogApp;

import com.BlogApp.entity.Role;
import com.BlogApp.entity.User;
import com.BlogApp.repository.RoleRepo;
import com.BlogApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role admin = roleRepo.findByName("ROLE_ADMIN").orElse(null);
		Role user1 = roleRepo.findByName("ROLE_USER").orElse(null);

		if (admin == null) {
			admin = new Role();
			admin.setName("ROLE_ADMIN");
			roleRepo.save(admin);
		}

		if (user1 == null) {
			user1 = new Role();
			user1.setName("ROLE_USER");
			roleRepo.save(user1);
		}

		User user = userRepo.findByEmail("shubham123@gmail.com").orElse(null);
		if (user == null) {
			user = new User();
			user.setName("shubham Sharma");
			user.setEmail("shubham123@gmail.com");
			user.setPassword("shubham");
			user.setAbout("I am a java teacher");

			List<Role> roles = new ArrayList<>();
			roles.add(admin);
			roles.add(user1);
			user.setRoles(List.of(admin));
			userRepo.save(user);
			System.out.println("User is created successfully");
		} else {
			System.out.println("User already exists");
		}
	}
}
