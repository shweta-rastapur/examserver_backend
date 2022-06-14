package com.exam;

import com.exam.entity.Role;
import com.exam.entity.User;
import com.exam.entity.UserRole;

import com.exam.helper.UserFoundException;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class ExamserverApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userService;

	public static <user1> void main(String[] args) {
		SpringApplication.run(ExamserverApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
      try {
		  User user = new User();
		  user.setFirstName("Sweta");
		  user.setLastName("Rastapur");
		  user.setUsername("sweta1427");
		  user.setPassword(this.bCryptPasswordEncoder.encode("abc"));
		  user.setEmail("abc@gmail.com");
		  user.setProfile("default.png");

		  Role role1 = new Role();
		  role1.setRoleId(44L);
		  role1.setRoleName("ADMIN");

		  Set<UserRole> userRoleSet = new HashSet<>();
		  UserRole userRole = new UserRole();
		  userRole.setRole(role1);
		  userRole.setUser(user);
		  userRoleSet.add(userRole);

		  User user1 = this.userService.createUser(user, userRoleSet);
		  System.out.println(user1.getUsername());

	  }
	  catch(UserFoundException e)
	  {
		  e.printStackTrace();
	  }
	}


}
