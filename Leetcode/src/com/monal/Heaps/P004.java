package com.monal.Heaps;

import java.util.*;

public class P004 {
  public class Pair {
    int value;
    int coords[];
    int x, y;

    Pair(int value, int[] coords) {
      this.value = value;
      this.coords = coords;
      this.x = coords[0];
      this.y = coords[1];
    }
  }

  public int[][] kClosestToOrigin(int[][] coordinates, int k) {
    int n = coordinates.length;
    if (k <= 0 || n == 0)
      return new int[0][0];
    if (k >= n)
      return coordinates;
    int[][] result = new int[k][2];
    // the value is x^2 + y^2 to be compared in the max heap
    PriorityQueue<Pair> maxHeap = new PriorityQueue<>((a, b) -> {
      if (a.value != b.value)
        return b.value - a.value; // larger distance
      if (a.x != b.x)
        return a.x - b.x; // if equal
      return a.y - b.y; // if equal, remove smaller y
    });
    for (int i = 0; i < n; i++) {
      int x = coordinates[i][0];
      int y = coordinates[i][1];
      int value = x * x + y * y; // calculate the distance from origin
      Pair pair = new Pair(value, coordinates[i]);
      maxHeap.offer(pair);
      if (maxHeap.size() > k)
        maxHeap.poll(); // remove the farthest point

    }

    // fill the result array with the k closest points
    int index = 0;
    while (!maxHeap.isEmpty()) {
      Pair pair = maxHeap.poll();
      result[index][0] = pair.x;
      result[index][1] = pair.y;
      index++;
    }

    // sort the result array by x and then by y
    Arrays.sort(result, (a, b) -> {
      if (a[0] != b[0])
        return a[0] - b[0]; // sort by x
      return a[1] - b[1]; // sort by y
    });
    return result;

  }
}
