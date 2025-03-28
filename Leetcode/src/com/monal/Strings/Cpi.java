package com.monal.Strings;

// /*
//  You are given a string num, representing a large integer. Return the largest-valued odd integer (as a string) that is a non-empty substring of num, or an empty string "" if no odd integer exists.
// A substring is a contiguous sequence of characters within a string.

// Example 1:

// Input: num = "52"
// Output: "5"
// Explanation: The only non-empty substrings are "5", "2", and "52". "5" is the only odd number.
// Example 2:

// Input: num = "4206"
// Output: ""
// Explanation: There are no odd numbers in "4206".
// Example 3:

// Input: num = "35427"
// Output: "35427"
// Explanation: "35427" is already an odd number.

//  */

// /*
// Write a function to find the longest common prefix string amongst an array of strings.
// If there is no common prefix, return an empty string "".

// Example 1:
// Input: strs = ["flower","flow","flight"]
// Output: "fl"

// Example 2:
// Input: strs = ["dog","racecar","car"]
// Output: ""
// Explanation: There is no common prefix among the input strings.
// */

// /*
// Given two strings s and t, determine if they are isomorphic.
// Two strings s and t are isomorphic if the characters in s can be replaced to get t.
// All occurrences of a character must be replaced with another character while preserving the order of characters. No two characters may map to the same character, but a character may map to itself.

// Example 1:
//     Input: s = "egg", t = "add"
//     Output: true
//     Explanation:
//     The strings s and t can be made identical by:
//     Mapping 'e' to 'a'.
//     Mapping 'g' to 'd'.
// Example 2:
//     Input: s = "foo", t = "bar"
//     Output: false
//     Explanation:
//     The strings s and t can not be made identical as 'o' needs to be mapped to both 'a' and 'r'.
// Example 3:
//     Input: s = "paper", t = "title"
//     Output: true

// Example 4:
//     Input: s = "banana", t = "papaya"
//     Output: true
//  */
// public class P001 {

//     public String largestOddNumber(String num) {
//         if (Integer.parseInt(num) % 2 != 0) {
//             return num;
//         }

//         for (int i = num.length() - 1; i >= 0; i--) {
//             if ((num.charAt(i) - '0') % 2 != 0) {
//                 return num.substring(0, i + 1);
//             }
//         }

//         return "";
//     }

//     public String longestCommonPrefix(String[] strs) {
//         if (strs.length == 0) {
//             return "";
//         }
//         String prefix = strs[0];

//         for (int i = 1; i < strs.length; i++) {
//             while (!strs[i].startsWith(prefix)) {
//                 prefix = prefix.substring(0, prefix.length() - 1);
//                 if (prefix.isEmpty()) {
//                     return "";
//                 }
//             }
//         }
//         return prefix;
//     }

//     public boolean isIsomorphic(String s, String t) {
//         int[] smap = new int[256];
//         int[] tmap = new int[256];

//         for (int i = 0; i < s.length(); i++) {
//             if (smap[s.charAt(i)] != tmap[t.charAt(i)]) {
//                 return false;
//             }

//             smap[s.charAt(i)] = i + 1;
//             tmap[t.charAt(i)] = i + 1;
//         }
//         return true;
//     }

//     public static void main(String[] args) {
//         P001 p001 = new P001();

//         System.out.println(p001.largestOddNumber("4206"));
//         System.out.println(p001.largestOddNumber("52"));
//         System.out.println(p001.largestOddNumber("35427"));
//     }
// }
import java.util.Scanner;

public class Cpi {
    private static final int[] SEMESTER_CREDITS = { 67, 63, 52, 58, 62, 58, 0, 0 };

