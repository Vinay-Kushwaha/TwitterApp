package com.twitterApplication.exceptions;

import lombok.Getter;

@Getter
public class TweetNotFoundException extends RuntimeException{
    public TweetNotFoundException(String message) {
        super(message);
    }

    public TweetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
