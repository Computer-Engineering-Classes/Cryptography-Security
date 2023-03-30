package encryption;

import hashing.HashUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class AsymmetricEncryptionDemo {
    public static void encryptText(int keysize, String plainText) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(keysize);
        KeyPair keyPair = keyGen.generateKeyPair();

        // Encrypt the message
        Cipher cipher;
        cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        HashUtils.printBytes("CipherText", cipherText);

        // Decrypt the message
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] decryptedText = cipher.doFinal(cipherText);
        System.out.println("Decrypted Text: " + new String(decryptedText));
    }

    public static void main(String[] args) {
        try {
            System.out.println("RSA 4096 Encrypt");
            encryptText(4096, "Hello World!");
            System.out.println("\nRSA 4096 Sign");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
