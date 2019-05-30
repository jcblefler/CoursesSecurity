package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner{
    // Instantiate both the user and role repository to invoke constructor methods
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
        Run method will be executed after the application context is
        loaded and right before the Spring Application run method is
        completed.
     */
    @Override
    public void run(String... strings) throws Exception{
        System.out.println("Loading data...");

        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("STUDENT"));

        Role userRole = roleRepository.findByRole("USER");
        Role adminRole = roleRepository.findByRole("ADMIN");
        Role studentRole = roleRepository.findByRole("STUDENT");

        User user = new User("bob@bob.com",passwordEncoder.encode("password"),"Bob","Bobberson",true,"bob");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        user = new User("admin@adm.com",passwordEncoder.encode("password"),"Admin","User",true,"admin");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);

        user = new User("admin@adm.com",passwordEncoder.encode("password"),"Admin","User",true,"admin");
        user.setRoles(Arrays.asList(studentRole));
        userRepository.save(user);

        Course course = new Course("Astrophysics", "Neil D Tyson", "Just a course on stars", 3);
        courseRepository.save(course);

        course = new Course("Calculus", "Carol Henley", "Rate of Change of the Rate of Change", 3);
        courseRepository.save(course);

        course = new Course("Freshman English", "Gereldine Pegram", "Learn your language children", 3);
        courseRepository.save(course);
    }
}
