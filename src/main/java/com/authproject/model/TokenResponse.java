package com.authproject.model;

// model/TokenResponse.java
public class TokenResponse {
    public String access_token;
    public String token_type = "Bearer";

    public TokenResponse(String token) {
        this.access_token = token;
    }

    // A simple method to turn this into JSON without a library
    public String toJson() {
        return "{\"access_token\":\"" + access_token + "\", \"token_type\":\"" + token_type + "\"}";
    }
}
