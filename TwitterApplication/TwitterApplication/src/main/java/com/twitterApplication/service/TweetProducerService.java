package com.twitterApplication.service;

import com.twitterApplication.dto.TweetKafkaDTO;
import com.twitterApplication.dto.request.UpdateTweetDto;
import com.twitterApplication.dto.response.TweetResponseDto;
import com.twitterApplication.model.Tweet;
import com.twitterApplication.model.User;
import com.twitterApplication.repositories.TweetRepository;
import com.twitterApplication.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TweetProducerService {
    @Autowired
    private KafkaTemplate<String, TweetKafkaDTO> kafkaTemplate;
    @Autowired
    TweetRepository tweetRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    @Value("${spring.kafka.template.default-topic}")
    private String tweetTopic;
    public TweetProducerService(KafkaTemplate<String, TweetKafkaDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public TweetResponseDto sendTweet(TweetKafkaDTO tweetDto) {
        Tweet tweet = new Tweet();
        User user = userRepository.findById(tweetDto.getUserId()).get();
        tweet.setUser(user);
        tweet.setContent(tweetDto.getContent().toString());
        tweetRepository.save(tweet);
        log.info("Send Tweet : Kafka Topic : {} : Tweet :{}",tweetTopic,tweet);
        kafkaTemplate.send(tweetTopic, tweetDto);
        return modelMapper.map(tweet,TweetResponseDto.class);
    }
}
