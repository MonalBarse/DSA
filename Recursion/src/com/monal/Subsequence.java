package com.monal;

import java.util.*;

// Ques : 1-2 for understanding string manipulation using recursion
// Ques : 3 for Subsets/Subsequence of a list using recursion
// Ques : 5~ Random subsequences and subsets questions

public class Subsequence {

  // Ques 1 : Given a string, edit it such that it has no 'a' or 'A' in it.
  public static String question_1(String str) {
    // Base case: if the string is empty, return an empty string
    if (str.length() == 0) {
      return "";
    }
    if (str.charAt(0) == 'a' || str.charAt(0) == 'A') {
      return question_1(str.substring(1));
    } else {
      return str.charAt(0) + question_1(str.substring(1));
    }
  }

  /*
   * // Ques 1: using for loops
   * public static StringBuilder questioN_1(String str) {
   * // using for loops
   * StringBuilder sb = new StringBuilder();
   * for (int i = 0; i < str.length(); i++) {
   * if (str.charAt(i) != 'a' && str.charAt(i) != 'A') {
   * sb.append(str.charAt(i));
   * }
   * }
   * return sb;
   * }
   */

  // ------------------------------------------------------------------------ //
  // Ques 2: Given a sentence, return a sentence without any occurrence of the
  // word "apple"
  // Word can even be adfapple or appleadgf the output should be adf or adgf
  public static StringBuilder question_2(String str) {
    if (str.isEmpty()) {
      return new StringBuilder();
    }
    if (str.length() < 5) {
      return new StringBuilder(str);
    }
    if (str.substring(0, 5).equals("apple")) {
      return question_2(str.substring(5));
    } else {
      return new StringBuilder(str.charAt(0) + question_2(str.substring(1)).toString());
    }
  }

  public static ArrayList<String> subset(String given) {
    // Base codition if the given string is empty return an empty string
    if (given.isEmpty()) {
      ArrayList<String> result = new ArrayList<>();
      result.add("");
      return result;
    }

    char firstChar = given.charAt(0);
    String restString = given.substring(1);

    // This is the recursive call to get the subsets of the rest of the string
    ArrayList<String> subsetsWithoutFirst = subset(restString);

    // This call will store the subsets with the first character
    ArrayList<String> subsetsWithFirst = new ArrayList<>();

    // This loop will add the first character to the beginning of the subset
    // and then add it to the subsetsWithFirst list
    for (String subset : subsetsWithoutFirst) {
      String newSubset = firstChar + subset;
      subsetsWithFirst.add(newSubset);
    }

    // This will add the subsetsWithFirst to the subsetsWithoutFirst list
    subsetsWithoutFirst.addAll(subsetsWithFirst);
    return subsetsWithoutFirst;
  }

  // ------------------------------------------------------------------------ //
  // Ques 3: Given an ArrayList of integers, return all the possible sub-set of
  // the list.
  public static ArrayList<ArrayList<Integer>> question_3(ArrayList<Integer> givenList) {
    if (givenList.isEmpty()) {
      ArrayList<ArrayList<Integer>> result = new ArrayList<>();
      result.add(new ArrayList<>()); // Add empty subset
      return result;
    }

    int firstElement = givenList.get(0);
    ArrayList<Integer> restOfList = new ArrayList<>(givenList.subList(1, givenList.size()));
    ArrayList<ArrayList<Integer>> subsetsWithoutFirst = question_3(restOfList);
    ArrayList<ArrayList<Integer>> subsetsWithFirst = new ArrayList<>();

    for (ArrayList<Integer> subset : subsetsWithoutFirst) {
      ArrayList<Integer> newSubset = new ArrayList<>(subset);
      newSubset.add(0, firstElement); // Add the first element at the beginning
      subsetsWithFirst.add(newSubset);
    }
    subsetsWithoutFirst.addAll(subsetsWithFirst);
    return subsetsWithoutFirst;
  }

  // ------------------------------------------------------------------------ //
  // Ques 3.0 - Subsets of a list using recursion
  public List<List<Integer>> subsets(int[] arr) {
    List<List<Integer>> result = new ArrayList<>();
    generateSubsets(arr, 0, new ArrayList<>(), result);
    return result;
  }

  private void generateSubsets(int[] arr, int index, List<Integer> processed, List<List<Integer>> result) {
    // Base case: when index reaches the end of the array
    if (index == arr.length) {
      result.add(new ArrayList<>(processed)); // Store a copy of the current subset
      return;
    }
    // Choice 1: Exclude the current element (unprocessed)
    generateSubsets(arr, index + 1, processed, result);
    // Choice 2: Include the current element (processed)
    processed.add(arr[index]); // Add the current element
    generateSubsets(arr, index + 1, processed, result);
    processed.remove(processed.size() - 1); // Backtrack to remove last added element
  }

