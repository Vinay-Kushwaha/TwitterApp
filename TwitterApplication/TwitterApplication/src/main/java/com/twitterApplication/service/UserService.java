package com.twitterApplication.service;

import com.twitterApplication.dto.request.UserDto;
import com.twitterApplication.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDto createUser(UserDto userDto);
    Optional<User> findUserById(Long id);
    User findUserByName(String userName);
    Boolean followUser(String userName, String followingUserName);
    List<User> getFollowers(User user);
}
