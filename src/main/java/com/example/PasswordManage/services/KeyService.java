package com.example.PasswordManage.services;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;

@Service
public class KeyService {
    private static final String alpha = "abcdefghijklmnopqrstuvwxyz"; // a-z
    private static final String alphaUpperCase = alpha.toUpperCase(); // A-Z
    private static final String digits = "0123456789"; // 0-9
    private static final String ALPHA_NUMERIC = alpha + alphaUpperCase + digits;

    private static SecureRandom generator = new SecureRandom();

    /**
     * Random string with a-zA-Z0-9, not included special characters
     */
    public static int randomNumber(int min, int max) {
        return generator.nextInt((max - min) + 1) + min;
    }
    public static String randomAlphaNumeric(int numberOfCharactor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfCharactor; i++) {
            int number = randomNumber(0, ALPHA_NUMERIC.length() - 1);
            char ch = ALPHA_NUMERIC.charAt(number);
            sb.append(ch);
        }
        return sb.toString();
    }

    public static SecretKey createSeed() {
        String randStr = randomAlphaNumeric(16);
        SecretKey seed = new SecretKeySpec(randStr.getBytes(), "AES");
        return seed;
    }

    public static void storeSeedToKeyStore(SecretKey keyToStore, String password, String filepath) throws Exception {
        File file = new File(filepath);
        java.security.KeyStore javaKeyStore = java.security.KeyStore.getInstance("JCEKS");
        if(!file.exists()) {
            javaKeyStore.load(null, null);
        }

        javaKeyStore.setKeyEntry("keyAlias", keyToStore, password.toCharArray(), null);
        OutputStream writeStream = new FileOutputStream(filepath);
        javaKeyStore.store(writeStream, password.toCharArray());
    }

    public static SecretKey loadSeedFromKeyStore(String filepath, String password) {
        try {
            java.security.KeyStore keyStore = java.security.KeyStore.getInstance("JCEKS");
            InputStream readStream = new FileInputStream(filepath);
            keyStore.load(readStream, password.toCharArray());
            SecretKey key = (SecretKey) keyStore.getKey("keyAlias", password.toCharArray());
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
