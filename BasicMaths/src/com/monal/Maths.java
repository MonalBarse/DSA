package com.monal;

public class Maths {
  public static void main(String[] args) {
    factorOfNumber(40);
  }

  // -------------------------------------------------------------------- //

  public static int factorOfNumber(int number) {
    // Given a number find the factors of the number
    // We can solve this problem by iterating from 1 to the square root of the number
    // and checking if the number is divisible by the current number
    // If it is divisible then we can add the number and the number divided by the current number
    // to the list of factors
    // The time complexity of this algorithm is O(sqrt(n))

    for (int i = 1; i <= Math.sqrt(number); i++) {
      if (number % i == 0) {
        if (number / i == i) {
          System.out.print(i + " ");
        } else {
          System.out.print(i + " " + number / i + " ");
        }
      }
    }
    return 0;
  }

  // -------------------------------------------------------------------- //
  public class squareRootNR {

    // Method to find the square root using the Newton-Raphson method
    public static double sqrtNR(int number, int precision) {
      double x = number; // A guess
      double root;
      double threshold =
          Math.pow(10, -precision); // Precision threshold based on the desired precision

      while (true) {
        root = 0.5 * (x + (number / x)); // Raphson formula
        // Check if the difference between the new and old guess is within the precision threshold
        if (Math.abs(root - x) < threshold) {
          break; // If within the threshold, stop the iteration
        }
        x = root;
      }

      return root;
    }

    public static void main(String[] args) {
      int number = 40;
      int precision = 3;
      double sqrt = sqrtNR(number, precision);
      System.out.println(
          "Square root of " + number + " with precision " + precision + " is: " + sqrt);
      System.out.println("Square root of 55 with precision 2 is: " + sqrtNR(55, 2));
    }
  }

  // -------------------------------------------------------------------- //

  public static double sqrtBS(int number, int precision) {
    int s = 0;
    int e = number;
    double root = 0;

    // Binary search for the integer part
    while (s <= e) {
      int mid = s + (e - s) / 2;
      if (mid * mid == number) {
        return mid;
      } else if (mid * mid < number) {
        root = mid;
        s = mid + 1;
      } else {
        e = mid - 1;
      }
    }

    // Linear search for the fractional part
    double increment = 0.1;
    for (int i = 0; i < precision; i++) {
      while (root * root <= number) {
        root += increment;
      }

      // we also need to round off the number to the given precision
      // we can do this by multiplying the number by 10^precision, rounding it off and then dividing
      // it by 10^precision

      root = root - increment;
      increment = increment / 10;
    }
    root = Math.round(root * Math.pow(10, precision)) / Math.pow(10, precision);
    return root;
    /*
     * int number = 50;
     * int precision = 3;
     * double sqrt = sqrtBS(number, precision);
     * System.out.println("Square root of " + number + " with precision " + precision + " is: " + sqrt);
     */
  }

  // -------------------------------------------------------------------- //

  public static void seive(int number) {
    // Given a number find all the prime numbers less than or equal to it
    // We can solve this problem using Seive of Eratosthenes
    // The algorithm works by marking the multiples of each prime number starting from 2 as
    // composites
    // and then finding the next prime number and repeating the process
    // The time complexity of this algorithm is O(nlog(logn)) and space complexity is O(n)
    // The algorithm is efficient for finding all prime numbers up to 10 million and not efficient
    // for finding in a range.

    // We will mark the prime numbers as true and the non prime numbers as false
    boolean[] isPrime =
        new boolean[number + 1]; // Initialzing an empty array puts all the values to false
    for (int i = 2; i <= number; i++) {
      isPrime[i] = true;
    }
    // We are marking all the numbers as true as we are assuming all the numbers to be prime
    // and the prime numbers as true
    for (int i = 2; i * i <= number; i++) {
      if (isPrime[i]) {
        for (int j = i * 2; j <= number; j += i) {
          isPrime[j] = false;
        }
      }
    }
    for (int i = 2; i <= number; i++) {
      if (isPrime[i]) {
        System.out.print(i + " ");
      }
    }
    // Calculating the complexity of the algorithm
  }
}