  // Ques 3.1: Given a string, return all the possible subsequence of the string.
  /*
   * public static ArrayList<String> question_4(String processed, String
   * unprocessed) {
   * // Base case: if the string is empty, return an empty string
   *
   * if (unprocessed.isEmpty()) {
   * ArrayList<String> result = new ArrayList<>();
   * result.add(processed);
   * return result;
   * }
   *
   * // Include the first character
   * ArrayList<String> result1 =
   * question_4(processed + unprocessed.charAt(0), unprocessed.substring(1));
   *
   * // Exclude the first character
   * ArrayList<String> result2 = question_4(processed, unprocessed.substring(1));
   *
   * result1.addAll(result2);
   * return result1;
   * }
   */

  // Using for loops
  // Ques 3.2: This quesiton is similar to the question 3 (subset of a list using
  // iteration)
  public static List<List<Integer>> subset(int[] arr) {
    List<List<Integer>> outer = new ArrayList<>();

    outer.add(new ArrayList<>());

    // Iterate over each number in the input array
    for (int num : arr) {
      int n = outer.size(); // Current size of the outer list

      // Iterate over all existing subsets
      for (int i = 0; i < n; i++) {

        // Create a new subset from the existing subset
        List<Integer> internal = new ArrayList<>(outer.get(i));
        internal.add(num); // Add the current number to this new subset

        // Add this new subset to the outer list
        outer.add(internal);
      }
    }

    return outer;
  }

  // Ques 3.3: Same as question 3 but it has duplicates in the array
  public static List<List<Integer>> subsetwitdDuplicate(int[] arr) {
    List<List<Integer>> subsets = new ArrayList<>(); // This will store all the subsets

    // Start with an empty subset
    subsets.add(new ArrayList<>());

    int startIndex; // To manage subsets to be considered when the current element is a duplicate
    int endIndex = 0; // To manage the last index of the current subsets

    // Iterate over each number in the input array
    for (int i = 0; i < arr.length; i++) {
      // If the current element is a duplicate, we only want to consider subsets
      // that were formed in the last iteration (to avoid duplicate subsets)
      startIndex = 0;
      if (i > 0 && arr[i] == arr[i - 1]) {
        startIndex = endIndex + 1; // This ensures we avoid repeating subsets with the same element
      }

      // The number of subsets formed so far
      int currentSize = subsets.size();
      endIndex = currentSize - 1; // Update the end index for the next iteration

      // For every existing subset, create a new subset by adding the current element
      for (int j = startIndex; j < currentSize; j++) {
        List<Integer> newSubset = new ArrayList<>(subsets.get(j)); // Copy the current subset
        newSubset.add(arr[i]); // Add the current element to the subset
        subsets.add(newSubset); // Add the new subset to the list
      }
    }
    return subsets;

  }

