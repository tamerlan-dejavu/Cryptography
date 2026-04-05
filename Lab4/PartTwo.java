package Cryptography.Lab4;

import java.util.Scanner;

public class PartTwo {

    static String expandKey(String text, String key) {
        StringBuilder expanded = new StringBuilder();
        int j = 0;
        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                expanded.append(key.charAt(j % key.length()));
                j++;
            }
        }
        return expanded.toString();
    }

    static String encrypt(String text, String key) {
        text = text.toUpperCase();
        key = key.toUpperCase();
        String expandedKey = expandKey(text, key);
        StringBuilder result = new StringBuilder();
        int j = 0;
        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                result.append((char) ('A' + (c - 'A' + expandedKey.charAt(j++) - 'A') % 26));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    static String decrypt(String text, String key) {
        text = text.toUpperCase();
        key = key.toUpperCase();
        String expandedKey = expandKey(text, key);
        StringBuilder result = new StringBuilder();
        int j = 0;
        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                result.append((char) ('A' + ((c - 'A' - (expandedKey.charAt(j++) - 'A') + 26) % 26)));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Text: ");
        String text = scanner.nextLine();
        System.out.print("Key: ");
        String key = scanner.nextLine();

        String expandedKey = expandKey(text.toUpperCase(), key.toUpperCase());
        String encrypted = encrypt(text, key);
        String decrypted = decrypt(encrypted, key);

        System.out.println("Original:     " + text.toUpperCase());
        System.out.println("Keyword:      " + key.toUpperCase());
        System.out.println("Expanded key: " + expandedKey);
        System.out.println("Encrypted:    " + encrypted);
        System.out.println("Decrypted:    " + decrypted);

        scanner.close();
    }
}