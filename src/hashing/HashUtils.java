package hashing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HashUtils {

    static public void printBytes(String s, byte[] array) {
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02x", b));
        }
        System.out.printf("%s: %s\n", s, sb);
        System.out.printf("Size: %d bits\n", array.length * 8);
    }

    static public String bytesToString(byte[] array) {

        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    static public String readFile(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        File file = new File(fileName);
        try (FileInputStream fis = new FileInputStream(file)) {
            while (fis.available() > 0) {
                sb.append((char) fis.read());
            }
        }
        return sb.toString();
    }
}