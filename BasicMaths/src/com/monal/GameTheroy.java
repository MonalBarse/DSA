/*
  You can't possibly imagine how cold our friends are this winter in Nvodsk! Two of them play the following game
  to warm up: initially a piece of paper has an integer q. During a move a player should write any integer
  number that is a non-trivial divisor of the last written number. Then he should run this number of
  circles around the hotel. Let us remind you that a number's divisor is called non-trivial if
  it is different from one and from the divided number itself.

  The first person who can't make a move wins as he continues to lie in his warm bed under
  three blankets while the other one keeps running. Determine which player wins considering
  that both players play optimally. If the first player wins, print any winning first move.

  Input
    The first line contains the only integer q (1 ≤ q ≤ 1013).


  Output
    In the first line print the number of the winning player (1 or 2).
    If the first player wins then the second line should contain another integer — his first move
    (if the first player can't even make the first move, print 0).
    If there are multiple solutions, print any of them.

Examples
  Input
    6
  Output
    2

  Input
    30
  Output
    1
    6

  Input
    1
  Output
    1
    0
-------------------------------------
Note
  Number 6 has only two non-trivial divisors: 2 and 3. It is impossible to make a move after the numbers 2 and 3
  are written, so both of them are winning, thus, number 6 is the losing number.
  A player can make a move and write number 6 after number 30; 6, as we know, is a losing number.
  Thus, this move will bring us the victory.
 */

package com.monal;

public class GameTheroy {
  public static void main() {

    // Taking the input
    Scanner sc = new Scanner(System.in);
    long q = sc.nextLong();
    sc.close();

    // Calling the function to determine the winner
    whoWon(q);
  }

  public static int whoWon(int q) {

    // Basic checks
    if (q == 1) {
      // If the number is 1, the first player wins
      System.out.println("1\n0");
      return;
    }

    long smallestDivisor = -1;
    for (long i = 2; i * i <= q; i++) {
      if (q % i == 0) {
        smallestDivisor = i;
        break;
      }
    }

    if (smallestDivisor == -1) {
      // q is prime, so first player loses
      System.out.println("1\n0");
    } else {
      long secondDivisor = q / smallestDivisor;
      boolean isLosingPosition = true;

      // Check if q has exactly two divisors (prime * prime case)
      for (long i = smallestDivisor + 1; i * i <= q; i++) {
        if (q % i == 0) {
          isLosingPosition = false;
          break;
        }
      }

      if (isLosingPosition) {
        System.out.println("2");
      } else {
        System.out.println("1");
        System.out.println(smallestDivisor);
      }
    }
  }
}
