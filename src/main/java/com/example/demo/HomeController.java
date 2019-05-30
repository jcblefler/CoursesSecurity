package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("courses", courseRepository.findAll());

        return "index";
    }

    @RequestMapping("/test")
    public String testStudent(Model model){
        model.addAttribute("user", new User());
        User user = new User("jim@bob.com",passwordEncoder.encode("password"),"Jim","Jimson",true,"jim");
        Role studentRole = roleRepository.findByRole("STUDENT");

        user.setRoles(Arrays.asList(studentRole));
        userRepository.save(user);

        return"test";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/secure")
    public String secure(Model model) {
        // Gets the currently logged in user and maps it to "user" in the Thymeleaf template
        model.addAttribute("user", userService.getCurrentUser());
        return "secure";
    }



    //Register Users

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("studentRole", Arrays.asList(roleRepository.findByRole("STUDENT")));

        return "/registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, @RequestParam("role") String role, BindingResult result, Model model) {
        model.addAttribute("user", user);

        if(result.hasErrors()) {
            return "registration";
        }
        else {
            userService.saveUser(user, role);
            model.addAttribute("message", "User Account Successfully Created");
        }
        return "redirect:/";
    }


    // Courses

    @GetMapping("/add")
    public String courseForm(Model model){
        model.addAttribute("course", new Course());
        return "courseform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Course course, BindingResult result){
        if (result.hasErrors()){
            return "courseform";
        }
        courseRepository.save(course);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("course", courseRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("course", courseRepository.findById(id).get());
        return "courseform";
    }

    @RequestMapping("/enroll/{id}")
    public String enrollCourse(@PathVariable("id") long id, Model model){
        // add course to current user
        userService.getCurrentUser().setCourses(Arrays.asList(courseRepository.findById(id).get()));

        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("user", userService.getCurrentUser());

        return "studentenrolled";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id){
        courseRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/student")
    public String studentPage(Model model){
        model.addAttribute("courses", courseRepository.findAll());
        model.addAttribute("user", userService.getCurrentUser());

        return "studentenrolled";
    }

}
