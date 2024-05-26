package com.twitterApplication;
import com.twitterApplication.controller.UserController;
import com.twitterApplication.dto.request.TweetDto;
import com.twitterApplication.dto.request.UserDto;
import com.twitterApplication.service.Implementations.UserServiceImp;
import com.twitterApplication.service.TweetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImp userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TweetService tweetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserDto userDto = new UserDto();
        userDto.setUserName("testUser");
        userDto.setEmail("test@example.com");
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);
        ResponseEntity<UserDto> result = userController.createUser(userDto);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(userDto, result.getBody());
        verify(userService, times(1)).createUser(userDto);
    }

    @Test
    void testFollowUser() {
        String userName = "user1";
        String followingUserName = "user2";
        when(userService.followUser(anyString(), anyString())).thenReturn(true);
        ResponseEntity<Boolean> result = userController.followUser(userName, followingUserName);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody());
        verify(userService, times(1)).followUser(userName, followingUserName);
    }

    @Test
    void testGetUserFeed() {
        String userName = "testUser";
        List<TweetDto> tweets = new ArrayList<>();
        when(tweetService.getHomeTimeLine(anyString())).thenReturn(tweets);
        ResponseEntity<List<TweetDto>> result = userController.getUserFeed(userName);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(tweets, result.getBody());
        verify(tweetService, times(1)).getHomeTimeLine(userName);
    }

}
