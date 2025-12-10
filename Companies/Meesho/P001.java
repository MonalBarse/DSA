package Comapanies.Meesho;

import java.io.*;
import java.util.*;

/**
 * In the futuristic city of Nexus Prime, a tech organization stores large amounts of encrypted data
 * files. Each file is a string S, scrambled using different arrangements of letters, creating
 * unique encryption patterns. Dr. Axiom, the lead researcher, wants to figure out how many
 * different ways a file can be scrambled (encrypted). As the size of the files grows, Dr. Axiom
 * must find a fast way to count all possible unique arrangements of characters in the file.
 *
 * <p>Your task is to help Dr. Axiom by writing a program that, given a string S (representing the
 * encrypted file), calculates how many unique ways the letters in the string can be rearranged.
 * Return the answer modulo 109 + 7.
 *
 * <p>For example, if the string is "hidden code", there are many possible ways to rearrange the
 * letters, like "hidden code", "hiddn code", "hidden hode", "denh code", and more. Your goal is to
 * find out how many such unique arrangements exist.
 *
 * <p>Input Format The first and only line consists of a single string S which represents the
 * contents of the encrypted file. The string consists of lowercase English letters and spaces.
 * There is a single space between consecutive words.
 *
 * <p>Output Format Return an integer representing the number of unique encryption patterns of the
 * given string S. Since the answer may be very large, return it modulo 109 + 7.
 *
 * <p>Constraints 1 <= |S| <= 105, |S| represents the length of string. Examples Example 1: Input:
 * hidden code Output: 8640 Explanation: Some of the encryption patterns of the given string are
 * "hidden code", "hiddn code", "diden hode", "denh code", and "dhod clened", etc.
 *
 * <p>Example 2: Input: ab Output: 2 Explanation: There are two encryption patterns possible for the
 * given string: "ab" and "ba".
 */
public class P001 {

  public class Solution {

    private static final long MOD = 1000000007;

    public int solve(String S) {
      // Count frequency of each character (excluding spaces)
      Map<Character, Integer> freq = new HashMap<>();
      int totalLetters = 0;

      for (char c : S.toCharArray()) {
        if (c != ' ') {
          freq.put(c, freq.getOrDefault(c, 0) + 1);
          totalLetters++;
        }
      }

      // If no letters, return 1 (empty permutation)
      if (totalLetters == 0) {
        return 1;
      }

      // Precompute factorials up to totalLetters
      long[] factorial = new long[totalLetters + 1];
      factorial[0] = 1;
      for (int i = 1; i <= totalLetters; i++) {
        factorial[i] = (factorial[i - 1] * i) % MOD;
      }

      // Calculate numerator: totalLetters!
      long numerator = factorial[totalLetters];

      // Calculate denominator: product of all (frequency!)
      long denominator = 1;
      for (int count : freq.values()) {
        denominator = (denominator * factorial[count]) % MOD;
      }

      // Result = numerator / denominator (mod MOD)
      // Using modular multiplicative inverse: a/b = a * b^(-1) mod MOD
      long result = (numerator * modInverse(denominator, MOD)) % MOD;

      return (int) result;
    }

    // Calculate modular multiplicative inverse using Fermat's Little Theorem
    // For prime p: a^(-1) ≡ a^(p-2) (mod p)
    private long modInverse(long a, long mod) {
      return power(a, mod - 2, mod);
    }

    // Fast modular exponentiation: (base^exp) % mod
    private long power(long base, long exp, long mod) {
      long result = 1;
      base %= mod;

      while (exp > 0) {
        if (exp % 2 == 1) {
          result = (result * base) % mod;
        }
        base = (base * base) % mod;
        exp /= 2;
      }

      return result;
    }
  }

  public static void main(String[] args) {}
}
