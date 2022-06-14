package com.exam.controller;

import com.exam.entity.Role;
import com.exam.entity.User;
import com.exam.entity.UserRole;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {

        Set<UserRole> roles = new HashSet<>();

        user.setProfile("default.png");
        //encoding password with bcryptpassword encode
        //it codes the plain password
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = new Role();
        role.setRoleId(45L);
        role.setRoleName("NORMAL");

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        roles.add(userRole);
        return this.userService.createUser(user, roles);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) throws Exception {
        return this.userService.getUser(username);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) throws Exception {
        this.userService.deleteUser(userId);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) throws Exception {
        if (this.userRepository.findByUsername(user.getUsername())!=null)
        {
            User user2 = this.userService.getUser(user.getUsername());
            user2.setFirstName(user.getUsername());
            user2.setLastName(user.getLastName());
            user2.setPassword(user.getPassword());
            user2.setEmail(user.getEmail());
            user2.setProfile(user.getProfile());
            user2.setPhone(user.getPhone());
            return this.userService.updateUser(user2);
        }
        else{
            return null;
        }

    }
}




