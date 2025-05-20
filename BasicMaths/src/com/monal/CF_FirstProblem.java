
package com.monal;

import java.util.*;

public class CF_FirstProblem {
  public static String findPalindrome(int k, String s) {
    int n = s.length();
    char[] title = s.toCharArray();
    boolean[] alreadyUsed = new boolean[k]; // to be used as a hashmap to store the used characters (default all false)
    // From the title, we can check which character is already used from the given k
    // available
    // characters
    // condition will be if charachter is not ? and should be less than k (characher
    // - 'a' => 0 to
    // k-1)
    // charachter - 'a' => true or false
    for (char ch : title) {
      if (ch != '?' && ch - 'a' < k) {
        alreadyUsed[ch - 'a'] = true;
      }
    }

    // ENSUREd that the lexiographacally heighest element should be at hte start
    // SO we will add to available array from the last element that is 'false'
    List<Character> available = new ArrayList<>();
    for (int i = k - 1; i >= 0; i--) {
      if (!alreadyUsed[i]) {
        // convert the int to char
        available.add((char) ('a' + i));
      }
    }

    // filling from the middle
    int left = (n - 1) / 2;
    int right = n / 2;

    int idx = 0;

    while (left >= 0) {
      // if both ? then fill it with first element of available[]
      // EDGE CASE: if available is empty then fill with 'a'
      if (title[left] == '?' && title[right] == '?') {
        if (!available.isEmpty()) {
          char fill = available.remove(0);
          title[left] = title[right] = fill;
        } else {
          title[left] = title[right] = 'a';
        }
        // If the left side is ? and right side is not then fill left same sa right
      } else if (title[left] == '?') {
        title[left] = title[right];
        // fill right same as left
      } else if (title[right] == '?') {
        title[right] = title[left];
        // BASE CASE : if both are not ? then check if they are equal or not
      } else if (title[left] != title[right]) {
        return "IMPOSSIBLE";
      }
      left--;
      right++;
    }

    // CHECK : IF all the k letters are used or not, if not then return "IMPOSSIBLE"
    /*
     * int usedCount = 0;
     * for (int i = 0; i < k; i++) {
     * if (alreadyUsed[i]) {
     * usedCount++;
     * }
     * }
     * if (usedCount < k) return "IMPOSSIBLE";
     */

    Set<Character> usedSet = new HashSet<>();
    for (char c : title) {
      if (c - 'a' < k) {
        usedSet.add(c);
      }
    }
    if (usedSet.size() < k)
      return "IMPOSSIBLE";

    return new String(title);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int k = scanner.nextInt();
    String s = scanner.next();
    scanner.close();

    System.out.println(findPalindrome(k, s));
  }
}
