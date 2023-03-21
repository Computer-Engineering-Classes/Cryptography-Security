package encryption;

import hashing.HashUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

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

    public static void signText(int keysize, String plainText) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(keysize);
        KeyPair keyPair = keyGen.generateKeyPair();

        // Sign the message
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(keyPair.getPrivate());
        signature.update(plainText.getBytes());
        byte[] signatureBytes = signature.sign();
        HashUtils.printBytes("Signature", signatureBytes);

        // Verify the message
        signature.initVerify(keyPair.getPublic());
        signature.update(plainText.getBytes());
        boolean verified = signature.verify(signatureBytes);
        System.out.println("Verified: " + verified);
    }

    public static void main(String[] args) {
        try {
            System.out.println("RSA 4096 Encrypt");
            encryptText(4096, "Hello World!");
            System.out.println("\nRSA 4096 Sign");
            signText(4096, "Hello World!");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | SignatureException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
