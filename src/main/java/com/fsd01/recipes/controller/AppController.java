package com.fsd01.recipes.controller;

import com.fsd01.recipes.model.Recipe;
import com.fsd01.recipes.model.RecipeUserDetails;
import com.fsd01.recipes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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

    private User currentUser;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof RecipeUserDetails) {
            String name = ((RecipeUserDetails)principal).getUser().getDisplayName();
            model.addAttribute("loggedInUser", name);
        }
        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());

        return "register";
    }

    @PostMapping("/processRegister")
    public String processRegister(HttpServletRequest request, @Valid User user, BindingResult result, Model model) {

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

        if (!user.getPasswordEntry().equals(user.getPasswordRepeat())) {
            otherErrors.add("Passwords do not match");
        }

        if (!otherErrors.isEmpty()) {
            model.addAttribute("otherErrors", otherErrors);
            return "register";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPasswordEntry());
        user.setPassword(encodedPassword);

        userService.addUser(user);

        try {
            request.login(user.getEmail(), user.getPasswordEntry());
        } catch (ServletException e) {
            System.out.println(e.getMessage());
        }

        return "accountCreated";
    }

    @GetMapping("/addRecipe")
    public String showRecipeForm(Model model) {

        model.addAttribute("recipe", new Recipe());

        return "addRecipe";
    }

    @PostMapping("/processRecipe")
    public String processRecipe(@Valid Recipe recipe, Model model) {
        model.addAttribute("recipe", recipe);
        return "recipeAdded";
    }




}
