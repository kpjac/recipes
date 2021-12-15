package com.fsd01.recipes.controller;

import com.fsd01.recipes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/process_register")
    public String processRegister(@Valid User user, BindingResult result, Model model) {

        for (FieldError error : result.getFieldErrors()) {
            System.out.println(error.getDefaultMessage());
        }

        if (result.hasErrors()) {
            return "signup_form";
        }

        List<String> otherErrors = new ArrayList<>();

        Optional<User> userOptional = userService.getUserRepo().findUserByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            otherErrors.add("Email is already registered");
        }

        if (!user.getPassword().equals(user.getPasswordRepeat())) {
            otherErrors.add("Passwords do not match");
        }

        if (!otherErrors.isEmpty()) {
            model.addAttribute("otherErrors", otherErrors);
            return "register";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userService.addUser(user);

        return "register_success";
    }

}
