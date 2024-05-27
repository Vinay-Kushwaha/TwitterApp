package com.twitterApplication;

import com.twitterApplication.dto.request.TweetDto;
import com.twitterApplication.dto.request.UpdateTweetDto;
import com.twitterApplication.dto.response.TweetResponseDto;
import com.twitterApplication.model.Tweet;
import com.twitterApplication.repositories.TweetRepository;
import com.twitterApplication.service.Implementations.TweetServiceImpl;
import com.twitterApplication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class TweetServiceImplTest {
    @InjectMocks
    private TweetServiceImpl tweetService;

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testCreateTweet() {
//        TweetDto tweetDto = new TweetDto();
//        Tweet tweet = new Tweet();
//        when(modelMapper.map(any(TweetDto.class), eq(Tweet.class))).thenReturn(tweet);
//        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet);
//        when(modelMapper.map(any(Tweet.class), eq(TweetDto.class))).thenReturn(tweetDto);
//        TweetDto result = tweetService.createTweet(tweetDto);
//        assertNotNull(result);
//        assertEquals(tweetDto, result);
//        verify(tweetRepository, times(1)).save(tweet);
//    }

    @Test
    void testUpdateTweet() {
        UpdateTweetDto updateTweetDto = new UpdateTweetDto();
        updateTweetDto.setTweetId(1L);
        updateTweetDto.setContent("Updated content");
        Tweet tweet = new Tweet();
        when(tweetRepository.findById(anyLong())).thenReturn(Optional.of(tweet));
        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet);
        when(modelMapper.map(any(Tweet.class), eq(TweetDto.class))).thenReturn(new TweetDto());
        TweetDto result = tweetService.updateTweet(updateTweetDto);
        assertNotNull(result);
        assertEquals("Updated content", tweet.getContent());
        verify(tweetRepository, times(1)).findById(updateTweetDto.getTweetId());
        verify(tweetRepository, times(1)).save(tweet);
    }

    @Test
    void testDeleteTweet() {
        Long tweetId = 1L;
        doNothing().when(tweetRepository).deleteById(anyLong());
        Boolean result = tweetService.deleteTweet(tweetId);
        assertTrue(result);
        verify(tweetRepository, times(1)).deleteById(tweetId);
    }

    @Test
    void testGetUserTimeLine() {
        Long userId = 1L;
        List<Tweet> tweetList = Arrays.asList(new Tweet());
        when(tweetRepository.findByUserId(anyLong())).thenReturn(tweetList);
        when(modelMapper.map(any(Tweet.class), eq(TweetResponseDto.class))).thenReturn(new TweetResponseDto());
        List<TweetResponseDto> result = tweetService.getUserTimeLine(userId);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tweetRepository, times(1)).findByUserId(userId);
    }
}

