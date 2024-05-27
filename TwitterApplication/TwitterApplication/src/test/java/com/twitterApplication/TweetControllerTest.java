package com.twitterApplication;

import com.twitterApplication.controller.TweetController;
import com.twitterApplication.dto.request.TweetDto;
import com.twitterApplication.dto.request.UpdateTweetDto;
import com.twitterApplication.dto.response.TweetResponseDto;
import com.twitterApplication.service.TweetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class TweetControllerTest {
    @InjectMocks
    private TweetController tweetController;

    @Mock
    private TweetService tweetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testCreatePost() {
//        TweetResponseDto tweetDto = new TweetResponseDto();
//        when(tweetService.createTweet(any(TweetDto.class))).thenReturn(tweetDto);
//
//        ResponseEntity<TweetResponseDto> response = tweetController.createPost(tweetDto);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(tweetDto, response.getBody());
//    }

    @Test
    void testUpdatePost() {
        UpdateTweetDto updateTweetDto = new UpdateTweetDto();
        TweetDto tweetDto = new TweetDto();
        when(tweetService.updateTweet(any(UpdateTweetDto.class))).thenReturn(tweetDto);

        ResponseEntity<TweetDto> response = tweetController.updatePost(updateTweetDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tweetDto, response.getBody());
    }

    @Test
    void testDeletePost() {
        when(tweetService.deleteTweet(anyLong())).thenReturn(true);

        ResponseEntity<Boolean> response = tweetController.deletePost(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void testGetUserTimeLine() {
        TweetResponseDto tweetResponseDto = new TweetResponseDto();
        List<TweetResponseDto> tweetResponseList = Arrays.asList(tweetResponseDto);
        when(tweetService.getUserTimeLine(anyLong())).thenReturn(tweetResponseList);

        ResponseEntity<List<TweetResponseDto>> response = tweetController.getUserTimeLine(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tweetResponseList, response.getBody());
    }
}
