package com.monal.Heaps;

import java.util.*;
// 846. Hand of Straights (https://leetcode.com/problems/hand-of-straights/)

/*
Alice has some number of cards and she wants to rearrange the cards into
groups so that each group is of size groupSize, and consists of groupSize consecutive cards.
Given an integer array hand where hand[i] is the value written on
the ith card and an integer groupSize, return true if she can rearrange the cards, or false otherwise.
Example 1:
  Input: hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
  Output: true
  Explanation: Alice's hand can be rearranged as [1,2,3],[2,3,4],[6,7,8]
Example 2:
  Input: hand = [1,2,3,4,5], groupSize = 4
  Output: false
  Explanation: Alice's hand can not be rearranged into groups of 4.
*/

public class P002 {
  public boolean isNStraightHand(int[] hand, int groupSize) {
    if (hand.length % groupSize != 0)
      return false;

    Arrays.sort(hand);
    Map<Integer, Integer> freqMap = new HashMap<>();

    for (int card : hand) {
      int frequency = freqMap.getOrDefault(card, 0);
      freqMap.put(card, frequency + 1);
    }

    for (int card : hand) {
      if (freqMap.get(card) > 0) {
        for (int i = card; i < card + groupSize; i++) {
          if (freqMap.getOrDefault(i, 0) == 0)
            return false;
          freqMap.put(i, freqMap.get(i) - 1);
        }
      }
    }
    return true;
  }

}
