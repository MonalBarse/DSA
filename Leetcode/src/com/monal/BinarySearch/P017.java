package com.monal.BinarySearch;

import java.util.*;

public class P017 {

  class Solution {
    private int[] prefix;
    private int total;
    private Random random;

    public Solution(int[] w) {
      prefix = new int[w.length];
      random = new Random();
      prefix[0] = w[0];
      for (int i = 1; i < w.length; i++) prefix[i] = prefix[i - 1] + w[i];

      total = prefix[prefix.length - 1];
    }

    public int pickIndex() {
      // pick a random number between 1 and total inclusive
      int r = random.nextInt(total) + 1;

      // binary search: first index with prefix >= r (lower bound)
      int start = 0, end = prefix.length - 1;
      while (start < end) {
        int mid = start + (end - start) / 2;

        if (prefix[mid] < r) start = mid + 1;
        else end = mid;
      }
      return start;
    }
  }

}
