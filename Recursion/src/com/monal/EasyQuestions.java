package com.monal;

public class EasyQuestions {
  public static void main(String[] args) {}

  // ----------------------------------------------------------------- //
  public static void printNumber(int n) {
    // Given a number n, print all numbers from 1 to n using recursion

    if (n == 0) {
      return;
    }
    printNumber(n - 1);
    System.out.print(n + " ");
  }

  // ----------------------------------------------------------------- //
  public static int factorial(int n) {
    // Given a number n, find the factorial of the number using recursion
    if (n == 0) {
      return 1;
    }

    return n * factorial(n - 1);
    // System.out.println(factorial(8));
  }

  // ----------------------------------------------------------------- //

  public static int sumOfDigits(int n) {
    // Given a number, find the sum of it's digits
    if (n == 0) {
      return 0;
    }

    return (n % 10) + sumOfDigits(n / 10);
  }

  // ----------------------------------------------------------------- //

  public static int productOfDigits(int n) {
    // GIven a number, find the product of it's digits
    if (n == 0) {
      return 1;
    }
    int num = n % 10;
    n = n / 10;
    return num * productOfDigits(n);
  }

  // ----------------------------------------------------------------- //
  public static int reverseNumber(int n) {
    // Handle negative numbers by reversing the positive part and then reapplying the negative sign
    if (n < 0) {
      return -reverseNumberHelper(-n, 0);
    }
    return reverseNumberHelper(n, 0);
  }

  private static int reverseNumberHelper(int n, int count) {
    if (n == 0) {
      return count;
    }
    int rem = n % 10;
    count = count * 10 + rem;
    return reverseNumberHelper(n / 10, count);
  }

  // ----------------------------------------------------------------- //
  // we tend to use helper functions in recursion to avoid passing extra parameters
  public static boolean isPalindrome(String word) {

    word =
        word.replaceAll("\\s+", "")
            .toLowerCase(); // Remove all white spaces and convert to lower case
    int helped = isPalindromeHelper(word, 0, word.length() - 1);
    boolean ans = (helped == 1) ? false : true;
    return ans;
  }

  public static int isPalindromeHelper(String word, int start, int end) {
    if (start >= end) {
      return 0;
    }
    if (word.charAt(start) != word.charAt(end)) {
      return 1;
    }
    return isPalindromeHelper(word, start + 1, end - 1);
  }

  /* // THis is not using recursion
  public static int isPalindromeHelper(String word) {
    int start = 0;
    int end = word.length() - 1;
    while (start <= end) {
      if (word.charAt(start) != word.charAt(end)) {
        return 1; // false
      }
      if (word.charAt(start) == word.charAt(end)) {
        start = start + 1;
        end = end - 1;
      }
    }
    return 0; // true
  } */

  // ----------------------------------------------------------------- //

  public static int numberOfOccurences(int num, int culprit) {
    // Given an interger and a culprit digit find the number of culprit digits
    if (num == 0) {
      return 0;
    }
    int rem = num % 10;
    int count = 0;

    if (rem == culprit) {
      count = 1;
    }

    return count + numberOfOccurences(num / 10, culprit);

    // System.out.println(numberOfOccurences(222210001, 2));
  }

  // ----------------------------------------------------------------- //
}
