package system.flight.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Utils {


    public static String generateSalt() {

        final int SALT_LENGTH = 10;

        String saltChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder strSalt = new StringBuilder(SALT_LENGTH);

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < SALT_LENGTH; i++) {

            int randomIndex = random.nextInt(saltChars.length());

            strSalt.append(saltChars.charAt(randomIndex));

        }

        return strSalt.toString();
    }

    public static String generateHash(String inputString) {
        String strHash = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(inputString.getBytes());
            strHash = bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return strHash;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append("0");
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
