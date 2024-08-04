package com.monal;

import java.util.*;

// Ques : 1,2 for understanding string manipulation using recursion
// Ques : 3 for Subsets of a list using recursion
// Ques : 4 for Subsequence of a string using recursion

public class Subsequence {

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
    ArrayList<String> result = question_4("", "abcd");
    System.out.println(result); */

    System.out.println("-----------------------------");
    int[] arr = {1, 2, 2};
    List<List<Integer>> subset = subsetwitdDuplicate(arr);
    System.out.println(subset);

    System.out.println("------------------------------");
    System.out.println();
  }

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

  /* // Q1: using for loops
   public static StringBuilder questioN_1(String str) {
    // using for loops
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) != 'a' && str.charAt(i) != 'A') {
        sb.append(str.charAt(i));
      }
    }
    return sb;
  } */

  public static StringBuilder question_2(String str) {
    // Given a sentence, return a sentence without any occurrence of the word "apple"
    // Word can even be adfapple or appleadgf the output should be adf or adgf
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

  // Q3: Given an ArrayList of integers, return all the possible sub-set of the list.
  public static ArrayList<ArrayList<Integer>> question_3(ArrayList<Integer> list) {
    if (list.isEmpty()) {
      ArrayList<ArrayList<Integer>> result = new ArrayList<>();
      result.add(new ArrayList<>()); // Add empty subset
      return result;
    }

    int firstElement = list.get(0);
    ArrayList<Integer> restOfList = new ArrayList<>(list.subList(1, list.size()));

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

  // Q4: Given a string, return all the possible subsequence of the string.
  /* public static ArrayList<String> question_4(String processed, String unprocessed) {
    // Base case: if the string is empty, return an empty string

    if (unprocessed.isEmpty()) {
      ArrayList<String> result = new ArrayList<>();
      result.add(processed);
      return result;
    }

    // Include the first character
    ArrayList<String> result1 =
        question_4(processed + unprocessed.charAt(0), unprocessed.substring(1));

    // Exclude the first character
    ArrayList<String> result2 = question_4(processed, unprocessed.substring(1));

    result1.addAll(result2);
    return result1;
  }
   */

  // Using for loops
  public static List<List<Integer>> subset(int[] arr) {
    List<List<Integer>> outer = new ArrayList<>();

    // Start with an empty subset
    outer.add(new ArrayList<Integer>());

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

  public static List<List<Integer>> subsetwitdDuplicate(int[] arr) {
    List<List<Integer>> outer = new ArrayList<>();

    // Start with an empty subset
    outer.add(new ArrayList<Integer>());
    int start = 0;
    int end = 0;

    // Iterate over each number in the input array
    for (int i = 0; i < arr.length; i++) {
      start = 0;
      if (i > 0 && arr[i] == arr[i - 1]) {
        start = end + 1;
      }
      int n = outer.size(); // Current size of the outer list
      end = n - 1;
      // Iterate over all existing subsets
      for (int j = 0; j < n; j++) {
        // Create a new subset from the existing subset
        List<Integer> internal = new ArrayList<>(outer.get(j));
        internal.add(arr[i]); // Add the current number to this new subset

        // Add this new subset to the outer list
        outer.add(internal);
      }
    }

    return outer;
  }
}