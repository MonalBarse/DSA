package com.monal.Stacks_Queues;

// https://leetcode.com/problems/find-the-celebrity/description/
/*
 * The Celebrity Problem
 * There is a party with n people (labeled from 0 to n-1). A party is
 * considered to be a celebrity if there is a person (let's call them the
 * celebrity) such that:
 * - The celebrity knows no one at the party.
 * - Everyone at the party knows the celebrity.
 *
 * You are given a n x n binary matrix, also the number of people is n.
 * The matrix is defined as follows:
 * - matrix[i][j] = 1 if person i knows person j, and
 * - matrix[i][j] = 0 if person i does not know person j.
 *
 * You need to find the celebrity at the party, if there is one.
 * If there is no celebrity, return -1.
 */
public class P017 {
  public int findCelebrity(int n, int[][] party) {
    int candidate = 0;

    // Step 1: Find the potential celebrity
    for (int i = 1; i < n; i++) {
      if(i == candidate) continue; // i and candidate are the same, skip
      if (party[candidate][i] == 1) {
        // candidate knows i → candidate can't be celebrity
        candidate = i;
      }
      // else: i can't be celebrity, keep candidate as is
    }

    // Step 2: Verify candidate
    for (int i = 0; i < n; i++) {
      if (i == candidate)
        continue;

      // Candidate shouldn't know anyone,
      // Everyone should know candidate
      if (party[candidate][i] == 1 || party[i][candidate] == 0) {
        return -1;
      }
    }

    return candidate;
  }

  public static void main(String[] args) {
    P017 solution = new P017();
    int[][] party1 = {
      {1, 1, 0},
      {0, 1, 0},
      {0, 1, 1}
    };
    System.out.println(solution.findCelebrity(3, party1)); // Output: 1

    int[][] party2 = {
      {1, 1},
      {1, 1}
    };
    System.out.println(solution.findCelebrity(2, party2)); // Output: -1
    int[][] party3 = {
      {1, 1, 0, 1, 1, 1, 0, 1},
      {0, 1, 0, 0, 0, 0, 0, 0},
      {0, 1, 1, 1, 1, 1, 0, 1},
      {0, 1, 0, 1, 1, 1, 0, 1},
      {0, 1, 0, 1, 1, 1, 0, 0},
      {0, 1, 0, 1, 1, 1, 0, 1},
      {0, 1, 0, 1, 1, 1, 1, 1},
      {0, 1, 0, 1, 1, 1, 0 ,1}
    };
    System.out.println(solution.findCelebrity(8, party3)); // Output: 1
  }
}
