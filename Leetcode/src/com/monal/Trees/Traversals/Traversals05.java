package com.monal.Trees.Traversals;

import java.util.*;

// ================== MORRIS TRAVERSALS ==================== //
/*
We have studied Normal In-order, Pre-order, Post-order, and Level-order traversals.
They take up Time - O(N) and Space - O(N) in the worst case.
This Section we will learn about Morris Traversals.

Morris Traversal is a tree traversal technique that allows us to traverse a binary tree
without using recursion or stack, achieving O(1) space complexity while maintaining O(N) time complexity.

KEY CONCEPT:
- Uses the concept of "threaded binary trees"
- Temporarily creates links to facilitate traversal
- Breaks these links after using them to restore original tree structure

CORE IDEA:
- For each node, find its inorder predecessor (rightmost node in left subtree)
- Create a temporary link from predecessor to current node
- Use this link to come back to current node after processing left subtree
- Remove the temporary link once used

ADVANTAGES:
1. Space Complexity: O(1) - No recursion stack or explicit stack needed
2. In-place: Doesn't modify tree structure permanently
3. Iterative: Avoids stack overflow for deep trees

DISADVANTAGES:
1. Temporarily modifies tree structure (though restores it)
2. More complex logic compared to recursive approaches
3. Only works for inorder and preorder traversals easily

WHEN TO USE MORRIS TRAVERSAL:
- Memory-constrained environments
- Very deep trees where stack overflow is a concern
- When you need to traverse large trees with minimal memory footprint
- Embedded systems with limited stack space
- When processing trees with millions of nodes
*/

public class Traversals05 {

  // ================== MORRIS INORDER TRAVERSAL ==================== //
  /*
   * ALGORITHM:
   * 1. Initialize current = root
   * 2. While current is not null:
   * a) If current has no left child:
   * - Process current node
   * - Move to right child
   * b) If current has left child:
   * - Find inorder predecessor (rightmost node in left subtree)
   * - If predecessor's right is null:
   * Make current as right child of predecessor
   * Move to left child of current
   * - If predecessor's right is current:
   * Restore the tree (remove the link)
   * Process current node
   * Move to right child of current
   */

  public static List<Integer> morrisInorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    TreeNode current = root;
    while (current != null) {
      if (current.left == null) {
        // No left subtree, process current and go right
        result.add(current.val);
        current = current.right;
      } else {
        // Find inorder predecessor
        TreeNode predecessor = current.left;
        while (predecessor.right != null && predecessor.right != current) {
          predecessor = predecessor.right;
        }

        if (predecessor.right == null) {
          // Make current as right child of predecessor
          predecessor.right = current;
          current = current.left;
        } else {
          // Restore the tree and process current
          predecessor.right = null;
          result.add(current.val);
          current = current.right;
        }
      }
    }

