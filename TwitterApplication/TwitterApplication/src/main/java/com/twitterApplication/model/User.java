package com.twitterApplication.model;

import jakarta.persistence.*;

@Entity
@Table(name="user_table")
public class User {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User( String name, String email) {
//        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", insertable = false, updatable = false, unique = true)
    private Long id;
    @Column(name = "user_name", columnDefinition = "varchar(255)")
    private String name;
    @Column(name = "email", columnDefinition = "varchar(255)")
    private String email;

}
