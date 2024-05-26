package com.twitterApplication.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TweetResponseDto {
    private Long tweetId;
    private Long userId;
    private String content;
}
