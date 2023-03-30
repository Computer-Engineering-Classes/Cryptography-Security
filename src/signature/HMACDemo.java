package signature;

import hashing.HashUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class HMACDemo {
    public static void testSymmetricSignature(String message) throws NoSuchAlgorithmException, InvalidKeyException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256);
        Key key = keyGen.generateKey();
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] signature = mac.doFinal(message.getBytes());
        HashUtils.printBytes("Signature", signature);
    }

    // Similar to testSymmetricSignature() but with a password
    public static void testSymmetricSignatureWithPassword(String message, String password) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        // Derive the key from the password using PBKDF2
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 16 bytes = 128 bits
        random.nextBytes(salt);
        int iterations = 10000; // the number of iterations for PBKDF2
        int keyLength = 256; // the length of the derived key in bits
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "HmacSHA256");

        // Compute the HMAC signature for the message
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] signature = mac.doFinal(message.getBytes());
        HashUtils.printBytes("Signature", signature);
    }

    public static void main(String[] args) {
        try {
            testSymmetricSignature("The quick brown fox jumps over the lazy dog.");
            System.out.println();
            testSymmetricSignatureWithPassword("Hello World!", "password");
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
