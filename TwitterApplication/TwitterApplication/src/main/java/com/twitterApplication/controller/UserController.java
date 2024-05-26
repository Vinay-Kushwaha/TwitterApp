package com.twitterApplication.controller;

import com.twitterApplication.model.User;
import com.twitterApplication.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @PostMapping
    public User createUser(@RequestParam String name, @RequestParam String email) {
        User u1 = new User(name,email);
        return userRepository.save(u1);
    }

}
