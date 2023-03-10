package hashing;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashDemo {
    static void hashingTextDemo() throws NoSuchAlgorithmException, IOException {
        System.out.println("Directional:");
        hashText("The quick brown fox jumps over the lazy dog.");

        System.out.println("Deterministic:");
        hashText("The quick brown fox jumps over the lazy dog.");

        System.out.println("Pseudorandom:");
        hashText("The quick brown fox jumps ower the lazy dog.");

        System.out.println("Fixed length:");
        hashText("The quick brown fox jumps over the lazy dog and the lazy cat.");

        String file = "C:\\Users\\diogo\\Desktop\\Cryptography-Security\\README.md";
        String text = HashingUtils.readFile(file);
        System.out.println("File: " + file);
        System.out.println("Content: " + text);
        hashText(text);
    }

    private static String hashFile(String fileName, String algorithm) throws NoSuchAlgorithmException, IOException {
        MessageDigest digester = MessageDigest.getInstance(algorithm);
        FileInputStream fis = new FileInputStream(fileName);
        byte[] byteArray = new byte[512];
        int bytesCount;
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digester.update(byteArray, 0, bytesCount);
        }
        fis.close();
        byte[] digest = digester.digest();
        return HashingUtils.ByteArrayToString(digest);
    }

    private static void hashText(String s) throws NoSuchAlgorithmException {
        MessageDigest digester = MessageDigest.getInstance("SHA-256");
        byte[] input = s.getBytes();
        byte[] digest = digester.digest(input);
        HashingUtils.printByteArray("Digest", digest);
    }

    public static void main(String[] args) {
        try {
            hashingTextDemo();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }
}
