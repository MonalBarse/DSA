package com.monal.Strings;

import java.util.HashMap;

/*
The beauty of a string is the difference in frequencies between the most frequent and least frequent characters.
For example, the beauty of "abaacc" is 3 - 1 = 2.
Given a string s, return the sum of beauty of all of its substrings.

Example 1:
  Input: s = "aabcb"
  Output: 5
  Explanation: The substrings with non-zero beauty are ["aab","aabc","aabcb","abcb","bcb"], each with beauty equal to 1.

Example 2:
  Input: s = "aabcbaa"
  Output: 17
*/
public class P002 {

  public int beautySum(String s) {
    int sum = 0;
    HashMap<Character, Integer> map = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      map.clear();
      for (int j = i; j < s.length(); j++) {
        map.put(s.charAt(j), map.getOrDefault(s.charAt(j), 0) + 1);
        sum += beauty(map);
      }
    }
    return sum;
  }

  private int beauty(HashMap<Character, Integer> map) {
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    for (int value : map.values()) {
      max = Math.max(max, value);
      min = Math.min(min, value);
    }
    return max - min;
  }

  public static void main(String[] args) {
    P002 p002 = new P002();
    System.out.println(p002.beautySum("aabcb")); // 5
    System.out.println(p002.beautySum("aabcbaa")); // 17
  }
}
