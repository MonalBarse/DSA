package com.monal.Recursion;

import java.util.*;

/*
17. Letter Combinations of a Phone Number
Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.
A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
{
  2: abc, 3: def, 4:ghi, 5:jkl, 6:mno, 7:pqrs, 8:tuv, 9:wxyz
}

Example 1:
  Input: digits = "23"
  Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
Example 2:
  Input: digits = ""
  Output: []
Example 3:
  Input: digits = "2"
  Output: ["a","b","c"]

 */
public class P004 {
  class Solution {
    private final Map<Character, String> phone = Map.of(
        '2', "abc",
        '3', "def",
        '4', "ghi",
        '5', "jkl",
        '6', "mno",
        '7', "pqrs",
        '8', "tuv",
        '9', "wxyz");

    public List<String> letterCombinations(String digits) {
      // given a map we detect waht digits are passed and then run a permutation
      // recursion function

      List<String> result = new ArrayList<>();
      if (digits.length() == 0) {
        return result;
      }
      helper_fn(digits, 0, new StringBuilder(), result);

      return result;
    }

    private void helper_fn(String d, int idx, StringBuilder curr, List<String> result) {

      if (idx == d.length()) {
        result.add(curr.toString());
        return;
      }

      String letters = phone.get(d.charAt(idx));
      for (char ch : letters.toCharArray()) {
        curr.append(ch);
        helper_fn(d, idx + 1, curr, result);
        curr.deleteCharAt(curr.length() - 1); // Backtrack
      }
    }
  }

}
