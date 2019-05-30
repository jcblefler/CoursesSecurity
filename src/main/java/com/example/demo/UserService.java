package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Returns the currently logged in User object
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User user = userRepository.findByUsername(currentusername);
        return user;
    }


    // using the current user and radio button selected, set the User's Role and save the user in the UserRepository
    public void saveUser(User user, String role) {
        user.setRoles(Arrays.asList(roleRepository.findByRole(role)));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public void saveUser(User user) {
        user.setRoles(Arrays.asList(roleRepository.findByRole("USER")));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveAdmin(User user) {
        user.setRoles(Arrays.asList(roleRepository.findByRole("ADMIN")));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveStudent(User user) {
        user.setRoles(Arrays.asList(roleRepository.findByRole("STUDENT")));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}

