package keyExchange;

import java.util.Random;

public class DiffieHelmanDemo {
    public static void testDiffieHelman() {
        System.out.println("Both parties agree on the public keys G and N");
        Random random = new Random();
        long G = random.nextLong();
        System.out.println("Public key G: " + G);
        long N = random.nextLong();
        System.out.println("Public key N: " + N);

        System.out.println("\nAlice generates a private key a and computes A = G^a mod N");
        long privateA = random.nextLong();
        System.out.println("Private key a: " + privateA);
        long publicA = keyExchange(G, N, privateA);
        System.out.println("Public key A: " + publicA);

        System.out.println("\nBob generates a private key b and computes B = G^b mod N");
        long privateB = random.nextLong();
        System.out.println("Private key b: " + privateB);
        long publicB = keyExchange(G, N, privateB);
        System.out.println("Public key B: " + publicB);

        System.out.println("\nAlice computes the shared secret s = B^a mod N");
        long s1 = keyExchange(publicB, N, privateA);
        System.out.println("Shared secret s: " + s1);

        System.out.println("\nBob computes the shared secret s = A^b mod N");
        long s2 = keyExchange(publicA, N, privateB);
        System.out.println("Shared secret s: " + s2);
    }

    private static long keyExchange(long G, long N, long a) {
        return (long) Math.pow(G, a) % N;
    }

    public static void main(String[] args) {
        testDiffieHelman();
    }
}
