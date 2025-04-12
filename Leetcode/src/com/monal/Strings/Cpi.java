package com.monal.Strings;

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
        @SuppressWarnings("unused")
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