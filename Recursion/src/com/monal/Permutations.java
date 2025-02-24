package com.monal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutations {
  public static void main(String[] args) {

    List<String> list = new ArrayList<>();
    list.add("wex");
    list.add("mon");
    list.add("imat");
    List<List<String>> result = question_one(list);
    for (List<String> permList : result) {
      System.out.println(permList);
    }
  }

  public static List<List<String>> question_one(List<String> list) {
    // ["wex", "mon"]
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
}
