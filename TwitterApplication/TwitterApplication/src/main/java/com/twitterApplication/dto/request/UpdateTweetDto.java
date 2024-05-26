package com.twitterApplication.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateTweetDto {
    private Long tweetId;
    private Long userId;
    private String content;
}
