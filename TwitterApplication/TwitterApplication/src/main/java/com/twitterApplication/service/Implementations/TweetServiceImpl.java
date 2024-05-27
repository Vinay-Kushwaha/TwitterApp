package com.twitterApplication.service.Implementations;

import com.twitterApplication.dto.request.TweetDto;
import com.twitterApplication.dto.request.UpdateTweetDto;
import com.twitterApplication.dto.response.TweetResponseDto;
import com.twitterApplication.exceptions.ResourceNotFoundException;
import com.twitterApplication.exceptions.TweetCreationException;
import com.twitterApplication.exceptions.TweetNotFoundException;
import com.twitterApplication.model.Tweet;
import com.twitterApplication.model.User;
import com.twitterApplication.repositories.TweetRepository;
import com.twitterApplication.repositories.UserRepository;
import com.twitterApplication.service.TweetService;
import com.twitterApplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TweetServiceImpl implements TweetService {
    @Autowired
    TweetRepository tweetRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public TweetResponseDto createTweet(TweetDto tweetDto) {
        try {
            Tweet tweet = tweetRepository.save(modelMapper.map(tweetDto, Tweet.class));
            return modelMapper.map(tweet, TweetResponseDto.class);
        } catch (Exception e) {
            log.error("Error creating tweet: {}", e.getMessage(), e);
            throw new TweetCreationException("Failed to create tweet", e);
        }
    }

    @Override
    public TweetDto updateTweet(UpdateTweetDto tweetDto) {
        try {
            Optional<Tweet> optionalTweet = tweetRepository.findById(tweetDto.getTweetId());
            if (optionalTweet.isEmpty()) {
                throw new TweetNotFoundException("Tweet not found with id: " + tweetDto.getTweetId());
            }

            Tweet tweet = optionalTweet.get();
            tweet.setContent(tweetDto.getContent());
            tweetRepository.save(tweet);
            return modelMapper.map(tweet, TweetDto.class);
        } catch (TweetNotFoundException e) {
            log.error("Tweet not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error updating tweet: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update tweet", e);
        }
    }

    @Override
    public Boolean deleteTweet(Long id) {
        try {
            if (!tweetRepository.existsById(id)) {
                throw new TweetNotFoundException("Tweet not found with id: " + id);
            }
            tweetRepository.deleteById(id);
            return true;
        } catch (TweetNotFoundException e) {
            log.error("Tweet not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error deleting tweet: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete tweet", e);
        }
    }

    @Override
    public List<TweetResponseDto> getUserTimeLine(Long id) {
        try {
            List<Tweet> tweetList = tweetRepository.findByUserId(id);
            if (tweetList.isEmpty()) {
                log.info("No tweets found for user id: {}", id);
                return List.of();
            }
            return tweetList.stream()
                    .map(tweet -> modelMapper.map(tweet, TweetResponseDto.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error retrieving user timeline: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve user timeline", e);
        }
    }

    @Override
    public List<TweetDto> getHomeTimeLine(String author) {
        try {
            User user = userService.findUserByName(author);
            if (user == null) {
                throw new ResourceNotFoundException("User", author);
            }
            Set<User> following = user.getFollowing();
            List<Long> userIds = following.stream().map(User::getId).collect(Collectors.toList());

            List<Tweet> tweets = tweetRepository.findByUserIdIn(userIds);
            if (tweets.isEmpty()) {
                log.info("No tweets found for user's home timeline: {}", author);
                return List.of();
            }
            return tweets.stream()
                    .map(tweet -> modelMapper.map(tweet, TweetDto.class))
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            log.error("User not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving home timeline: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve home timeline", e);
        }
    }
}
