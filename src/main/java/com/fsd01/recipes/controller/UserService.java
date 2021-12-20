package com.fsd01.recipes.controller;

import com.fsd01.recipes.model.Category;
import com.fsd01.recipes.model.Recipe;
import com.fsd01.recipes.model.RecipeMade;
import com.fsd01.recipes.model.User;
import com.fsd01.recipes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    public UserRepository getUserRepo() {
        return userRepo;
    }

    public void addUser(User user) {
        userRepo.save(user);
    }

    public void updateUser(User user) {
        userRepo.save(user);
    }

}
