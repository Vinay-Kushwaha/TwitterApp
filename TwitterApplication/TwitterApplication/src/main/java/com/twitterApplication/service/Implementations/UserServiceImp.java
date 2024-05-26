package com.twitterApplication.service.Implementations;

import com.twitterApplication.dto.request.UserDto;
import com.twitterApplication.model.User;
import com.twitterApplication.repositories.UserRepository;
import com.twitterApplication.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userRepository.save(modelMapper.map(userDto, User.class));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findUserByName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Boolean followUser(String userName, String followingUserName) {
        User user = userRepository.findByUserName(userName);
        User followingUser = userRepository.findByUserName(followingUserName);
        Set<User> followers = followingUser.getFollowers();
        followers.add(user);
        followingUser.setFollowers(followers);


        Set<User> following = user.getFollowing();
        following.add(followingUser);
        user.setFollowing(following);

        userRepository.save(followingUser);
        userRepository.save(user);
        return true;
    }

    @Override
    public List<User> getFollowers(User user) {
//        User user = this.modelMapper.map(userDTO, User.class);
        return List.copyOf(user.getFollowers());
    }
}
