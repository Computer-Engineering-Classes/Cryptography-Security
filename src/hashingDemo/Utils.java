package hashingDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Utils {

    static public void printByteArray(String s, byte[] array) {

        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02x", b));
        }
        System.out.print("\n" + s + ": " + sb);
        System.out.println("\nSize: " + array.length * 8 + " bits");
    }

    static public String ByteArrayToString(byte[] array) {

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
            int r;
            while ((r = fis.read()) != -1) {
                sb.append((char) r);
            }
        }
        return sb.toString();
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}