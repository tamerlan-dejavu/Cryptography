package Cryptography.Lab4;

import java.util.Scanner;

public class PartOne {

    static String shift(String text, int k) {
        k = ((k % 26) + 26) % 26;
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (c == ' ') {
                result.append(c);
            } else if (c >= 'a' && c <= 'z') {
                result.append((char)('a' + (c - 'a' + k) % 26));
            } else if (c >= 'A' && c <= 'Z') {
                result.append((char)('A' + (c - 'A' + k) % 26));
            }
        }
        return result.toString();
    }

    static String shiftASCII(String text, int k) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray())
            result.append((char)(c + k));
        return result.toString();
    }

    static String encrypt(String text, int k) { return shift(text, k); }
    static String decrypt(String text, int k) { return shift(text, -k); }
    static String encryptASCII(String text, int k) { return shiftASCII(text, k); }
    static String decryptASCII(String text, int k) { return shiftASCII(text, -k); }

    static void bruteForce(String cipherText) {
        System.out.println("Key\tDecrypted Text");
        for (int k = 0; k < 26; k++)
            System.out.println(k + "\t" + decrypt(cipherText, k));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Text: ");
        String text = scanner.nextLine();
        System.out.print("Key: ");
        int key = scanner.nextInt();

        String encrypted = encrypt(text, key);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypt(encrypted, key));

        String encryptedASCII = encryptASCII(text, key);
        System.out.println("ASCII Encrypted: " + encryptedASCII);
        System.out.println("ASCII Decrypted: " + decryptASCII(encryptedASCII, key));

        System.out.println("Brute force:");
        bruteForce(encrypted);
        scanner.close();
    }
}