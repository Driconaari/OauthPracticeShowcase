package com.authproject.model;

// model/User.java

//in a real application, this would be an entity class with JPA annotations and stored in a database
//also recommend using lombok to reduce boilerplate code for getters/setters, but keeping it simple here for demonstration
public class User {
    private String username;
    private String password; // In a real application, this should be hashed and salted, I like to use bcrypt

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

}
