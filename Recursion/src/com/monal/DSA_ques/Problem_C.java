package com.monal.DSA_ques;

import java.util.*;

public class Problem_C {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int input = sc.nextInt();
    while (input-- > 0) {
      int n = sc.nextInt();
      int[] arr = new int[n];
      for (int i = 0; i < n; i++) {
        arr[i] = sc.nextInt();
      }

      System.out.println(solve(arr));
    }

    sc.close();
  }

  static int solve(int[] a) {
    int n = a.length;
    if (n == 0)
      return 0;

    // We want to find the maximum number of elements we can select
    // such that consecutive selected elements have gap >= 2

    int count = 1; // since always take first element
    int prev = a[0]; // last selected element

    for (int i = 1; i < n; i++) {
      if (a[i] >= prev + 2) {
        // This element creates a gap >= 2, so it starts a new array
        count++;
        prev = a[i];
      }
      // Skip elements that would extend current array (gap < 2)
    }

    return count;
  }
}