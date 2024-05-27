package com.twitterApplication.exceptions;

import lombok.Getter;

@Getter
public class TweetCreationException extends RuntimeException {
    public TweetCreationException(String message) {
        super(message);
    }

    public TweetCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
