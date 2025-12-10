package Companies.Medianet;

import java.util.*;
import java.io.*;

/*
We have a complete rooted tree (root = 1).
Each node i has:
A[i]: number of fruits on it initially.
B[i]: capacity — how many fruits it can hold at any time.

Rules:
  On each shake, each node (if it has ≥ 1 fruit) passes one fruit to its parent.
  After fruits move, if any node has more fruits than its capacity, the excess instantly falls to the ground.
  Root’s excess also goes to the ground directly.
  We must find the minimum number of shakes needed for all fruits to reach the ground.
*/
public class P002 {
  class Solution {
    private int[] depth;
    private long[] fruits;
    private long[] capacity;
    private  List<List<Integer>> tree;
    private long shakes;
    private boolean possible;

    private long solve(int[] A, int[] B, int[][] C) {
      // build the adj list
      int n = A.length;
      this.tree = new ArrayList<>();
      for (int i = 0; i <= n; i++)
        tree.add(new ArrayList<>());
      for (int[] edge : C) {
        tree.get(edge[0] - 1).add(edge[1] - 1);
        tree.get(edge[1] - 1).add(edge[0] - 1);
      }

      // convert int to long
      this.fruits = new long[n];
      this.capacity = new long[n];
      for (int i = 0; i < n; i++) {
        fruits[i] = A[i];
        capacity[i] = B[i];
      }

      // calculate depth
      this.depth = new int[n];
      Arrays.fill(depth, -1);
      calcluateDepth(tree, 0, 0);

      long start = 0, end = (long) 2e14; // max possible shakes
       // 2e14
      long ans = end;
      while(start <= end) {
        long mid = start + (end - start) /2 ;
        if(canAllFruitsFall(mid)){
          ans = mid;
          end = mid - 1;
        } else start = mid + 1;
      }

      return ans;
    }

    private long dfs(int node, int parent) {
      if(!possible) return 0;
      long fruitsFromSubtrees = fruits[node];
      for(int neigh : tree.get(node))
        if(neigh != parent)
          fruitsFromSubtrees += dfs(neigh, node);

      long fruitsToPass = Math.min(capacity[node], fruitsFromSubtrees);

      // root check
      if(node == 0) if(fruitsToPass > shakes)possible = false;
      else if(fruitsToPass > 0) if((long) depth[node] + fruitsToPass > shakes) possible = false;
      return fruitsToPass;
    }

    private boolean canAllFruitsFall(long minShakes) {
      this.shakes = minShakes;
      this.possible = true;
      dfs(0, -1);
      return possible;
    }

    private void calcluateDepth(List<List<Integer>> tree, int node, int depth) {
      Queue<int[]> queue = new LinkedList<>(); // {node, depth}
      queue.add(new int[] { node, depth });
      this.depth[node] = depth;
      while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int currNode = curr[0], currDepth = curr[1];
        for (int neigh : tree.get(currNode)) {
          if (this.depth[neigh] == -1) {
            this.depth[neigh] = currDepth + 1;
            queue.add(new int[] { neigh, currDepth + 1 });
          }
        }
      }
    }

  }

  public static void main(String[] args) throws IOException {
    Solution s = new P002().new Solution();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(br.readLine().trim());
    int[] A = new int[n];
    int[] B = new int[n];
    String[] aLine = br.readLine().trim().split(" ");
    String[] bLine = br.readLine().trim().split(" ");
    for (int i = 0; i < n; i++)
      A[i] = Integer.parseInt(aLine[i]);
    for (int i = 0; i < n; i++)
      B[i] = Integer.parseInt(bLine[i]);
    int[][] C = new int[n - 1][2];
    for (int i = 0; i < n - 1; i++) {
      String[] parts = br.readLine().trim().split(" ");
      C[i][0] = Integer.parseInt(parts[0]);
      C[i][1] = Integer.parseInt(parts[1]);
    }
    System.out.println(s.solve(A, B, C));
  }
}
