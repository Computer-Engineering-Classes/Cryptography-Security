package signature;

import hashing.HashUtils;

import java.security.*;

public class SigningDemo {
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
            signText(4096, "Hello World!");
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
    }
}
