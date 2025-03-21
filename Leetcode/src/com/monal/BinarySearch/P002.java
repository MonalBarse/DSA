package com.monal.BinarySearch;

/*
    Given 2 integers A and B and an array of integers C of size N.
    Element C[i] represents the length of ith board.
    You have to paint all N boards [C0, C1, C2, C3 … CN-1]. There are A painters available
    and each of them takes B units of time to paint 1 unit of the board.

    Calculate and return the minimum time required to paint all boards under
    the constraints that any painter will only paint contiguous sections of the board.

    NOTE:
        1. 2 painters cannot share a board to paint. That is to say, a board cannot be
            painted partially by one painter, and partially by another.
        2. A painter will only paint contiguous boards. This means a configuration where
            painter 1 paints boards 1 and 3 but not 2 is invalid.
*/

public class P002 {

    private static int minimum_time(int[] boards, int painters) {
        int start = 0, end = boards.length - 1;
        int res = -1;

        // Step 1 : Set the start and end values for binary search
        for (int board : boards) {
            // (lenght of board is time taken to paint)
            start = Math.max(start, board); // The minimum time is - the board with maximum length
            end += board; // The maximum time is - the sum of all boards
        }

        // Step 2 ; Search the space to find optimal time
        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (isPossible(boards, painters, mid)) {
                res = mid; // store potential time
                end = mid - 1; // continue searching left
            } else {
                start = mid + 1;
            }

        }

        return res;
    }

    private static boolean isPossible(int[] boards, int painters, int time) {
        // Determining if it's possible to paint boards with painters in given - `time`
        int paintersRequired = 1;
        int timeTaken = 0;

        for (int board : boards) {
            timeTaken += board;
            if (timeTaken > time) {
                paintersRequired++;
                timeTaken = board;
            }
            if (paintersRequired > painters) {
                return false;
            }
        }
        return true;

    }

    public static void main(String[] args) {

        int boards1[] = { 1, 4, 6, 11, 17, 15, 20 };
        int painters1 = 4;

        int boards2[] = { 1, 8, 11, 3, 5, 2, 4, 6, 7, 9, 10 };
        int painters2 = 3;

        int boards3[] = { 11, 52, 12, 76, 28, 48, 52, 81, 46, 81, 76, 49, 20, 20, 52, 6, 12, 6, 76, 81, 77 };
        int painters3 = 8;

        // Exptected output: 20
        System.out.println("Minimum time required to paint all boards: " + minimum_time(boards1, painters1));

        // Exptected output: 20
        System.out.println("Minimum time required to paint all boards: " + minimum_time(boards2, painters2));

        // Exptected output: 81
        System.out.println("Minimum time required to paint all boards: " + minimum_time(boards3, painters3));

    }
}
