package com.exam.service.impl;

import com.exam.entity.User;
import com.exam.entity.UserRole;
import com.exam.helper.UserFoundException;
import com.exam.helper.UserNotFoundException;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;



@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    //create user
    //for 1 user many roles(so set)
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        User local = this.userRepository.findByUsername(user.getUsername());
        if(local!=null)
        {
            System.out.println("User already present");
            throw new UserFoundException();
        }
        else {
            for(UserRole ur :userRoles) {
                roleRepository.save(ur.getRole());

                }
            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);
        }

        return local;
    }

    //getting user by username
    @Override
    public User getUser(String username) throws Exception {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(Long userId) throws Exception {
          this.userRepository.deleteById(userId);
    }

    @Override
    public User updateUser(User user2) throws Exception {
        return this.userRepository.save(user2);
    }
}

