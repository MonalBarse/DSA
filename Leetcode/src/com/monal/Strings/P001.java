package com.monal.Strings;

/*

Given a string s, return the longest palindromic substring in s.
Example 1:
  Input: s = "babad"
  Output: "bab"
  Explanation: "aba" is also a valid answer.
Example 2:
  Input: s = "cbbd"
  Output: "bb"
Constraints:
  1 <= s.length <= 1000
  s consist of only digits and English letters.

*/
public class P001 {

  public String longestPalindrome(String s) {
    if (s == null || s.length() < 1)
      return "";

    // My Approach
    // check every letter as center and expant to left and right starting from
    // center and check if it is palindrome if yes then check if it is greater than
    // previous palindrome if yes then update the palindrome
    // if no then continue to next center

    int start = 0;
    int maxLength = 1;

    for (int i = 0; i < s.length(); i++) {
      int len1 = expantAroundCenter(s, i, i);
      int len2 = expantAroundCenter(s, i, i + 1);
      int len = Math.max(len1, len2);

      if (len > maxLength) {
        maxLength = len;
        start = i - (len - 1) / 2;
      }
    }

    return s.substring(start, start + maxLength);

  }

  private int expantAroundCenter(String s, int left, int right) {
    while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
      left--;
      right++;
    }

    return right - left - 1;
  }

  public static void main(String[] args) {
    P001 p001 = new P001();
    System.out.println(p001.longestPalindrome("babad"));
    System.out.println(p001.longestPalindrome("cbbd"));
  }

}
