package system.flight.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Utils {

    // Generates a random salt using SecureRandom and Base64 encoding
    public static String generateSalt() {
        byte[] saltBytes = new byte[8]; // 8 bytes = 64 bits
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(saltBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(saltBytes); // URL-safe Base64
    }

    // Generates a SHA-256 hash of the input string + salt
    public static String generateHash(String inputString, String salt) {
        String combined = inputString + salt;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(combined.getBytes());
            return Base64.getEncoder().encodeToString(hashBytes); // Base64 encoded hash
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
