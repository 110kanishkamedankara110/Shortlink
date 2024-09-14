package com.phoenix.shortLink.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

public class UrlUuidShortener {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = BASE62.length();
    public static String generateUniqueShortID(Long id) {
        return encodeBase62(id);
    }

    private static String encodeBase62(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int remainder = (int) (num % BASE);
            sb.append(BASE62.charAt(remainder));
            num /= BASE;
        }
        return sb.reverse().toString();
    }
}
