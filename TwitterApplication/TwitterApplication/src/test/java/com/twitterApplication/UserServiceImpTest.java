package com.twitterApplication;

import com.twitterApplication.dto.request.UserDto;
import com.twitterApplication.model.User;
import com.twitterApplication.repositories.UserRepository;
import com.twitterApplication.service.Implementations.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUser() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setUserName("testUser");
        userDto.setEmail("test@example.com");
        User user = new User();
        user.setId(1L);
        user.setUserName("testUser");
        user.setEmail("test@example.com");
        when(modelMapper.map(any(UserDto.class), eq(User.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDto);
        UserDto result = userService.createUser(userDto);
        assertEquals(userDto, result);
        verify(modelMapper, times(1)).map(userDto, User.class);
        verify(userRepository, times(1)).save(user);
        verify(modelMapper, times(1)).map(user, UserDto.class);
    }

    @Test
    void testFindUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUserName("testUser");
        user.setEmail("test@example.com");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        Optional<User> result = userService.findUserById(userId);
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        assertEquals("testUser", result.get().getUserName());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindUserByName() {
        // Given
        String username = "testUser";
        User user = new User();
        user.setId(1L);
        user.setUserName(username);
        user.setEmail("test@example.com");
        when(userRepository.findByUserName(anyString())).thenReturn(user);
        User result = userService.findUserByName(username);

        assertNotNull(result);
        assertEquals(username, result.getUserName());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).findByUserName(username);
    }

    @Test
    void testFollowUser() {
        String userName = "user1";
        String followingUserName = "user2";
        User user = new User();
        user.setId(1L);
        user.setUserName(userName);
        user.setFollowing(new HashSet<>());
        User followingUser = new User();
        followingUser.setId(2L);
        followingUser.setUserName(followingUserName);
        followingUser.setFollowers(new HashSet<>());
        when(userRepository.findByUserName(userName)).thenReturn(user);
        when(userRepository.findByUserName(followingUserName)).thenReturn(followingUser);
        Boolean result = userService.followUser(userName, followingUserName);
        assertTrue(result);
        assertTrue(followingUser.getFollowers().contains(user));
        assertTrue(user.getFollowing().contains(followingUser));
        verify(userRepository, times(1)).findByUserName(userName);
        verify(userRepository, times(1)).findByUserName(followingUserName);
        verify(userRepository, times(1)).save(followingUser);
        verify(userRepository, times(1)).save(user);
    }
}

