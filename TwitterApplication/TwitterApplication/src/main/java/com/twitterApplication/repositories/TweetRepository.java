package com.twitterApplication.repositories;

import com.twitterApplication.model.Tweet;
import com.twitterApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByContentContainingOrderByCreatedAtDesc(String keyword);

    List<Tweet> findByUserId(Long userId);
//    User findByUserId(Long userId);

    List<Tweet> findByUserIdIn(List userIds);

}
