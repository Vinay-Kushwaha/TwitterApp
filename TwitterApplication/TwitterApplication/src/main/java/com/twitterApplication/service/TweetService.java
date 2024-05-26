package com.twitterApplication.service;

import com.twitterApplication.dto.request.TweetDto;
import com.twitterApplication.dto.request.UpdateTweetDto;
import com.twitterApplication.dto.response.TweetResponseDto;

import java.util.List;

public interface TweetService {
    TweetDto createTweet(TweetDto tweetDto);

    TweetDto updateTweet(UpdateTweetDto tweetDto);

    Boolean deleteTweet(Long id);

    List<TweetResponseDto> getUserTimeLine(Long id);

    List<TweetDto> getHomeTimeLine(String author);
}
