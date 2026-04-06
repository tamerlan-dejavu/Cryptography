package Cryptography.Lab4;

import java.util.Scanner;

public class PartOne {

    static char[] shift(char[] text, int k) {
        k = k % 26;
        if (k < 0) k = k + 26;
        char[] result = new char[text.length];
        
        for (int i = 0; i < text.length; i++) {
            char c = text[i];
            if (c == ' ') {
                result[i] = c;
            } else if (c >= 'a' && c <= 'z') {
                result[i] = (char)('a' + (c - 'a' + k) % 26);
            } else if (c >= 'A' && c <= 'Z') {
                result[i] = (char)('A' + (c - 'A' + k) % 26);
            }
        }
        return result;
    }

    static char[] shiftASCII(char[] text, int k) {
        char[] result = new char[text.length];
        for (int i = 0; i < text.length; i++) {
            result[i] = (char)(text[i] + k);
        }   
        return result;
    }

    // static char[] decrypt(char[] text, int k) {
    //     k = ((k % 26) + 26) % 26;
    //     char[] result = new char[text.length];
        
    //     for (int i = 0; i < text.length; i++) {
    //         char c = text[i];
    //         if (c == ' ') {
    //             result[i] = c;
    //         } else if (c >= 'a' && c <= 'z') {
    //             result[i] = (char)('a' + (c - 'a' - k) % 26);
    //         } else if (c >= 'A' && c <= 'Z') {
    //             result[i] = (char)('A' + (c - 'A' - k) % 26);
    //         }
    //     }
    //     return result;
    // }

    // static char[] decryptASCII(char[] text, int k) {
    //     char[] result = new char[text.length];
    //     for (int i = 0; i < text.length; i++) {
    //         result[i] = (char)(text[i] - k);
    //     }
    //     return result;
    // }

    static char[] encrypt(char[] text, int k) { return shift(text, k); }
    static char[] decrypt(char[] text, int k) { return shift(text, -k); }
    static char[] encryptASCII(char[] text, int k) { return shiftASCII(text, k); }
    static char[] decryptASCII(char[] text, int k) { return shiftASCII(text, -k); }

    static void printChars(char[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }

    static void bruteForce(char[] cipherText) {
        System.out.println("Key\tDecrypted Text");
        for (int k = 0; k < 26; k++) {
            System.out.print(k + "\t");
            printChars(decrypt(cipherText, k));
        }
    }

    static char[] readLine(Scanner scanner) {
        String line = scanner.nextLine();
        char[] result = new char[line.length()];
        for (int i = 0; i < line.length(); i++) {
            result[i] = line.charAt(i);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Text: ");
        char[] text = readLine(scanner);

        System.out.print("Key: ");
        int key = scanner.nextInt();

        char[] encrypted = encrypt(text, key);
        System.out.print("Encrypted: ");
        printChars(encrypted);

        System.out.print("Decrypted: ");
        printChars(decrypt(encrypted, key));

        char[] encryptedASCII = encryptASCII(text, key);
        System.out.print("ASCII Encrypted: ");
        printChars(encryptedASCII);

        System.out.print("ASCII Decrypted: ");
        printChars(decryptASCII(encryptedASCII, key));

        System.out.println("Brute force:");
        bruteForce(encrypted);

        scanner.close();
    }
}