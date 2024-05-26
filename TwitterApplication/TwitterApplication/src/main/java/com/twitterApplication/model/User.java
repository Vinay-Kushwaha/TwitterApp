package com.twitterApplication.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, updatable = false, unique = true)
    private Long id;
    @Column(name = "user_name", columnDefinition = "varchar(255)")
    private String userName;
    @Column(name = "email", columnDefinition = "varchar(255)")
    private String email;

    //    @OneToMany(cascade = CascadeType.ALL,mappedBy = "id")
////    @JoinColumn(name = "id", referencedColumnName = "id")
//    private Set<User> followers = new HashSet<>();
//
//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "id")
////    @JoinColumn(name = "id", referencedColumnName = "id")
//    private Set<User> following = new HashSet<>();
// Users this user is following
    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<User> following = new HashSet<>();

    // Users following this user
    @ManyToMany(mappedBy = "following")
    private Set<User> followers = new HashSet<>();
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tweet> tweets;

}
