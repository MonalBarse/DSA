package com.monal;

import java.io.*;
import java.util.*;

public class Solve {
  private final int MOD = (int) 1e9 + 7;

  public int solve(int[] arr) {
    int n = arr.length - 1; // arr is 1-indexed

    // find the lengths of each cycle
    boolean[] vis = new boolean[n + 1];
    List<Integer> cycleLengths = new ArrayList<>();

    for (int i = 1; i <= n; i++) {
      if (!vis[i]) {
        int len = 0;
        int curr = i;
        while (!vis[curr]) {
          vis[curr] = true;
          curr = arr[curr];
          len++;
        }
        if (len > 0) cycleLengths.add(len);
      }
    }
    if (cycleLengths.isEmpty()) return 1; // no cycles
    if (cycleLengths.size() == 1) return 0; // single cycle

    // our answer is lcm of all cycle lengths
  }

  // -------------------- xxxxx ------------------------ //
  public static void main(String[] args) throws Exception {
    FastScanner fs = new FastScanner(System.in);
    Integer nObj = fs.nextInt();
    if (nObj == null) return;
    int n = nObj;
    int[] p = new int[n + 1];
    for (int i = 1; i <= n; ++i) p[i] = fs.nextInt();

    Solution sol = new Solution();
    System.out.println(sol.solve(p));
  }

  // Simple fast scanner for competitive-style input
  static class FastScanner {
    private final InputStream in;
    private final byte[] buffer = new byte[1 << 16];
    private int ptr = 0, len = 0;

    FastScanner(InputStream is) {
      in = is;
    }

    private int read() throws IOException {
      if (ptr >= len) {
        len = in.read(buffer);
        ptr = 0;
        if (len <= 0) return -1;
      }
      return buffer[ptr++];
    }

    Integer nextInt() throws IOException {
      int c;
      do {
        c = read();
        if (c == -1) return null;
      } while (c <= ' ');
      int sign = 1;
      if (c == '-') {
        sign = -1;
        c = read();
      }
      int val = c - '0';
      while (true) {
        c = read();
        if (c < '0' || c > '9') break;
        val = val * 10 + (c - '0');
      }
      return val * sign;
    }
  }

  class Solution {
    public void nextPermutation(int[] nums) {
      int ind1 = -1;
      int ind2 = -1;
      // step 1 find breaking point
      for (int i = nums.length - 2; i >= 0; i--) {
        if (nums[i] < nums[i + 1]) {
          ind1 = i;
          break;
        }
      }
      // if there is no breaking  point
      if (ind1 == -1) {
        reverse(nums, 0);
      } else {
        // step 2 find next greater element and swap with ind2
        for (int i = nums.length - 1; i >= 0; i--) {
          if (nums[i] > nums[ind1]) {
            ind2 = i;
            break;
          }
        }

        swap(nums, ind1, ind2);
        // step 3 reverse the rest right half
        reverse(nums, ind1 + 1);
      }
    }

    void swap(int[] nums, int i, int j) {
      int temp = nums[i];
      nums[i] = nums[j];
      nums[j] = temp;
    }

    void reverse(int[] nums, int start) {
      int i = start;
      int j = nums.length - 1;
      while (i < j) {
        swap(nums, i, j);
        i++;
        j--;
      }
    }
  }
}
