package CodeForces.src.com.contestOne;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class acd {
  class Solution {
    public int minJumps(int[] arr) {
      int n = arr.length;
      if (n == 1)
        return 0;

      int maxVal = 0;
      for (int num : arr)
        if (num > maxVal)
          maxVal = num;
      boolean[] isPrime = sieveOfE(maxVal);

      Map<Integer, List<Integer>> map = new HashMap<>(); // prime val -> indices
      for (int i = 0; i < n; i++)
        map.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);

      Queue<Integer> q = new ArrayDeque<>();
      boolean[] vis = new boolean[n];
      Set<Integer> usedPrimes = new HashSet<>();
      q.offer(0);
      vis[0] = true;
      int jumps = 0;

      while (!q.isEmpty()) {
        int size = q.size();
        for (int i = 0; i < size; i++) {
          int curr = q.poll();
          if (curr == n - 1)
            return jumps;

          // Adjacent moves
          if (curr + 1 < n && !vis[curr + 1]) {
            vis[curr + 1] = true;
            q.offer(curr + 1);
          }
          if (curr - 1 >= 0 && !vis[curr - 1]) {
            vis[curr - 1] = true;
            q.offer(curr - 1);
          }

          int val = arr[curr];
          if (val > 1 && isPrime[val] && !usedPrimes.contains(val)) {
            usedPrimes.add(val);
            // if we encounter a prime num -> can jump to all idx that is multiple of this
            // prime
            for (int multiple = val; multiple <= maxVal; multiple += val) {
              if (map.containsKey(multiple)) {
                for (int idx : map.get(multiple)) {
                  if (!vis[idx]) {
                    vis[idx] = true;
                    q.offer(idx);
                  }
                }
                map.remove(multiple); // remove to avoid reprocessing
              }
            }
          }
        }
        jumps++;
      }
      return -1;
    }

    private boolean[] sieveOfE(int max) {
      boolean[] isPrime = new boolean[max + 1];
      Arrays.fill(isPrime, true);
      if (max >= 0)
        isPrime[0] = false;
      if (max >= 1)
        isPrime[1] = false;
      for (int i = 2; i * i <= max; i++)
        if (isPrime[i])
          for (int j = i * i; j <= max; j += i)
            isPrime[j] = false;
      return isPrime;
    }
  }

  class Solution_numOfSubsequence {
    class Solution {
      public long numOfSubsequences(String s) {
        int n = s.length();

        int[] prefixL = new int[n + 1];
        int[] prefixLC = new int[n + 1];
        int[] suffixT = new int[n + 1];

        // Prefix L
        for (int i = 0; i < n; i++)
          prefixL[i + 1] = prefixL[i] + (s.charAt(i) == 'L' ? 1 : 0);

        // Prefix LC
        for (int i = 0; i < n; i++) {
          prefixLC[i + 1] = prefixLC[i];
          if (s.charAt(i) == 'C') {
            prefixLC[i + 1] += prefixL[i]; // Count all L before this C
          }
        }
        // Suffix T
        for (int i = n - 1; i >= 0; i--)
          suffixT[i] = suffixT[i + 1] + (s.charAt(i) == 'T' ? 1 : 0);

        // Base count: LCT already present
        long base = 0;
        for (int i = 0; i < n; i++)
          if (s.charAt(i) == 'T')
            base += prefixLC[i];

        long result = base;
        for (int i = 0; i <= n; i++) {
          // insert L, if curr idx -> C add all T after
          long gain = 0;
          for (int j = i; j < n; j++)
            if (s.charAt(j) == 'C')
              gain += suffixT[j + 1];
          result = Math.max(result, base + gain);

          // insert C, how many L before curr idx * how many T after
          gain = (long) prefixL[i] * suffixT[i];
          result = Math.max(result, base + gain);

          // insert T, how many LC before curr idx * how many L after
          gain = prefixLC[i];
          result = Math.max(result, base + gain);
        }

        return result;
      }
    }
  }
}
