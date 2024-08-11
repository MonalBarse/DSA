package com.monal;

import java.util.ArrayList;
import java.util.List;

public class Permutations {
  public static void main(String[] args) {

    List<String> list = new ArrayList<>();
    list.add("def");
    list.add("are");

    List<List<String>> result = question_one(list);
    for (List<String> permList : result) {
      System.out.println(permList);
    }
  }

  // Q1. Given a List of Strings create all possible substrings with all possible permutations
  public static List<List<String>> question_one(List<String> list) {
    // We have been given a list like : [ 'are', 'def']
    // We have to create all possible substrings with all possible permutations like
    // [ 'are', 'era', 'ear', 'aer', 'rea', 'rae', 'def', 'edf', 'efd', 'dfe', 'dfe', 'fed']
    List<List<String>> result = new ArrayList<>();
    for (String str : list) {
      List<String> permutations = new ArrayList<>();
      question_oneHelper("", str, permutations);
      result.add(permutations);
    }
    return result;
  }

  public static void question_oneHelper(
      String processed, String unprocessed, List<String> permutations) {
    if (unprocessed.isEmpty()) {
      permutations.add(processed);
      return;
    }
    char ch = unprocessed.charAt(0);
    for (int i = 0; i <= processed.length(); i++) {
      String first = processed.substring(0, i);
      String second = processed.substring(i);
      question_oneHelper(first + ch + second, unprocessed.substring(1), permutations);
    }
  }
}
