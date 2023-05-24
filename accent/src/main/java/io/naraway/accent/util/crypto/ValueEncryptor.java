/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.crypto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@SuppressWarnings("java:S5542")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValueEncryptor {
    //
    public static final String SECRET_PROPERTY = "value-encryptor-secret";
    private static final String ALGORITHMS = "AES/ECB/PKCS5PADDING";

    private static SecretKeySpec setKey(final String myKey) {
        //
        MessageDigest sha = null;
        byte[] key = null;
        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String encrypt(String secret, byte[] input) {
        //
        try {
            SecretKeySpec secretKey = setKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHMS);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(input));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String encrypt(String secret, String input) {
        //
        return encrypt(secret, input.getBytes(StandardCharsets.UTF_8));
    }

    public static String encrypt(byte[] input) {
        //
        return encrypt(getSystemSecret(), input);
    }

    public static String encrypt(String input) {
        //
        return encrypt(getSystemSecret(), input);
    }

    public static String decrypt(String secret, byte[] input) {
        //
        try {
            SecretKeySpec secretKey = setKey(secret);
            Cipher cipher = Cipher.getInstance(ALGORITHMS);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(input));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String decrypt(String secret, String input) {
        //
        try {
            return decrypt(secret, Base64.getDecoder().decode(input));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String decrypt(byte[] input) {
        //
        return decrypt(getSystemSecret(), input);
    }

    public static String decrypt(String input) {
        //
        return decrypt(getSystemSecret(), input);
    }

    private static String getSystemSecret() {
        //
        String secret = System.getProperty(SECRET_PROPERTY);
        if (secret == null || secret.trim().length() == 0) {
            throw new IllegalArgumentException("No secret key in system props");
        }

        return secret;
    }
}