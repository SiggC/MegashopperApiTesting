package dev.megashopper.common.utils;
import dev.megashopper.common.entities.Password;
import org.apache.tomcat.util.ExceptionUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
public class Generation {
    public static Password generatePassword(String password) {
        KeySpec keySpec;

        SecretKeyFactory factory;
        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[16];
        random.nextBytes(salt);
        keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(keySpec).getEncoded();
            return new Password(hash, salt);
        } catch (NoSuchAlgorithmException e) {
            // TO-DO: log this
            return null;
        } catch (InvalidKeySpecException e) {
            // TO-DO: log this
            return null;
        }
    }
    public static Password generatePassword(String password, byte[] salt) {
        SecretKeyFactory factory;
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(keySpec).getEncoded();
            return new Password(hash, salt);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            // TO-DO: log this
            return null;
        }
    }
    public static String generateResetToken() {
        char[] validChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        Random r = new SecureRandom();

        StringBuilder token = new StringBuilder();

        for (int i = 0; i < 64; i++) {
            token.append(validChars[r.nextInt(validChars.length)]);
        }
        return new String(Base64.getEncoder().encode(token.toString().getBytes()));
    }
}
