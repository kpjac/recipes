package com.fsd01.recipes.controller;

import com.fsd01.recipes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserRepository getUserRepo() {
        return userRepo;
    }

    public void addUser(User user) {
        userRepo.save(user);
    }
}
