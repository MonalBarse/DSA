package com.monal;

public class Basics {

  public static void main(String[] args) {
    // Q1 size 3 x 3 matrix
    // [ s * * ]
    // [ * * * ]
    // [ * * e ]
    System.out.println(problem_one(3, 3));
  }

  static int problem_one(int r, int c) {
    if (r == 1 || c == 1) {
      return 1;
    }
    return problem_one(r - 1, c) + problem_one(r, c - 1);
  }
}
