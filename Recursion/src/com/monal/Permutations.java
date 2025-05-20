package com.monal;

import java.util.*;

public class Permutations {

  /*
   * Ques: Given a List of Strings create all possible substrings with all
   * possible permutations like
   * Example:
   * Input: ["wex", "mon", "imat"]
   * Output: [
   * ["wex", "wxe", "ewx", "exw", "xwe", "xew"],
   * ["mon", "mno", "omn", "onm", "nom", "nmo"],
   * ["imat", "imta", "iamt", "iatm", "itma", "itam"]
   * ]
   */
  public static List<List<String>> question_one(List<String> list) {
    List<List<String>> result = new ArrayList<>();

    // Process each word in the list
    for (String word : list) {
      List<String> permutations = new ArrayList<>();
      question_oneHelper(word.toCharArray(), 0, permutations);
      result.add(permutations);
    }

    return result;
  }

  public static void question_oneHelper(char[] chars, int index, List<String> result) {
    if (index == chars.length) {
      result.add(new String(chars)); // Store the full permutation
      return;
    }

    Set<Character> used = new HashSet<>(); // To avoid duplicate swaps

    for (int i = index; i < chars.length; i++) {
      if (used.contains(chars[i]))
        continue; // Skip duplicate characters

      used.add(chars[i]);
      swap(chars, index, i); // Swap current character
      question_oneHelper(chars, index + 1, result);
      swap(chars, index, i); // Backtrack
    }
  }

  public static void swap(char[] arr, int i, int j) {
    char temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  // // Q1. Given a List of Strings create all possible substrings with all
  // possible permutations
  // public static List<List<String>> question_one(List<String> list) {
  // // We have been given a list like : [ 'are', 'def']
  // // We have to create all possible substrings with all possible permutations
  // like
  // // [ 'are', 'era', 'ear', 'aer', 'rea', 'rae', 'def', 'edf', 'efd', 'dfe',
  // 'dfe', 'fed']
  // List<List<String>> result = new ArrayList<>();
  // for (String str : list) {
  // List<String> permutations = new ArrayList<>();
  // question_oneHelper("", str, permutations);
  // result.add(permutations);
  // }
  // return result;
  // }

  // public static void question_oneHelper(
  // String processed, String unprocessed, List<String> permutations) {
  // if (unprocessed.isEmpty()) {
  // permutations.add(processed);
  // return;
  // }
  // char ch = unprocessed.charAt(0);
  // for (int i = 0; i <= processed.length(); i++) {
  // String first = processed.substring(0, i);
  // String second = processed.substring(i);
  // question_oneHelper(first + ch + second, unprocessed.substring(1),
  // permutations);
  // }
  // }

  /**
   * Q: Given a string, return all possible permutations of the string with
   * case changes.
   *
   * Examples
   * Input: "awb"
   * Output: ["awb", "Awb", "aWb", "AWb", "awb", "AwB", "aWB", "AWB"]
   *
   * Input: "a1b"
   * Output: ["a1b", "A1b", "a1B", "A1B"]
   *
   * @param str
   * @return
   */
  private List<String> permutationWithCaseChange(String str) {
    List<String> result = new ArrayList<>();
    helper_1(str.toCharArray(), 0, result);
    return result;

  }

  private void helper_1(char[] str, int idx, List<String> res) {
    if (idx == str.length) {
      res.add(new String(str));
      return;
    }

    // If the current char is a letter, we keep it or change its case
    if (Character.isLetter(str[idx])) {

      // change to lowercase
      str[idx] = Character.toLowerCase(str[idx]);
      helper_1(str, idx + 1, res);

      // change to uppercase
      str[idx] = Character.toUpperCase(str[idx]);
      helper_1(str, idx + 1, res);
    } else {
      // If it's not a letter, we just move to the next character
      helper_1(str, idx + 1, res);
    }
  }

  public static void main(String[] args) {

    // List<String> list = new ArrayList<>();
    // list.add("wex");
    // list.add("mon");
    // list.add("imat");
    // List<List<String>> result = question_one(list);
    // for (List<String> permList : result) {
    // System.out.println(permList);
    // }
    // // Expected : [[wex, wxe, ewx, exw, xwe, xew], [mon, mno, omn, onm, nom,
    // nmo],
    // // [imat, imta, iamt, iamt, itma, itam, mait, mait, mtia, mtai

    // For permutations with case changes
    List<String> result = new Permutations().permutationWithCaseChange("a1b");
    System.out.println(result);
    // Expected : [a1b, A1b, a1B, A1B]

    List<String> result2 = new Permutations().permutationWithCaseChange("awb");
    System.out.println(result2);
    // Expected : [awb, Awb, aWb, AWb, awb, AwB, aWB, AWB]

    List<String> result3 = new Permutations().permutationWithCaseChange("AB2c");
    System.out.println(result3);
    // Expected : [AB2c, Ab2c, aB2c, ab2c, AB2C, Ab2C, aB2C, ab2C,

  }
}
