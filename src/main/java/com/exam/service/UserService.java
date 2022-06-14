package com.exam.service;

import com.exam.entity.User;
import com.exam.entity.UserRole;



import org.springframework.stereotype.Service;

import java.util.Set;



public interface UserService {

    //creating user

    //public written in user createUser removed now
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;
    //get user details
    public User getUser(String username) throws Exception;
    //delete the user
    public void deleteUser(Long userId) throws Exception;


    public User updateUser(User user2) throws Exception;
}
