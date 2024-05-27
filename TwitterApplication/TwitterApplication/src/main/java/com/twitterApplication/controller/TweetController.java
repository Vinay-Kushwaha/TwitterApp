package com.twitterApplication.controller;

import com.twitterApplication.dto.TweetKafkaDTO;
import com.twitterApplication.dto.request.TweetDto;
import com.twitterApplication.dto.request.UpdateTweetDto;
import com.twitterApplication.dto.response.TweetResponseDto;
import com.twitterApplication.service.TweetProducerService;
import com.twitterApplication.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {
    @Autowired
    TweetService tweetService;
    @Autowired
    TweetProducerService tweetProducerService;

    @PostMapping("/create")
    public ResponseEntity<TweetResponseDto> createPost(@RequestBody TweetDto tweetDto) {
        return new ResponseEntity<>(tweetService.createTweet(tweetDto), HttpStatus.CREATED);
    }

    @PostMapping("/create/V1")
    public ResponseEntity<TweetResponseDto> createPostUSingKafka(@RequestBody TweetKafkaDTO tweetDto) {
        return new ResponseEntity<>(tweetProducerService.sendTweet(tweetDto), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<TweetDto> updatePost(@RequestBody UpdateTweetDto tweetDto) {
        return new ResponseEntity<>(tweetService.updateTweet(tweetDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Long id) {
        return new ResponseEntity<>(tweetService.deleteTweet(id), HttpStatus.OK);
    }

    @GetMapping("/userTimeLine/{userId}")
    public ResponseEntity<List<TweetResponseDto>> getUserTimeLine(@PathVariable Long userId) {
        return new ResponseEntity<>(tweetService.getUserTimeLine(userId), HttpStatus.OK);
    }

}
