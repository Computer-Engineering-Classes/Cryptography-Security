package hashing;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashDemo {
    public static void hashingTextDemo(String algorithm) throws NoSuchAlgorithmException, IOException {
        System.out.println("Directional:");
        hashText("The quick brown fox jumps over the lazy dog.", algorithm);

        System.out.println("\nDeterministic:");
        hashText("The quick brown fox jumps over the lazy dog.", algorithm);

        System.out.println("\nPseudorandom:");
        hashText("The quick brown fox jumps ower the lazy dog.", algorithm);

        System.out.println("\nFixed length:");
        hashText("The quick brown fox jumps over the lazy dog and the lazy cat.", algorithm);
    }

    public static void hashingFileDemo(String algorithm) throws NoSuchAlgorithmException, IOException {
        String fileName = "src\\hashing\\HashDemo.java";
        System.out.println("File: " + fileName);
        System.out.println("Checksum: " + hashFile(fileName, algorithm));
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
        return HashUtils.bytesToString(digest);
    }

    private static void hashText(String s, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digester = MessageDigest.getInstance(algorithm);
        byte[] input = s.getBytes();
        byte[] digest = digester.digest(input);
        HashUtils.printBytes("Digest", digest);
    }

    public static void main(String[] args) {
        try {
            hashingTextDemo("SHA-256");
            System.out.println();
            hashingFileDemo("SHA-256");
        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