    /**
     * Calculate required SPI for future semester to achieve target CPI
     *
     * @param currentSemester Current semester number (1-based index)
     * @param currentCPI      Current Cumulative Performance Index
     * @param targetCPI       Desired target CPI
     * @return Required SPI for the next semester
     */
    public static double calculateRequiredSPI(int currentSemester, double currentCPI, double targetCPI) {
        // Validate input
        if (currentSemester < 1 || currentSemester >= SEMESTER_CREDITS.length) {
            System.out.println("Invalid semester number.");
            return -1;
        }

        // Calculate total credits up to current semester
        int totalCreditsUpToCurrent = 0;
        for (int i = 0; i < currentSemester; i++) {
            totalCreditsUpToCurrent += SEMESTER_CREDITS[i];
        }

        // Calculate total weighted SPI up to current semester
        double totalWeightedSPIUpToCurrent = currentCPI * totalCreditsUpToCurrent;

        // Credits for the next semester
        int nextSemesterCredits = SEMESTER_CREDITS[currentSemester];

        // Calculate required SPI for next semester
        double requiredNextSemesterSPI = (targetCPI * (totalCreditsUpToCurrent + nextSemesterCredits)
                - totalWeightedSPIUpToCurrent)
                / nextSemesterCredits;

        return requiredNextSemesterSPI;
    }

    /**
     * Predict final CPI if a specific SPI is achieved in future semesters
     *
     * @param currentSemester   Current semester number (1-based index)
     * @param currentCPI        Current Cumulative Performance Index
     * @param futureConstantSPI Assumed constant SPI for remaining semesters
     * @return Predicted final CPI
     */
    public static double predictFinalCPI(int currentSemester, double currentCPI, double futureConstantSPI) {
        // Validate input
        if (currentSemester < 1 || currentSemester >= SEMESTER_CREDITS.length) {
            System.out.println("Invalid semester number.");
            return -1;
        }

        // Calculate total credits up to current semester
        int totalCreditsUpToCurrent = 0;
        for (int i = 0; i < currentSemester; i++) {
            totalCreditsUpToCurrent += SEMESTER_CREDITS[i];
        }

        // Calculate total weighted SPI up to current semester
        double totalWeightedSPIUpToCurrent = currentCPI * totalCreditsUpToCurrent;

        // Calculate remaining semesters and their credits
        int remainingSemesters = SEMESTER_CREDITS.length - currentSemester;
        int remainingCredits = 0;
        for (int i = currentSemester; i < SEMESTER_CREDITS.length; i++) {
            remainingCredits += SEMESTER_CREDITS[i];
        }

        // Calculate weighted SPI for remaining semesters
        double remainingWeightedSPI = remainingCredits * futureConstantSPI;

        // Calculate final predicted CPI
        int totalCredits = totalCreditsUpToCurrent + remainingCredits;
        double finalCPI = (totalWeightedSPIUpToCurrent + remainingWeightedSPI) / totalCredits;

        return finalCPI;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Advanced CPI Predictor ---");
            System.out.println("1. Calculate Required SPI for Target CPI");
            System.out.println("2. Predict Final CPI");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Calculate Required SPI
                    System.out.print("Enter completed semester number: ");
                    int currentSemester = scanner.nextInt();

                    System.out.print("Enter exact current CPI: ");
                    double currentCPI = scanner.nextDouble();

                    System.out.print("Enter target final CPI: ");
                    double targetCPI = scanner.nextDouble();

                    double requiredSPI = calculateRequiredSPI(currentSemester, currentCPI, targetCPI);
                    if (requiredSPI != -1) {
                        System.out.printf("To achieve a target CPI of %.2f, you need an SPI of %.2f " +
                                "in Semester %d with %d credits.\n",
                                targetCPI, requiredSPI, currentSemester + 1,
                                SEMESTER_CREDITS[currentSemester]);
                    }
                    break;

                case 2:
                    // Predict Final CPI
                    System.out.print("Enter completed semester number: ");
                    int currentSemesterForPrediction = scanner.nextInt();

                    System.out.print("Enter current CPI: ");
                    double currentCPIForPrediction = scanner.nextDouble();

                    System.out.print("Enter assumed constant SPI for future semesters: ");
                    double futureConstantSPI = scanner.nextDouble();

                    double predictedFinalCPI = predictFinalCPI(
                            currentSemesterForPrediction,
                            currentCPIForPrediction,
                            futureConstantSPI);
                    if (predictedFinalCPI != -1) {
                        System.out.printf("Predicted Final CPI if you maintain an SPI of %.2f: %.2f\n",
                                futureConstantSPI, predictedFinalCPI);
                    }
                    break;

                case 3:
                    System.out.println("Thank you for using Advanced CPI Predictor!");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}