// MEDIUM
package com.monal.DSA_ques;

/*
  Number of Substrings Containing All Three Characters
    Given a string s consisting only of characters a, b and c.
    Return the number of substrings containing at least one occurrence of all these characters a, b and c.
*/
public class P001 {
  public static void main(String[] args) {

    int result1 = numberOfSubstrings("abcabc");
    int result2 = numberOfSubstrings("aaacb");
    int result3 = numberOfSubstrings("abc");

    int expected1 = 10;
    int expected2 = 3;
    int expected3 = 1;

    System.out.println("Test case 1: " + ((result1 == expected1) ? "Passed" : "Failed ")
        + ((result1 != expected1) ? "Your result: " + result1 + " Expected: " + expected1 : ""));
    System.out.println("Test case 2: " + ((result2 == expected2) ? "Passed" : "Failed ")
        + ((result2 != expected2) ? "Your result: " + result2 + " Expected: " + expected2 : ""));
    System.out.println("Test case 3: " + ((result3 == expected3) ? "Passed" : "Failed ")
        + ((result3 != expected3) ? "Your result: " + result3 + " Expected: " + expected3 : ""));

  }

  // Brute Force
  public static int numberOfSubstringsBrute(String arr) {
    int count = 0;
    int n = arr.length();

    for (int leftptr = 0; leftptr < n; leftptr++) {
      for (int rightPtr = leftptr + 1; rightPtr <= n; rightPtr++) {
        String currentsubstring = arr.substring(leftptr, rightPtr);

        // Check if substring contains 'a', 'b', and 'c'
        if (currentsubstring.contains("a") && currentsubstring.contains("b") && currentsubstring.contains("c")) {
          count++;
        }
      }
    }

    return count;
  }

  public static int numberOfSubstrings(String s) {
    int count = 0;
    int left = 0;
    int[] freq = new int[3];

    for (int right = 0; right < s.length(); right++) {
      freq[s.charAt(right) - 'a']++;

      while (freq[0] > 0 && freq[1] > 0 && freq[2] > 0) {
        count += s.length() - right;
        freq[s.charAt(left) - 'a']--; // Moving forward with left will leave the first char
        left++;
      }
    }
    return count;
  }

  /*
   * private static int numberOfSubstrings(String s) {
   * int count = 0;
   * int left = 0;
   * Map<Character, Integer> freq = new HashMap<>();
   *
   * for (int right = 0; right < s.length(); right++) {
   * // Increase frequency count for current character
   * freq.put(s.charAt(right), freq.getOrDefault(s.charAt(right), 0) + 1);
   *
   * // If all 'a', 'b', 'c' exist in the window, shrink from the left
   * while (freq.size() == 3) {
   * count += s.length() - right; // All substrings from left to end are valid
   * // Reduce frequency of left character and move left pointer
   * char leftChar = s.charAt(left);
   * freq.put(leftChar, freq.get(leftChar) - 1);
   * if (freq.get(leftChar) == 0)
   * freq.remove(leftChar);
   * left++;
   * }
   * }
   * return count;
   * }
   */

  /*
   * // With Trick
   * public int numberOfSubstrings(String s) {
   * char[] ch = s.toCharArray();
   * int[] abc = {-1, -1, -1};
   * int count = 0, right = 0;
   *
   * while(right < ch.length){
   *
   * abc[ch[right] - 'a'] = right; // update latest index of curr char
   *
   * int min = Integer.MAX_VALUE;
   * for(int i=0; i<3; i++){
   * min = Math.min(min, abc[i]);
   * }
   * count = count + min + 1 ;
   * right++;
   * }
   * return count;
   * }
   *
   *
   */

}

// link-
// https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/description/?envType=daily-question&envId=2025-03-11
