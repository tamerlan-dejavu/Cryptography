package Cryptography.Lab5;

import java.util.Scanner;

public class PartOne {

    static char toUpper(char c) {
        if (c >= 'a' && c <= 'z') return (char)(c - 32);
        return c;
    }

    static char[] toUpperArray(char[] text) {
        char[] result = new char[text.length];
        for (int i = 0; i < text.length; i++) {
            result[i] = toUpper(text[i]);
        }
        return result;
    }

    static boolean isValidKeyword(char[] key) {
        if (key.length == 0) return false;
        for (int i = 0; i < key.length; i++) {
            if (!((key[i] >= 'a' && key[i] <= 'z') || (key[i] >= 'A' && key[i] <= 'Z'))) {
                return false;
            }
        }
        return true;
    }

    static char[][] buildMatrix(char[] key) {
        boolean[] used = new boolean[26];
        char[] matrixFlat = new char[25];
        int pos = 0;

        for (int i = 0; i < key.length; i++) {
            char c = key[i];
            if (c == 'J') c = 'I';
            if (!used[c - 'A']) {
                used[c - 'A'] = true;
                matrixFlat[pos++] = c;
            }
        }

        for (int i = 0; i < 26; i++) {
            if (i == 'J' - 'A') continue;
            if (!used[i]) {
                matrixFlat[pos++] = (char)('A' + i);
            }
        }

        char[][] matrix = new char[5][5];
        for (int i = 0; i < 25; i++) {
            matrix[i / 5][i % 5] = matrixFlat[i];
        }
        return matrix;
    }

    static int[] findPosition(char[][] matrix, char c) {
        if (c == 'J') c = 'I';
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (matrix[row][col] == c) {
                    return new int[]{row, col};
                }
            }
        }
        return new int[]{-1, -1};
    }

    static char[] prepareText(char[] text) {
        int count = 0;
        for (int i = 0; i < text.length; i++) {
            if (text[i] >= 'A' && text[i] <= 'Z') count++;
        }

        char[] letters = new char[count];
        int j = 0;
        for (int i = 0; i < text.length; i++) {
            char c = text[i];
            if (c >= 'A' && c <= 'Z') {
                if (c == 'J') c = 'I';
                letters[j++] = c;
            }
        }

        char[] temp = new char[count * 2];
        int size = 0;
        int i = 0;
        while (i < letters.length) {
            temp[size++] = letters[i];
            if (i + 1 < letters.length) {
                if (letters[i] == letters[i + 1]) {
                    temp[size++] = 'X';
                    i++;
                } else {
                    temp[size++] = letters[i + 1];
                    i += 2;
                }
            } else {
                temp[size++] = 'X';
                i++;
            }
        }

        char[] result = new char[size];
        for (int k = 0; k < size; k++) {
            result[k] = temp[k];
        }
        return result;
    }

    static char[] encrypt(char[] text, char[][] matrix) {
        char[] prepared = prepareText(text);
        char[] buffer = new char[prepared.length];

        for (int i = 0; i < prepared.length; i += 2) {
            char a = prepared[i];
            char b = prepared[i + 1];

            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);

            int rowA = posA[0], colA = posA[1];
            int rowB = posB[0], colB = posB[1];

            if (rowA == rowB) {
                buffer[i]     = matrix[rowA][(colA + 1) % 5];
                buffer[i + 1] = matrix[rowB][(colB + 1) % 5];
            } else if (colA == colB) {
                buffer[i]     = matrix[(rowA + 1) % 5][colA];
                buffer[i + 1] = matrix[(rowB + 1) % 5][colB];
            } else {
                buffer[i]     = matrix[rowA][colB];
                buffer[i + 1] = matrix[rowB][colA];
            }
        }
        return buffer;
    }

    static char[] decrypt(char[] text, char[][] matrix) {
        char[] buffer = new char[text.length];

        for (int i = 0; i < text.length; i += 2) {
            char a = text[i];
            char b = text[i + 1];

            int[] posA = findPosition(matrix, a);
            int[] posB = findPosition(matrix, b);

            int rowA = posA[0], colA = posA[1];
            int rowB = posB[0], colB = posB[1];

            if (rowA == rowB) {
                buffer[i]     = matrix[rowA][(colA + 4) % 5];
                buffer[i + 1] = matrix[rowB][(colB + 4) % 5];
            } else if (colA == colB) {
                buffer[i]     = matrix[(rowA + 4) % 5][colA];
                buffer[i + 1] = matrix[(rowB + 4) % 5][colB];
            } else {
                buffer[i]     = matrix[rowA][colB];
                buffer[i + 1] = matrix[rowB][colA];
            }
        }
        return buffer;
    }

    static void printMatrix(char[][] matrix) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
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
        char[] text = toUpperArray(readLine(scanner));

        System.out.print("Key: ");
        char[] key = toUpperArray(readLine(scanner));

        if (!isValidKeyword(key)) {
            System.out.println("Error: keyword must contain only letters.");
            scanner.close();
            return;
        }

        char[][] matrix = buildMatrix(key);
        char[] prepared = prepareText(text);
        char[] encrypted = encrypt(text, matrix);
        char[] decrypted = decrypt(encrypted, matrix);

        System.out.println("\nMatrix:");
        printMatrix(matrix);

        System.out.print("\nOriginal:  "); printChars(text);
        System.out.print("Prepared:  "); printChars(prepared);
        System.out.print("Keyword:   "); printChars(key);
        System.out.print("Encrypted: "); printChars(encrypted);
        System.out.print("Decrypted: "); printChars(decrypted);

        scanner.close();
    }
}