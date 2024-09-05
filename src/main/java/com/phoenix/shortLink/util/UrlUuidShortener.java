package com.phoenix.shortLink.util;

import com.phoenix.shortLink.impl.GetAllLinksBean;
import com.phoenix.shortLink.model.ShortUrl;
import jakarta.ejb.EJB;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class UrlUuidShortener {

    private static final String BASE62 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ID_LENGTH = 10;

    public static String generateUniqueShortID(String originalUrl) throws NoSuchAlgorithmException {


        String shortUrl;

            String urlHash = hashUrl(originalUrl);
            UUID uuid = UUID.randomUUID();
            String combinedString = urlHash + uuid.toString();
            shortUrl = base62Encode(combinedString.getBytes(StandardCharsets.UTF_8)).substring(0, ID_LENGTH);

        return shortUrl;
    }

    private static String hashUrl(String url) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(url.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(Integer.toHexString(0xff & b));
        }
        return hexString.toString();
    }

    private static String base62Encode(byte[] bytes) {
        StringBuilder encoded = new StringBuilder();
        for (byte b : bytes) {
            encoded.append(BASE62.charAt(b % 62));
        }
        return encoded.toString();
    }


}

