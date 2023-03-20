package encryption;

import hashing.HashUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.*;

public class SymmetricEncryptionDemo {
    public static void testSymmetricEncryption(int keysize, String transformation) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize);
        Key key = keyGen.generateKey();
        HashUtils.printBytes("Key", key.getEncoded());

        byte[] plainText = "Hello World!".repeat(4).getBytes();
        System.out.println("Input: " + new String(plainText));

        Cipher cipher = Cipher.getInstance(transformation);
        byte[] cipherText;
        // CBC requires an initialization vector (IV)
        if (transformation.contains("CBC")) {
            // IV should be unpredictable, random and kept secret
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] iv = new byte[16];
            random.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            HashUtils.printBytes("IvSpec", ivSpec.getIV());
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            // Encrypt the message
            cipherText = cipher.doFinal(plainText);
            HashUtils.printBytes("CipherText", cipherText);
            // Decrypt the message, given derived key and initialization vector
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        } else {
            // ECB doesn't require an IV
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // Encrypt the message
            cipherText = cipher.doFinal(plainText);
            // Decrypt the message, given derived key
            HashUtils.printBytes("CipherText", cipherText);
            cipher.init(Cipher.DECRYPT_MODE, key);
        }

        byte[] decryptedText = cipher.doFinal(cipherText);
        HashUtils.printBytes("DecryptedText", decryptedText);
    }

    public static void main(String[] args) {
        try {
            System.out.println("AES 256 ECB");
            testSymmetricEncryption(256, "AES/ECB/PKCS5Padding");
            System.out.println("AES 192 CBC");
            testSymmetricEncryption(192, "AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }
}
