package com.monal.Array;

// Find Intersection and Union of the given arrays

// Link - https://www.geeksforgeeks.org/union-and-intersection-of-two-sorted-arrays-2/
// Intersection: Elements that are common in both arrays
// Union: Elements that are present in either of the arrays

/*
 * Approach:
 *
 * Intersection:
 *  - Convert the first array to a HashSet for O(1) lookup
 *  - Iterate through the second array and check if the element exists in the HashSet
 *  - If it does, add it to the result list
 *  - Return the result list
 *
 * Union:
 *  - Use a HashSet to automatically handle duplicates
 *  - Add all elements from both arrays to the HashSet
 *  - Convert the HashSet back to a list and return it
 */

import java.util.*;

public class P002 {

  public static void main(String[] args) {
    // Example arrays
    String array1[] = { "A", "B", "C", "D" };
    String array2[] = { "B", "D", "E" };
    int[] arr1 = { 1, 2, 3, 4, 5 };
    int[] arr2 = { 3, 4, 5, 6, 7 };

    // Find intersection and union
    List<String> intersectionResult = intersection(array1, array2);
    List<String> unionResult = union(array1, array2);

    int intersectionResultInt = intersection(arr1, arr2);
    int unionResultInt = union(arr1, arr2);

    // Print results
    System.out.println("Intersection: " + intersectionResult);
    System.out.println("Union: " + unionResult);

    System.out.println("Intersection of primitive array: " + intersectionResultInt);
    System.out.println("Union of primitive array: " + unionResultInt);
  }

  public static <T> List<T> intersection(T[] arr1, T[] arr2) {
    // Convert arrays to HashSet for O(1) lookup
    Set<T> set1 = new HashSet<>(Arrays.asList(arr1));

    // Create result list
    List<T> result = new ArrayList<>();

    // Check each element in arr2 if it exists in set1
    for (T element : arr2) {
      if (set1.contains(element)) {
        result.add(element);
      }
    }

    return result;
  }

  public static <T> List<T> union(T[] arr1, T[] arr2) {
    // Use HashSet to automatically handle duplicates
    Set<T> unionSet = new HashSet<>();

    // Add all elements from both arrays
    unionSet.addAll(Arrays.asList(arr1));
    unionSet.addAll(Arrays.asList(arr2));

    // Convert set back to list
    return new ArrayList<>(unionSet);
  }

  public static int intersection(int[] arr1, int[] arr2) {
    // Convert arrays to HashSet for O(1) lookup
    Set<Integer> set1 = new HashSet<>();
    for (int i : arr1) {
      set1.add(i);
    }

    // Create result list
    int count = 0;

    // Check each element in arr2 if it exists in set1
    for (int element : arr2) {
      if (set1.contains(element)) {
        count++;
      }
    }

    return count;
  }

  public static int union(int[] arr1, int[] arr2) {
    // Use HashSet to automatically handle duplicates
    Set<Integer> unionSet = new HashSet<>();

    // Add all elements from both arrays
    for (int i : arr1) {
      unionSet.add(i);
    }
    for (int i : arr2) {
      unionSet.add(i);
    }

    // Convert set back to list
    return unionSet.size();
  }
}
