package com.twitterApplication.controller;

import com.twitterApplication.dto.request.TweetDto;
import com.twitterApplication.dto.request.UserDto;
import com.twitterApplication.service.Implementations.UserServiceImp;
import com.twitterApplication.service.TweetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImp userService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TweetService tweetService;

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        try {
            return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/{userName}/follow/{followingUserName}")
    public ResponseEntity<Boolean> followUser(@PathVariable String userName, @PathVariable String followingUserName) {
        return new ResponseEntity<>(userService.followUser(userName, followingUserName), HttpStatus.OK);
    }

    @GetMapping("/homeTimeLine/{userName}")
    public ResponseEntity<List<TweetDto>> getUserFeed(@PathVariable String userName) {
        return new ResponseEntity<>(tweetService.getHomeTimeLine(userName), HttpStatus.OK);
    }
}
