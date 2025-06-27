package com.monal.Trees.GoodProblems;

import com.monal.Trees.TreeNode;
import java.util.*;

/*
Mininum Time to Burn a Binary Tree
Given a binary tree and a target node, find the minimum time required to burn the entire tree starting from the target node.
The burning process starts from the target node and spreads to its parent and children nodes in one second
and continues until all nodes are burned.
Example:
  Input: root = [1,2,3,4,5,6,7],
  node = 2
  Output: 4
  Explaintion : We started burning from node 2, it takes 1 second to burn node 2,
  then it takes 1 second to burn its children nodes (4 and 5), and finally it takes 1 second to burn its parent node (1).


*/
public class P004 {
  public int minTime(TreeNode root, TreeNode start) {
    // Do a DFS to find the target node and build a parent map
    Map<TreeNode, TreeNode> parentMap = new HashMap<>();
    buildParentMap(root, null, parentMap);

    // BFS from teh start node to burn the whole tree
    Queue<TreeNode> q = new ArrayDeque<>();

    Set<TreeNode> visited = new HashSet<>();

    q.offer(start);
    visited.add(start);

    int time = 0;
    while (!q.isEmpty()) {
      int size = q.size();
      boolean burned = false; // Track if any node was burned in this second

      for (int i = 0; i < size; i++) {
        TreeNode current = q.poll();

        // Check parent node
        TreeNode parent = parentMap.get(current);
        if (parent != null && !visited.contains(parent)) {
          visited.add(parent);
          q.offer(parent);
          burned = true;
        }

        // Check left child
        if (current.left != null && !visited.contains(current.left)) {
          visited.add(current.left);
          q.offer(current.left);
          burned = true;
        }

        // Check right child
        if (current.right != null && !visited.contains(current.right)) {
          visited.add(current.right);
          q.offer(current.right);
          burned = true;
        }
      }

      if (burned) {
        time++; // Increment time only if at least one node was burned
      }
    }

    return time;

  }

  private void buildParentMap(TreeNode node, TreeNode parent, Map<TreeNode, TreeNode> parentMap) {
    // DFS
    if (node == null)
      return;

    parentMap.put(node, parent);
    if (node.left != null) {
      buildParentMap(node.left, node, parentMap);
    }
    if (node.right != null) {
      buildParentMap(node.right, node, parentMap);
    }
  }

  public static void main(String[] args) {
    P004 solution = new P004();

    // Example tree:
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.left.right = new TreeNode(5);
    root.right.left = new TreeNode(6);
    root.right.right = new TreeNode(7);
    // Example usage:
    // Start burning from node 2
    int timeToBurn = solution.minTime(root, root.left); // Start burning from node 2
    System.out.println("Minimum time to burn the tree: " + timeToBurn + " seconds");
    // Output: Minimum time to burn the tree: 4 seconds

  }
}
