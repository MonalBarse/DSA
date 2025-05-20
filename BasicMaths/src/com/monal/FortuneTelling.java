
package com.monal;

import java.util.*;

public class FortuneTelling {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    int[] petals = new int[n];

    int totalSum = 0;
    int minOdd = Integer.MAX_VALUE;
    boolean hasOdd = false;

    // Reading input and calculate total sum + find smallest odd petal count
    for (int i = 0; i < n; i++) {
      petals[i] = scanner.nextInt();
      totalSum += petals[i];

      if (petals[i] % 2 == 1) {
        hasOdd = true;
        minOdd = Math.min(minOdd, petals[i]);
      }
    }
    scanner.close();

    // If there is at least one odd number, remove the smallest odd number from the
    // sum.
    // If all petals are even, it's impossible to make the sum odd → print 0.
    // If total sum is odd, return it
    if (totalSum % 2 == 1) {
      System.out.println(totalSum);
    }
    // If total sum is even, remove the smallest odd (if exists)
    else if (hasOdd) {
      System.out.println(totalSum - minOdd);
    }
    // If no odd petals exist, we can't get an odd sum → output 0
    else {
      System.out.println(0);
    }
  }
}
