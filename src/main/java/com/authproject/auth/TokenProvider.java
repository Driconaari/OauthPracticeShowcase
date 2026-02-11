package com.authproject.auth;

import com.authproject.model.User;
import com.authproject.util.JwtUtils;

// auth/TokenProvider.java
public class TokenProvider {

    public String generateTokenForUser(User user) throws Exception {
        return JwtUtils.createToken(user.getUsername());
    }

    public boolean validateToken(String token) throws Exception {
        return JwtUtils.verify(token);
    }
}
