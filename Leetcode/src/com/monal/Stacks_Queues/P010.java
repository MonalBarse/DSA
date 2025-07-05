package com.monal.Stacks_Queues;
import java.util.*;
/*
 * LEETCODE 735. Asteroid Collision (https://leetcode.com/problems/asteroid-collision/)
 *
 * We are given an array asteroids of integers representing asteroids in a row.
 * The indices of the asteriod in the array represent their relative position in space.
 * For each asteroid, the absolute value represents its size, and the sign represents
 * its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.
 * Find out the state of the asteroids after all collisions. If two asteroids meet,
 * the smaller one will explode. If both are the same size, both will explode.
 * Two asteroids moving in the same direction will never meet.
 */
public class P010 {
  // Approach: Use a stack to simulate the collisions.
  // If a positive asteroid is followed by a negative one, we check their sizes.
  // If the positive asteroid is larger, it survives; if the negative one is larger,
  // it survives; if they are equal, both explode.

  class Solution {
    public int[] asteroidCollision(int[] asteroids) {
      Stack<Integer> stack = new Stack<>();
      for (int asteroid : asteroids) {
        // If the asteroid is positive, push it onto the stack
        if (asteroid > 0) {
          stack.push(asteroid);
        } else {
          // Handle collisions with negative asteroids
          while (!stack.isEmpty() && stack.peek() > 0 && stack.peek() < -asteroid) {
            stack.pop(); // Positive asteroid is smaller, it explodes
          }
          if (!stack.isEmpty() && stack.peek() == -asteroid) {
            stack.pop(); // Both asteroids are equal, both explode
          } else if (stack.isEmpty() || stack.peek() < 0) {
            stack.push(asteroid); // Negative asteroid survives or no positive asteroid to collide with
          }
        }
      }

      // Convert the stack to an array
      int[] result = new int[stack.size()];
      for (int i = result.length - 1; i >= 0; i--) {
        result[i] = stack.pop();
      }
      return result;

    }
}

}
