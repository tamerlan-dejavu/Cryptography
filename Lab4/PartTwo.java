package Cryptography.Lab4;

import java.util.Scanner;

public class PartTwo {

    static boolean isValidKeyword(char[] key) {
        if (key.length == 0) return false;
        for (int i = 0; i < key.length; i++) {
            if (!((key[i] >= 'a' && key[i] <= 'z') || (key[i] >= 'A' && key[i] <= 'Z'))) {
                return false;
            }
        }
        return true;
    }

    static char[] expandKey(char[] text, char[] key) {
        char[] expanded = new char[text.length];
        int keyIndex = 0;
        for (int i = 0; i < text.length; i++) {
            if ((text[i] >= 'a' && text[i] <= 'z') || (text[i] >= 'A' && text[i] <= 'Z')) {
                expanded[i] = key[keyIndex % key.length];
                keyIndex++;
            } else {
                expanded[i] = text[i];
            }
        }
        return expanded;
    }

    static char[] encrypt(char[] text, char[] expandedKey) {
        char[] buffer = new char[text.length];
        for (int i = 0; i < buffer.length; i++) {
            if (text[i] >= 'a' && text[i] <= 'z') {
                int pi = text[i] - 'a';
                int ki = (expandedKey[i] >= 'a') ? expandedKey[i] - 'a' : expandedKey[i] - 'A';
                buffer[i] = (char)('a' + (pi + ki) % 26);
            } else if (text[i] >= 'A' && text[i] <= 'Z') {
                int pi = text[i] - 'A';
                int ki = (expandedKey[i] >= 'a') ? expandedKey[i] - 'a' : expandedKey[i] - 'A';
                buffer[i] = (char)('A' + (pi + ki) % 26);
            } else {
                buffer[i] = text[i];
            }
        }
        return buffer;
    }

    static char[] decrypt(char[] text, char[] expandedKey) {
        char[] buffer = new char[text.length];
        for (int i = 0; i < buffer.length; i++) {
            if (text[i] >= 'a' && text[i] <= 'z') {
                int ci = text[i] - 'a';
                int ki = (expandedKey[i] >= 'a') ? expandedKey[i] - 'a' : expandedKey[i] - 'A';
                buffer[i] = (char)('a' + (ci - ki + 26) % 26);
            } else if (text[i] >= 'A' && text[i] <= 'Z') {
                int ci = text[i] - 'A';
                int ki = (expandedKey[i] >= 'a') ? expandedKey[i] - 'a' : expandedKey[i] - 'A';
                buffer[i] = (char)('A' + (ci - ki + 26) % 26);
            } else {
                buffer[i] = text[i];
            }
        }
        return buffer;
    }

    static void printChars(char[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
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
        char[] key = readLine(scanner);

        if (!isValidKeyword(key)) {
            System.out.println("Error: keyword must contain only letters.");
            scanner.close();
            return;
        }

        char[] expandedKey = expandKey(text, key);
        char[] encrypted = encrypt(text, expandedKey);
        char[] decrypted = decrypt(encrypted, expandedKey);

        System.out.print("Original: "); printChars(text);
        System.out.print("Keyword: "); printChars(key);
        System.out.print("Expanded key:"); printChars(expandedKey);
        System.out.print("Encrypted: "); printChars(encrypted);
        System.out.print("Decrypted: "); printChars(decrypted);

        scanner.close();
    }
}