package TestingAutomationTool.Utilities;

import java.util.Random;

public class RandomBitsGenerator {
    public static String generateHexInputs(int n) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 4; j++) { // 4 characters for 16 bits
                int randInt = rand.nextInt(16); // Generate a random integer between 0 and 15
                sb.append(Integer.toHexString(randInt).toUpperCase()); // Convert the integer to hexadecimal and append to StringBuilder
            }
            sb.append((i==n-1)?"":"\n"); // Add newline character after each hexadecimal input
        }
        return sb.toString();
    }

    // Generates numbers from 0 to 31 and extends them with zeros
    public static String generateUnsignedExtendedHexInputs(int n) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            // Generate a random number from 0 to 31
            int randInt = rand.nextInt(32); // 2^5 = 32

            // Extend to 16 bits with zero extension
            sb.append(String.format("%04X", randInt)); // Zero extend to 16 bits
            sb.append((i == n - 1)?"":"\n"); // Add newline character after each hexadecimal input
        }
        return sb.toString();
    }

    // Generates numbers from -16 to 15 and extends them with sign extension
    public static String generateSignedExtendedHexInputs(int n) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            // Generate a random number from -16 to 15
            int randInt = rand.nextInt(32) - 16; // -16 to 15

            // Extend to 16 bits with signed extension
            sb.append(String.format("%04X", (randInt & 0xFFFF))); // Sign extend to 16 bits
            sb.append("\n"); // Add newline character after each hexadecimal input
        }
        return sb.toString();
    }

    public static String generateRandomShiftAmount(int count) {
        StringBuilder result = new StringBuilder();

        // Generate the specified number of random numbers
        for (int i = 0; i < count; i++) {

            Random random = new Random();
            // Generate a random number from 0 to 15
            int randomNumber = random.nextInt(16); // 0 to 16 inclusive

            // Convert the random number to a 4-digit hexadecimal number with zero-padding
            String hexNumber = Integer.toHexString(randomNumber).toUpperCase(); // Convert to uppercase
            // Add zero-padding if necessary
            if (hexNumber.length() < 4) {
                hexNumber = "0".repeat(4 - hexNumber.length()) + hexNumber;
            }

            // Append the hexadecimal number to the result with a newline character
            result.append(hexNumber).append((i == count - 1)?"":"\n");
        }

        return result.toString();
    }

}
