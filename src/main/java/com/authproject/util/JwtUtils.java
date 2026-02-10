package com.authproject.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import javax.crypto.spec.SecretKeySpec;

public class JwtUtils {

    private static final String SECRET = "my-super-secret-key-that-is-at-least-32-characters-long";
    private static final String ALGORITHM = "HmacSHA256";

    public static String createToken(String username) throws Exception{
        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = "{\"sub\":\"" + username + "\",\"iat\":" + System.currentTimeMillis() / 1000 + "}";

        String encodedHeader = encode(header.getBytes());
        String encodedPayload = encode(payload.getBytes());

        String signature = sign (encodedHeader + "." + encodedPayload);
        return encodedHeader + "." + encodedPayload + "." + signature;
    }

    public static boolean verify(String token) throws Exception{
        String[] parts = token.split("\\.");
        if (parts.length != 3) return false;

        String reconstructedSignature = sign(parts[0] + "." + parts[1]);
        return reconstructedSignature.equals(parts[2]);
    }

    private static String sign (String data) throws Exception{
        Mac hmac = Mac.getInstance(ALGORITHM);
        SecretKeySpec secrectKey = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        hmac.init(secrectKey);
        return Base64.getEncoder().encodeToString(hmac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    private static String encode(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
    }
}
