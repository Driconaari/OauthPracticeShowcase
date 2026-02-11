package com.authproject.util;

// util/JSONParser.java
public class JSONParser {
    public static String createTokenResponse(String token) {
        return "{\"access_token\":\"" + token + "\", \"token_type\":\"Bearer\"}";
    }
}
