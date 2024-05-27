package com.twitterApplication.service;

import com.twitterApplication.dto.TweetKafkaDTO;
import com.twitterApplication.dto.request.UpdateTweetDto;
import com.twitterApplication.model.Tweet;
import com.twitterApplication.model.User;
import com.twitterApplication.repositories.TweetRepository;
import com.twitterApplication.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TweetConsumerService {
    @Autowired
    TweetRepository tweetRepository;
    @Autowired
    UserRepository userRepository;

    @KafkaListener(topics = "create-post-topic", groupId = "group-1")
    public void consumeTweet(TweetKafkaDTO tweetDto) {
        log.info("Tweet Details received : {}",tweetDto);
    }
}
