package com.monal;

/* public class Basics01 {
  public static void main(String[] args) {
    int n = 10; // Example: Calculate Fibonacci sequence up to the 10th number
    for (int i = 0; i < n; i++) {
      System.out.print(fibonacci(i) + " ");
    }
  }

  // Recursive method to calculate Fibonacci number
  public static int fibonacci(int n) {
    if (n < 0) {
      return -1;
    }
    if (n <= 1) {
      return n;
    } else {
      return fibonacci(n - 1) + fibonacci(n - 2);
    }
  }
} */

// Output: 0 1 1 2 3 5 8 13 21 34

// THe above code repetats some of the calculations like for eg fibonacci(3)  the fibonachhi of 1
// and 2 is calculated twice
// To avoid this we can use memoization
// Memoization is an optimization technique used primarily to speed up computer programs by storing
// the results of expensive function calls and returning the cached result when the same inputs
// occur again.

public class Basics01 {
  public static void main(String[] args) {
    int n = 20; // Example: Calculate Fibonacci sequence up to the 10th number
    int[] memo = new int[n + 1];
    memo[0] = 0; // Base case for n = 0
    memo[1] = 1; // Base case for n = 1
    for (int i = 0; i < n; i++) {
      System.out.print(fibonacci(i, memo) + " ");
    }
  }

  // Recursive method to calculate Fibonacci number
  public static int fibonacci(int n, int[] memo) {

    int result = memo[n];
    if (result == 0) {
      // if we initilaize an array with 10 elements all the elements will be 0 by default

      if (n <= 1) {
        return n;
      } else {
        result = fibonacci(n - 1, memo) + fibonacci(n - 2, memo);
        memo[n] = result;
      }
    }
    return result;
  }
}

/* We first created a memo array (one element larger than the input n as we start from 0 and not 1) to store the results of the Fibonacci sequence.
 * We then modified the recursive method to check if the result for the current n has already been
 * calculated and stored in the memo array. If not, we calculate the Fibonacci number as usual
 * and store it in the memo array before returning the result.
 */

/*
We can calculate the formula for the nth fibonacci number using the formula - of recurrence relation of linear recurrence
[WE KNOW THAT THE LINEAR RECURRENCES LOOKS LIKE THIS f(x) = a1 f(x-1) + a2 f(x-2) + a3 f(x-3) + ... + ak f(x-k)]

   F(n) = F(n-1) + F(n-2)
   F(0) = 0 -- (i)
   F(1) = 1 -- (ii)
   f(n) = f(n-1) + f(n-2)
   put f(n) = a^n
   a^n = a^(n-1) + a^(n-2)
   a^2 = a + 1
   roots of the equation a^2 - a - 1 = 0 are a = (1 + sqrt(5))/2 and a = (1 - sqrt(5))/2
   f(n) = (1/sqrt(5)) * ((1 + sqrt(5))/2)^n - (1/sqrt(5)) * ((1 - sqrt(5))/2)^n

   we know f(n) = c1 * a1^n + c2 * a2^n
   now put (i) and (ii) in the above equation
   0 = c1 + c2
   1 = c1 * a1 + c2 * a2
   c1 = 1/sqrt(5) & c2 = -1/sqrt(5)
   f(n) = (1/sqrt(5)) * ((1 + sqrt(5))/2)^n - (1/sqrt(5)) * ((1 - sqrt(5))/2)^n
   so the nth fibonacci number can be calculated using the above formula
*/