  // Same as above but recursively
  public static List<List<Integer>> subsetWithDuplicateRecr(int[] arr) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(arr); // Sort to bring duplicates together
    generateSubsetsRecr(arr, 0, result, new ArrayList<>());
    return result;
  }

  private static void generateSubsetsRecr(int[] arr, int index, List<List<Integer>> result, List<Integer> processed) {
    result.add(new ArrayList<>(processed));

    for (int i = index; i < arr.length; i++) {
      if (i > index && arr[i] == arr[i - 1]) {
        continue;
      }
      processed.add(arr[i]);
      generateSubsetsRecr(arr, i + 1, result, processed);
      processed.remove(processed.size() - 1);
    }
  }

  // Premutation with spaces
  /*
   * In this problem we are given a string `str` and we have to add spaces
   * between the characters of the string to form all possible combinations.
   * except the first and last character
   *
   * Input: str = "readf"
   * Output: ["readf", "rea df", "rea d f", "re adf", "re a df",
   * "re a d f", "r eadf", "r e adf", "r e a df", "r e a d f"]
   */

  public List<String> addSpaces(String str) {
    List<String> result = new ArrayList<>();
    if (str == null || str.length() <= 1) {
      if (str != null)
        result.add(str);
      return result;
    }

    // Start with the first character already in the current string
    // We don't add space before the first character
    addSpacesHelper(str, 1, Character.toString(str.charAt(0)), result);
    return result;
  }

  /**
   * Helper method that uses backtracking to generate all permutations.
   *
   * @param str     The original input string
   * @param idx     Current position in the string being processed
   * @param current Current permutation being built
   * @param result  List to store all valid permutations
   */
  private void addSpacesHelper(String str, int idx, String current, List<String> result) {
    // Base case: reached the end of string
    if (idx == str.length()) {
      result.add(current);
      return;
    }

    // Option 1: Add current character without adding a space before it
    addSpacesHelper(str, idx + 1, current + str.charAt(idx), result);

    // Option 2: Add a space before the current character
    // We can add space before any character except the first one
    addSpacesHelper(str, idx + 1, current + " " + str.charAt(idx), result);
  }

  // ------------------------------------- //

  // AMAZON QUESTION

  // Given a list of Strings, return the number of subsequences
  // that have a vowel count less that or equal to a given threshold.
  // Method to count vowels in a string

  public static ArrayList<ArrayList<String>> generateSubsequences(ArrayList<String> list) {
    if (list.isEmpty()) {
      ArrayList<ArrayList<String>> result = new ArrayList<>();
      result.add(new ArrayList<>()); // Add empty subset
      return result;
    }

    String firstElement = list.get(0);
    ArrayList<String> restOfList = new ArrayList<>(list.subList(1, list.size()));

    ArrayList<ArrayList<String>> subsetsWithoutFirst = generateSubsequences(restOfList);
    ArrayList<ArrayList<String>> subsetsWithFirst = new ArrayList<>();

    for (ArrayList<String> subset : subsetsWithoutFirst) {
      ArrayList<String> newSubset = new ArrayList<>(subset);
      newSubset.add(0, firstElement); // Add the first element at the beginning
      subsetsWithFirst.add(newSubset);
    }

    subsetsWithoutFirst.addAll(subsetsWithFirst);
    return subsetsWithoutFirst;
  }

  public static int countValidSubsequences(ArrayList<String> list, int threshold) {
    ArrayList<ArrayList<String>> allSubsequences = generateSubsequences(list);
    int count = 0;

    for (ArrayList<String> subsequence : allSubsequences) {
      int vowelCount = 0;
      for (String s : subsequence) {
        vowelCount += countVowels(s);
      }
      if (vowelCount <= threshold) {
        count++;
      }
    }
    // Subtract 1 to exclude the empty subsequence
    return count - 1;
  }

  public static int countVowels(String s) {
    String vowels = "aeiouAEIOU";
    int count = 0;
    for (char c : s.toCharArray()) {
      if (vowels.indexOf(c) != -1) {
        count++;
      }
    }
    return count;
  }

  // ------------------------------------------------------------------------ //
  // GOOGLE QUESTION
  /*
   * using Backtraking Given a string containing digits from 2-9 inclusive, return
   * all possible letter
   * tcombinations tha the number could represent. Return the answer in any order.
   * (A mapping of digits to letters just like on the telephone buttons).
   * Note that 1 does not map to any letters.
   * Input: digits = "23"
   * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
   * Input: digits = ""
   * Output: []
   */

  private static final String[] mapping = {
      "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
  };

  public List<String> letterCombinations(String digits) {
    List<String> res = new ArrayList<>();
    if (digits.length() == 0)
      return res;

    combinations(digits, 0, new StringBuilder(), res);
    return res;
  }

  private void combinations(String digits, int pos, StringBuilder sb, List<String> res) {
    if (pos == digits.length()) {
      res.add(new String(sb));
      return;
    }

    String letters = mapping[digits.charAt(pos) - '2'];
    for (int i = 0; i < letters.length(); i++) {
      sb.append(letters.charAt(i));
      combinations(digits, pos + 1, sb, res);
      sb.deleteCharAt(sb.length() - 1);
    }
  }

  // ------------------------------------------------------------------------ //
  // Dice Question
  /*
   * Given a dice with 6 faces, each face having a number from 1 to 6. and a
   * target number N,
   * find the number of ways to get the target number N by
   * rolling the dice. you can roll the any number of times)
   * For eg, if N = 3, the output should be [111, 12, 21] because
   * we can get 3 by rolling the in the following ways: 1+1+1, 1+2, 2+1
   */

  public static List<String> diceRoll(int target) {
    return diceRollHelper("", target);
  }

  public static List<String> diceRollHelper(String p, int target) {
    if (target == 0) {
      List<String> result = new ArrayList<>();
      result.add(p);
      return result;
    }
    if (target < 0) {
      return new ArrayList<>();
    }
    List<String> result = new ArrayList<>();

    for (int i = 1; i <= 6 && i <= target; i++) {

      result.addAll(diceRollHelper(p + i, target - i));
      // p + i will add the number to the string
    }

    return result;
  }

  // Ques Given an array of distinct integers - arr and a target integer - target,
  // return the number of possible combinations that add up to target
  public int combinationSum4(int[] arr, int target) {
    int[] dp = new int[target + 1];
    Arrays.fill(dp, -1); // uncomputed
    return countOutput(arr, target, dp);

  }

  public static int countOutput(int[] arr, int target, int[] dp) {
    if (target == 0)
      return 1; // valid combination
    if (target < 0)
      return 0; // Exceeded target, stop

    if (dp[target] != -1)
      return dp[target];

    int count = 0;
    for (int num : arr) {
      count += countOutput(arr, target - num, dp); // Recursive call
    }
    dp[target] = count;
    return count;
  }

  public static void main(String[] args) {

    // Q1: Given a string, edit it such that it has no 'a' or 'A' in it.
    // For example, if the input string is "apple", the output should be "pple".
    String str, str2;
    str = "BlushA & aGalaoawa";
    str2 = "Adioapples";
    System.out.println(question_1(str));
    System.out.println(question_2(str2));

    System.out.println("-----------------------------");
    ArrayList<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    list.add(3);

    ArrayList<ArrayList<Integer>> subsets = question_3(list);
    System.out.println(subsets);

    System.out.println("-----------------------------");
    /*
     * ArrayList<String> result = question_4("", "abcd");
     * System.out.println(result);
     */
    System.out.println("-----------------------------");
    int[] arr = { 1, 2, 2 };
    List<List<Integer>> subset = subsetwitdDuplicate(arr);
    System.out.println(subset);

    System.out.println("------------------------------");
    System.out.println();
  }
}
