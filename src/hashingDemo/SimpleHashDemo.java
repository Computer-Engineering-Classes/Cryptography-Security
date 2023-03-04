package hashingDemo;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SimpleHashDemo {
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
