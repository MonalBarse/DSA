package com.monal;

/*
    The chess knight has a unique movement, it may move two squares vertically and one square horizontally,
    or two squares horizontally and one square vertically (with both forming the shape of an L).
    The possible movements of chess knight are shown in this diagram:

    A chess knight can move as indicated in the chess diagram below:
    [ ] [x] [ ] [x]
    [x] [ ] [ ] [ ]
    [ ] [ ] [♞] [ ]
    [x] [ ] [ ] [ ]
    [ ] [x] [ ] [x]

    We have a chess knight and a phone pad as shown below, the knight can only stand on a numeric cell (i.e. not on # and *).
    -> ♞ - Knight

    -> phone pad:
      [1] [2] [3]
      [4] [5] [6]
      [7] [8] [9]
      [*] [0] [#]

    Given an integer n, return how many distinct phone numbers of length n we can dial.

    You are allowed to place the knight on any numeric cell initially and then
    you should perform n - 1 jumps to dial a number of length n.
    All jumps should be valid knight jumps.

    As the answer may be very large, return the answer modulo 109 + 7.

    1. Example 1:
     Input: n = 1
     Output: 10
     Explanation: We need to dial a number of length 1, so placing the knight over any numeric cell of the 10 cells is sufficient.

    2. Example 2:
     Input: n = 2
     Output: 20
     Explanation: All the valid number we can dial are [04, 06, 16, 18, 27, 29, 34, 38, 40, 43, 49, 60, 61, 67, 72, 76, 81, 83, 92, 94]

    3. Example 3:
     Input: n = 3131
     Output: 136006598
     Explanation: Please take care of the mod.
*/
public class KnightDialer {
  static final int mod = (int) 1e9 + 7;
  // mod for large numbers i.e for if n is large number then we will do this : count = count % mod;
  // for eg if n = 1000000000 then count = count % mod; => count = 1000000000 % 1000000007 =
  // 999999993

  static final int[][] MOVES = {
    // for 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
    {4, 6}, {6, 8}, {7, 9}, {4, 8}, {0, 3, 9}, {}, {0, 1, 7}, {2, 6}, {1, 3}, {2, 4}
  };

  static final int[][] cache = new int[5001][10]; // 5001 is the maximum value of n (1 <= n <= 5000)

  public static int knightDialer(int n) {
    return knightDialer(n, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
  }

  public static int knightDialer(int remaining, int[] nextNumbers) {
    if (remaining == 1) {
      return nextNumbers.length;
    }
    // count is the number of ways to dial a number of length remaining
    int count = 0;

    // sare numbers ke liye recusively call karna hai as we have to dial a number of length
    // remaining
    for (int nextNumber : nextNumbers) {
      int cur = cache[remaining][nextNumber];

      // caching me nahi mila to normally call karo
      if (cur == 0) {
        cur = knightDialer(remaining - 1, MOVES[nextNumber]);
        cache[remaining][nextNumber] = cur;
      }
      count += cur; // count me add
      count %= mod; // mod for large numbers
    }
    return count;
  }

  public static void main(String[] args) {
    System.out.println("knightDialer Problem");
    System.out.println("=====================");
    System.out.println("Example 1: " + knightDialer(1));
    System.out.println("Example 2: " + knightDialer(2));
    System.out.println("Example 3: " + knightDialer(3131));
  }
}
