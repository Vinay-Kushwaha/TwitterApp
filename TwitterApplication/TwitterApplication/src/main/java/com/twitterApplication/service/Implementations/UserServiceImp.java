package com.twitterApplication.service.Implementations;

import com.twitterApplication.dto.request.UserDto;
import com.twitterApplication.exceptions.InvalidUserException;
import com.twitterApplication.exceptions.UserCreationException;
import com.twitterApplication.exceptions.UserNotFoundException;
import com.twitterApplication.model.User;
import com.twitterApplication.repositories.UserRepository;
import com.twitterApplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@Slf4j
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        try {
            User user = userRepository.save(modelMapper.map(userDto, User.class));
            return modelMapper.map(user, UserDto.class);
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage(), e);
            throw new UserCreationException("Failed to create user", e);
        }
    }

    @Override
    public Optional<User> findUserById(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                log.info("User not found with id: {}", id);
                throw new UserNotFoundException("User not found with id: " + id);
            }
            return user;
        } catch (UserNotFoundException e) {
            log.error("User not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving user by id: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve user by id", e);
        }
    }

    @Override
    public User findUserByName(String userName) {
        try {
            User user = userRepository.findByUserName(userName);
            if (user == null) {
                log.info("User not found with username: {}", userName);
                throw new UserNotFoundException("User not found with username: " + userName);
            }
            return user;
        } catch (UserNotFoundException e) {
            log.error("User not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving user by username: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve user by username", e);
        }
    }

    @Override
    public Boolean followUser(String userName, String followingUserName) {
        try {
            User user = userRepository.findByUserName(userName);
            if (user == null) {
                throw new UserNotFoundException("User not found with username: " + userName);
            }

            User followingUser = userRepository.findByUserName(followingUserName);
            if (followingUser == null) {
                throw new UserNotFoundException("Following user not found with username: " + followingUserName);
            }

            Set<User> followers = followingUser.getFollowers();
            followers.add(user);
            followingUser.setFollowers(followers);

            Set<User> following = user.getFollowing();
            following.add(followingUser);
            user.setFollowing(following);

            userRepository.save(followingUser);
            userRepository.save(user);

            return true;
        } catch (UserNotFoundException e) {
            log.error("User not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error following user: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to follow user", e);
        }
    }

    @Override
    public List<User> getFollowers(User user) {
        try {
            if (user == null) {
                log.info("Invalid user: user is null");
                throw new InvalidUserException("Invalid user: user is null");
            }

            return List.copyOf(user.getFollowers());
        } catch (InvalidUserException e) {
            log.error("Invalid user: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving followers: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve followers", e);
        }
    }
}
