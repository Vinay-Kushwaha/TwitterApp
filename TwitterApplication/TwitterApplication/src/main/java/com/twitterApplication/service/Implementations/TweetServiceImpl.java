package com.twitterApplication.service.Implementations;

import com.twitterApplication.dto.request.TweetDto;
import com.twitterApplication.dto.request.UpdateTweetDto;
import com.twitterApplication.dto.response.TweetResponseDto;
import com.twitterApplication.model.Tweet;
import com.twitterApplication.model.User;
import com.twitterApplication.repositories.TweetRepository;
import com.twitterApplication.repositories.UserRepository;
import com.twitterApplication.service.TweetService;
import com.twitterApplication.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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
    public TweetDto createTweet(TweetDto tweetDto) {
        Tweet tweet = tweetRepository.save(modelMapper.map(tweetDto, Tweet.class));
        return modelMapper.map(tweet, TweetDto.class);
    }

    @Override
    public TweetDto updateTweet(UpdateTweetDto tweetDto) {
        Optional<Tweet> optionalTweet = tweetRepository.findById(tweetDto.getTweetId());
        Tweet tweet = optionalTweet.get();
        tweet.setContent(tweetDto.getContent());
        tweetRepository.save(tweet);
        return modelMapper.map(tweet, TweetDto.class);
    }

    @Override
    public Boolean deleteTweet(Long id) {
        tweetRepository.deleteById(id);
        return true;
    }

    @Override
    public List<TweetResponseDto> getUserTimeLine(Long id) {
        List<Tweet> tweetList = tweetRepository.findByUserId(id);
        return tweetList.stream()
                .map(tweet -> modelMapper.map(tweet, TweetResponseDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public List<TweetDto> getHomeTimeLine(String author) {
        User user = userService.findUserByName(author);
        Set<User> following = user.getFollowing();
        List<Long> userIds = following.stream().map(User::getId).toList();
        List<Tweet> tweets = tweetRepository.findByUserIdIn(userIds);
        System.out.println(tweets);
        return tweets.stream()
                .map(tweet -> modelMapper.map(tweet, TweetDto.class))
                .collect(Collectors.toList());
    }

}
