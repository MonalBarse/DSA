package com.monal.Trees.GoodProblems;

import com.monal.Trees.TreeNode;
import java.util.*;

/*
 PRint all the nodes at distance k from a given node in a binary tree.
 Given a binary tree and a target node, print all the nodes that are at dsitandce k from the target node.

 Example:
  Input: root = [1,2,3,4,5,6,7],
  target = 2, k = 1
  Output: [1,4,5]

 */
public class P002 {

  public List<Integer> distanceKBFS(TreeNode root, TreeNode target, int k) {
    // Phase 1: Build parent map to convert tree to undirected graph
    Map<TreeNode, TreeNode> parentMap = new HashMap<>();
    Queue<TreeNode> q = new LinkedList<>();

    q.offer(root);
    parentMap.put(root, null);

    // Build complete parent map for entire tree
    while (!q.isEmpty()) {
      TreeNode node = q.poll();
      if (node.left != null) {
        parentMap.put(node.left, node);
        q.offer(node.left);
      }
      if (node.right != null) {
        parentMap.put(node.right, node);
        q.offer(node.right);
      }
    }

    // BFS from target treating tree as undirected graph
    List<Integer> result = new ArrayList<>();
    Set<TreeNode> visited = new HashSet<>();

    q.offer(target); // reuse q as it's empty now
    visited.add(target); // track visited just like in graph BFS

    int distance = 0; // Distance from target node to track `k`

    while (!q.isEmpty() && distance <= k) {
      int size = q.size();
      // If we reach the desired distance, collect results
      if (distance == k) {
        for (int i = 0; i < size; i++) {
          result.add(q.poll().val);
        }
        break;
      }

      // Process all nodes at current dist
      for (int i = 0; i < size; i++) {
        TreeNode current = q.poll();

        // Add all neighbors: parent, left child, right child
        // If it was a graph we -
        // for(int neighbor: grap.get(current))

        TreeNode parent = parentMap.get(current);
        if (parent != null && !visited.contains(parent)) {
          visited.add(parent);
          q.offer(parent);
        }

        if (current.left != null && !visited.contains(current.left)) {
          visited.add(current.left);
          q.offer(current.left);
        }

        if (current.right != null && !visited.contains(current.right)) {
          visited.add(current.right);
          q.offer(current.right);
        }
      }

      distance++;
    }

    return result;
  }

  // APPROACH 2: DFS SOLUTION (Tree-based thinking)
  public List<Integer> distanceKDFS(TreeNode root, TreeNode target, int k) {
    List<Integer> result = new ArrayList<>();
    Map<TreeNode, Integer> nodeToDistFromTarget = new HashMap<>();

    // Find all nodes on path from root to target
    findDistancesToTarget(root, target, nodeToDistFromTarget);

    // DFS to find all nodes at distance k
    dfsCollectNodes(root, k, 0, nodeToDistFromTarget, result);

    return result;
  }

  private int findDistancesToTarget(TreeNode root, TreeNode target, Map<TreeNode, Integer> nodeToDistFromTarget) {
    if (root == null)
      return -1; // Target not found

    if (root == target) {
      nodeToDistFromTarget.put(root, 0); // Target has distance 0 to itself
      return 0; // Found target, return distance
    }

    // Try left subtree
    int leftDist = findDistancesToTarget(root.left, target, nodeToDistFromTarget);
    if (leftDist >= 0) { // Target found in left subtree
      nodeToDistFromTarget.put(root, leftDist + 1);
      return leftDist + 1;
    }

    // Try right subtree
    int rightDist = findDistancesToTarget(root.right, target, nodeToDistFromTarget);
    if (rightDist >= 0) { // Target found in right subtree
      nodeToDistFromTarget.put(root, rightDist + 1);
      return rightDist + 1;
    }

    return -1; // Target not found in this subtree
  }

  private void dfsCollectNodes(TreeNode root, int k, int currentDist,
      Map<TreeNode, Integer> nodeToDistFromTarget, List<Integer> result) {
    if (root == null)
      return;

    // If this node is on the path to target, update current distance
    if (nodeToDistFromTarget.containsKey(root)) {
      currentDist = nodeToDistFromTarget.get(root);
    }

    // If we've reached distance k, add to result
    if (currentDist == k) {
      result.add(root.val);
    }

    // Continue DFS with incremented distance
    dfsCollectNodes(root.left, k, currentDist + 1, nodeToDistFromTarget, result);
    dfsCollectNodes(root.right, k, currentDist + 1, nodeToDistFromTarget, result);
  }

  /*
   * KEY DIFFERENCES:
   *
   * BFS APPROACH (Your Solution):
   * ✅ Intuitive: Convert tree to graph, then do standard BFS
   * ✅ General: Works for any graph distance problem
   * ✅ Clear phases: Build graph, then search
   * ❌ Space: O(n) for parent map + O(n) for queue + O(n) for visited
   * ❌ Time: O(n) + O(n) = O(n) but with higher constants
   *
   * DFS APPROACH (New Solution):
   * ✅ Tree-specific: Leverages tree structure cleverly
   * ✅ Space efficient: Only stores nodes on root-to-target path
   * ✅ Elegant: No need to convert tree to graph
   * ✅ Single traversal: Collects results in one DFS pass
   * ❌ Less intuitive: Requires understanding of tree path properties
   * ❌ More complex logic: Distance tracking is trickier
   *
   * ALGORITHMIC INSIGHTS:
   *
   * BFS Approach Logic:
   * 1. "Treat tree as undirected graph"
   * 2. "Do BFS from target node"
   * 3. "Level = distance"
   *
   * DFS Approach Logic:
   * 1. "Find path from root to target"
   * 2. "For nodes ON this path, distance to target is known"
   * 3. "For nodes NOT on path, distance = distance_to_path_node + steps_down"
   * 4. "DFS and track distance, updating when we hit path nodes"
   *
   * Both are O(n) time, but DFS has better space complexity in practice!
   */
}