    return result;
  }

  // ================== MORRIS PREORDER TRAVERSAL ==================== //
  /*
   * Similar to inorder, but we process the node when we first encounter it
   * (when creating the thread), not when we return to it.
   */
  public static List<Integer> morrisPreorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    TreeNode current = root;

    while (current != null) {
      if (current.left == null) {
        // No left subtree, process current and go right
        result.add(current.val);
        current = current.right;
      } else {
        // Find inorder predecessor
        TreeNode predecessor = current.left;
        while (predecessor.right != null && predecessor.right != current) {
          predecessor = predecessor.right;
        }

        if (predecessor.right == null) {
          // Make current as right child of predecessor
          // Process current node HERE (difference from inorder)
          result.add(current.val);
          predecessor.right = current;
          current = current.left;
        } else {
          // Restore the tree
          predecessor.right = null;
          current = current.right;
        }
      }
    }

    return result;
  }

  // ================== COMPARISON METHODS ==================== //

  // Normal recursive inorder (for comparison)
  public static List<Integer> normalInorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    inorderHelper(root, result);
    return result;
  }

  private static void inorderHelper(TreeNode node, List<Integer> result) {
    if (node == null)
      return;
    inorderHelper(node.left, result);
    result.add(node.val);
    inorderHelper(node.right, result);
  }

  // Normal recursive preorder (for comparison)
  public static List<Integer> normalPreorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    preorderHelper(root, result);
    return result;
  }

  private static void preorderHelper(TreeNode node, List<Integer> result) {
    if (node == null)
      return;
    result.add(node.val);
    preorderHelper(node.left, result);
    preorderHelper(node.right, result);
  }

  // ================== PRACTICAL APPLICATIONS ==================== //

  /*
   * APPLICATION 1: Validate BST using Morris Traversal
   * Use Morris inorder to check if tree is a valid BST with O(1) space
   */
  public static boolean isValidBSTMorris(TreeNode root) {
    TreeNode current = root;
    Integer prev = null;

    while (current != null) {
      if (current.left == null) {
        // Process current node
        if (prev != null && prev >= current.val) {
          return false;
        }
        prev = current.val;
        current = current.right;
      } else {
        // Find predecessor
        TreeNode predecessor = current.left;
        while (predecessor.right != null && predecessor.right != current) {
          predecessor = predecessor.right;
        }

        if (predecessor.right == null) {
          predecessor.right = current;
          current = current.left;
        } else {
          predecessor.right = null;
          // Process current node
          if (prev != null && prev >= current.val) {
            return false;
          }
          prev = current.val;
          current = current.right;
        }
      }
    }

    return true;
  }

  /*
   * APPLICATION 2: Find Kth Smallest Element in BST using Morris Traversal
   * Use Morris inorder to find kth smallest with O(1) space
   */
  public static int kthSmallestMorris(TreeNode root, int k) {
    TreeNode current = root;
    int count = 0;

    while (current != null) {
      if (current.left == null) {
        // Process current node
        count++;
        if (count == k) {
          return current.val;
        }
        current = current.right;
      } else {
        // Find predecessor
        TreeNode predecessor = current.left;
        while (predecessor.right != null && predecessor.right != current) {
          predecessor = predecessor.right;
        }

        if (predecessor.right == null) {
          predecessor.right = current;
          current = current.left;
        } else {
          predecessor.right = null;
          // Process current node
          count++;
          if (count == k) {
            return current.val;
          }
          current = current.right;
        }
      }
    }

    return -1; // k is larger than number of nodes
  }

  /*
   * APPLICATION 3: Convert BST to Greater Tree using Morris Traversal
   * Use reverse Morris inorder (right-root-left) to convert BST to greater tree
   */
  public static TreeNode convertBSTMorris(TreeNode root) {
    TreeNode current = root;
    int sum = 0;

    while (current != null) {
      if (current.right == null) {
        // Process current node
        sum += current.val;
        current.val = sum;
        current = current.left;
      } else {
        // Find successor (leftmost in right subtree)
        TreeNode successor = current.right;
        while (successor.left != null && successor.left != current) {
          successor = successor.left;
        }

        if (successor.left == null) {
          successor.left = current;
          current = current.right;
        } else {
          successor.left = null;
          // Process current node
          sum += current.val;
          current.val = sum;
          current = current.left;
        }
      }
    }

    return root;
  }

  // ================== UTILITY METHODS ==================== //

  public static void printTree(TreeNode root) {
    if (root == null) {
      System.out.println("Empty tree");
      return;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        if (node != null) {
          System.out.print(node.val + " ");
          queue.offer(node.left);
          queue.offer(node.right);
        } else {
          System.out.print("null ");
        }
      }
      System.out.println();
    }
  }

  // ================== EXAMPLE USAGE AND TESTING ==================== //

  public static void main(String[] args) {
    // Create test tree: 4
    // / \
    // 2 6
    // / \ / \
    // 1 3 5 7

    TreeNode root = new TreeNode(4);
    root.left = new TreeNode(2);
    root.right = new TreeNode(6);
    root.left.left = new TreeNode(1);
    root.left.right = new TreeNode(3);
    root.right.left = new TreeNode(5);
    root.right.right = new TreeNode(7);

    System.out.println("=== MORRIS TRAVERSAL DEMONSTRATIONS ===");
    System.out.println("Tree structure:");
    printTree(root);

    // Test Morris Inorder
    System.out.println("\n--- Inorder Traversal Comparison ---");
    List<Integer> morrisInorder = morrisInorderTraversal(root);
    List<Integer> normalInorder = normalInorderTraversal(root);
    System.out.println("Morris Inorder:  " + morrisInorder);
    System.out.println("Normal Inorder:  " + normalInorder);
    System.out.println("Results match: " + morrisInorder.equals(normalInorder));

    // Test Morris Preorder
    System.out.println("\n--- Preorder Traversal Comparison ---");
    List<Integer> morrisPreorder = morrisPreorderTraversal(root);
    List<Integer> normalPreorder = normalPreorderTraversal(root);
    System.out.println("Morris Preorder: " + morrisPreorder);
    System.out.println("Normal Preorder: " + normalPreorder);
    System.out.println("Results match: " + morrisPreorder.equals(normalPreorder));

    // Test BST Validation
    System.out.println("\n--- BST Validation using Morris ---");
    boolean isValid = isValidBSTMorris(root);
    System.out.println("Is valid BST: " + isValid);

    // Test Kth Smallest
    System.out.println("\n--- Kth Smallest Element using Morris ---");
    for (int k = 1; k <= 4; k++) {
      int kthSmallest = kthSmallestMorris(root, k);
      System.out.println(k + "th smallest element: " + kthSmallest);
    }

    // Test Convert BST to Greater Tree
    System.out.println("\n--- Convert BST to Greater Tree using Morris ---");
    TreeNode originalRoot = new TreeNode(4);
    originalRoot.left = new TreeNode(2);
    originalRoot.right = new TreeNode(6);
    originalRoot.left.left = new TreeNode(1);
    originalRoot.left.right = new TreeNode(3);
    originalRoot.right.left = new TreeNode(5);
    originalRoot.right.right = new TreeNode(7);

    System.out.println("Before conversion:");
    System.out.println("Inorder: " + morrisInorderTraversal(originalRoot));

    convertBSTMorris(originalRoot);
    System.out.println("After conversion to greater tree:");
    System.out.println("Structure: " + morrisInorderTraversal(originalRoot));

    // Demonstrate space efficiency
    System.out.println("\n--- Space Complexity Demonstration ---");
    System.out.println("Morris Traversal: O(1) space complexity");
    System.out.println("Normal Traversal: O(h) space complexity where h is height");
    System.out.println("For a tree with 1 million nodes:");
    System.out.println("- Morris: Uses constant extra space");
    System.out.println("- Normal: Could use up to 1 million stack frames in worst case");

    // Performance comparison for large trees
    System.out.println("\n--- When to Use Morris Traversal ---");
    System.out.println("✓ Memory-constrained environments");
    System.out.println("✓ Very deep trees (avoid stack overflow)");
    System.out.println("✓ Large trees with minimal memory footprint");
    System.out.println("✓ Embedded systems with limited stack space");
    System.out.println("✓ Real-time systems where memory usage is critical");
    System.out.println("✗ Simple applications where readability matters more");
    System.out.println("✗ When tree structure needs to remain completely unchanged");
  }

  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
      this.val = val;
      this.left = null;
      this.right = null;
    }
  }
}