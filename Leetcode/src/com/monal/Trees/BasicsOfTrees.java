package com.monal.Trees;
import java.util.*;

/**
 * COMPREHENSIVE TREE BASICS - Everything you need to ace your tree tests!
 *
 * <p>
 * Think of trees as - - Connected graphs with NO CYCLES - Exactly N-1 edges for
 * N nodes -
 * Hierarchical structure (parent-child relationships) - Used in many
 * applications like file
 * systems, organization charts, etc.
 *
 * <p>
 * This file covers: 1. Tree Node Structure & Basic Operations 2. Tree
 * Traversals (DFS variants +
 * BFS) 3. Tree Properties & Measurements 4. Binary Tree Specific Algorithms 5.
 * Tree Construction &
 * Manipulation 6. Advanced Tree Concepts
 */
public class BasicsOfTrees {

  // ======================== TREE NODE DEFINITIONS ========================

  /**
   * Basic Binary Tree Node - Most common in interviews/tests Each node has at
   * most 2 children (left
   * and right)
   */
  static class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
      this.val = val;
      this.left = this.right = null;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }

  /**
   * N-ary Tree Node - For trees where each node can have multiple children Think
   * of file systems,
   * organization charts, etc.
   */
  static class NTreeNode {
    int val;
    List<NTreeNode> children;

    NTreeNode(int val) {
      this.val = val;
      this.children = new ArrayList<>();
    }
  }

  // ======================== TREE TRAVERSALS ========================

  /*
   * TREE TRAVERSALS: The key to understanding trees! Think of traversals as ways
   * to "visit" every
   * node in the tree in a specific order.
   *
   * Common traversal types: -
   * - Preorder (DFS)
   * - Inorder (DFS)
   * - Postorder (DFS)
   * - Level Order (BFS)
   */

  /*
   * PREORDER TRAVERSAL:
   * Root -> Left -> Right
   * Use case: Creating a copy of tree, expression trees evaluation
   * Think: "Process current node BEFORE its children"
   */
  public static List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    preorderHelper(root, result);
    return result;
  }

  private static void preorderHelper(TreeNode node, List<Integer> result) {
    if (node == null)
      return;

    result.add(node.val);
    // process left
    preorderHelper(node.left, result);
    // process right
    preorderHelper(node.right, result);
    // Note: No need to return anything, we are collecting results in the list
  }

  /*
   * INORDER TRAVERSAL: Left -> Root -> Right
   * Use case: BST gives sorted order, expression trees give infix notation
   * Think: "Process current node BETWEEN its children"
   */
  public static void inorderTraversal(TreeNode root) {
    if (root == null)
      return;

    inorderTraversal(root.left); // First left subtree
    System.out.print(root.val + " "); // Then current node
    inorderTraversal(root.right); // Then right subtree
  }

  /**
   * POSTORDER TRAVERSAL: Left -> Right -> Root Use case: Deleting tree,
   * calculating size/height,
   * expression evaluation Think: "Process current node AFTER its children"
   */
  public static void postorderTraversal(TreeNode root) {
    if (root == null)
      return;
    postorderTraversal(root.left); // First left subtree
    postorderTraversal(root.right); // Then right subtree
    System.out.print(root.val + " "); // Finally current no```
  }

  /**
   * LEVEL ORDER TRAVERSAL (BFS): Level by level, left to right Use case: Printing
   * tree level by
   * level, finding shortest path in unweighted tree This is your BFS knowledge
   * applied to trees!
   */
  public static List<List<Integer>> levelOrderTraversal(TreeNode root) {
    if (root == null)
      return new ArrayList<>();
    List<List<Integer>> result = new ArrayList<>();
    Queue<TreeNode> q = new ArrayDeque<>();
    q.offer(root);

    while (!q.isEmpty()) {
      int levelSize = q.size();
      List<Integer> currentLevel = new ArrayList<>();
      for (int i = 0; i < levelSize; i++) {
        TreeNode node = q.poll();
        currentLevel.add(node.val);
        if (node.left != null)
          q.offer(node.left);
        if (node.right != null)
          q.offer(node.right);
      }
      result.add(currentLevel);
    }
    return result;
  }

  public static class AllTraversals {
    List<Integer> preorder;
    List<Integer> inorder;
    List<Integer> postorder;
  }

  public static AllTraversals allTraversalsInOneTraversal(TreeNode root) {
    AllTraversals traversals = new AllTraversals();
    traversals.preorder = new ArrayList<>();
    traversals.inorder = new ArrayList<>();
    traversals.postorder = new ArrayList<>();
    allTraversalsHelper(root, traversals);
    return traversals;
  }

  private static void allTraversalsHelper(TreeNode node, AllTraversals traversals) {
    if (node == null)
      return;

    // Preorder - process current node first
    traversals.preorder.add(node.val);

    // Recursively process left subtree
    allTraversalsHelper(node.left, traversals);

    // Inorder - process current node after left subtree
    traversals.inorder.add(node.val);

    // Recursively process right subtree
    allTraversalsHelper(node.right, traversals);

    // Postorder - process current node after both subtrees
    traversals.postorder.add(node.val);
  }

  /*
   * LEVEL ORDER with LEVEL SEPARATION - (Uses logic similar to BFS)
   * Returns list of lists, each inner list represents one level
   */
  public static List<List<Integer>> levelOrderWithLevels(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null)
      return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      int levelSize = queue.size(); // KEY: Current level's node count
      List<Integer> currentLevel = new ArrayList<>();

      // Process all nodes at current level
      for (int i = 0; i < levelSize; i++) {
        TreeNode node = queue.poll();
        currentLevel.add(node.val);

        if (node.left != null)
          queue.offer(node.left);
        if (node.right != null)
          queue.offer(node.right);
      }
      result.add(currentLevel);
    }
    return result;
  }

  // ================== TREE PROPERTIES & MEASUREMENTS ================== //
  /*
   * This Section Includes:
   * - Height of tree
   * - Depth of a node
   * - Diameter of tree
   * - Size of tree
   * - Balance check
   * - Maximum path sum
   */
  /*
   * HEIGHT of tree: Maximum depth from root to any leaf
   * Height of empty tree = -1, Height of single node = 0
   */
  public static int height(TreeNode root) {
    if (root == null)
      return -1;
    // +1 since we count the root node itself
    return 1 + Math.max(height(root.left), height(root.right));
  }

  /*
   * DEPTH of a node: Distance from root to that node.
   * Root has depth 0
   */
  public static int depthOfNode(TreeNode root, int target) {
    if (root == null)
      return -1;
    if (root.val == target)
      return 0; // Found the target node

    return depthOfNodeHelper(root, target, 0);
  }

  public static int depthOfNodeHelper(TreeNode root, int target, int currentDepth) {
    if (root == null)
      return -1; // Node not found
    if (root.val == target)
      return currentDepth;

    // Search in left subtree
    int leftDepth = depthOfNodeHelper(root.left, target, currentDepth + 1);
    int rightDepth = depthOfNodeHelper(root.right, target, currentDepth + 1);

    // If found in left subtree, return its depth
    if (leftDepth != -1)
      return leftDepth;
    // If found in right subtree, return its depth
    if (rightDepth != -1)
      return rightDepth;
    // If not found in either subtree, return -1
    return -1;

  }

  /** SIZE of tree: Total number of nodes */
  public static int size(TreeNode root) {
    if (root == null)
      return 0;
    return 1 + size(root.left) + size(root.right);
  }

  /*
   * Check if tree is Balnced or not
   * Balanced: For every node, height differnece between left and right is 0 or 1
   *
   * Approach: Recursively check balance of subtrees by:
   * - Calculating height of left and right subtrees
   * - Checking if height difference is withinh bounds
   * * Time Complexity: O(n) where n is number of nodes
   * * Space Complexity: O(h) where h is height of tree (due to recursion stack)
   */

  public static boolean isBalanced(TreeNode root) {
    if (root == null)
      return true; // An empty tree is balanced
    return checkIfBalanced(root) != -1;
  }

  private static int checkIfBalanced(TreeNode node) {
    if (node == null)
      return 0;

    int leftHeight = checkIfBalanced(node.left);
    if (leftHeight == -1)
      return -1; // Left subtree is unbalanced

    int rightHeight = checkIfBalanced(node.right);
    if (rightHeight == -1)
      return -1; // Right subtree is unbalanced

    // Check if current node is balanced
    if (Math.abs(leftHeight - rightHeight) > 1)
      return -1;

    return Math.max(leftHeight, rightHeight) + 1;
  }

  /*
   * DIAMETER of tree:
   * Longest path between any two nodes
   * Path may or may not pass through root
   */

  class DiameterOfBinaryTree {
    private int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
      dfs(root);
      return diameter;
    }

    private int dfs(TreeNode node) {
      if (node == null)
        return 0;
      int leftHeight = dfs(node.left);
      int rightHeight = dfs(node.right);
      diameter = Math.max(diameter, leftHeight + rightHeight);
      return 1 + Math.max(leftHeight, rightHeight);
    }
  }

  /**
   * Returns the diameter of the binary tree (longest path between any two nodes)
   */
  public static int diameter(TreeNode root) {
    BasicsOfTrees outer = new BasicsOfTrees();
    DiameterOfBinaryTree diameterCalculator = outer.new DiameterOfBinaryTree();
    return diameterCalculator.diameterOfBinaryTree(root);
  }

  class MaximumPathSum {
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
      calculateMaxPath(root);
      return maxSum;
    }

    private int calculateMaxPath(TreeNode node) {
      if (node == null)
        return 0;
      // Calcuate max Path sum for left and right subtrees
      // Ignore negative paths
      int leftMax = Math.max(0, calculateMaxPath(node.left));
      int rightMax = Math.max(0, calculateMaxPath(node.right));

      // Update global max sum
      // Current node can be part of the path
      maxSum = Math.max(maxSum, node.val + leftMax + rightMax);
      // Return max path sum including current node
      // This is the maximum path sum that can be extended to parent
      return node.val + Math.max(leftMax, rightMax);
    }
  }

  // ======================== BINARY TREE VALIDATION ========================

  /**
   * Check if tree is a BINARY SEARCH TREE BST Property: For each node, all left
   * descendants < node
   * < all right descendants
   */
  public static boolean isValidBST(TreeNode root) {
    return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
  }

  private static boolean isValidBST(TreeNode node, long min, long max) {
    if (node == null)
      return true;

    // Current node must be within bounds
    if (node.val <= min || node.val >= max)
      return false;

    // Recursively validate left and right subtrees with updated bounds
    return isValidBST(node.left, min, node.val) && isValidBST(node.right, node.val, max);
  }

  /*
   * Check if tree is COMPLETE
   * Complete: All levels filled except possibly the
   * last, and last fiilled from left
   */
  public static boolean isComplete(TreeNode root) {
    if (root == null)
      return true;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    boolean foundNull = false;

    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();

      if (node == null) {
        foundNull = true;
      } else {
        if (foundNull)
          return false; // Found non-null after null
        queue.offer(node.left);
        queue.offer(node.right);
      }
    }

    return true;
  }

  /*
   * Are Tree SImilar??
   * Two trees are similar if they have the same structure and node values
   * Approach: Recursively check if both trees are null or have same value and
   * structure
   */
  public boolean areTreesSimilar(TreeNode r1, TreeNode r2) {
    if (r1 == null && r2 == null) {
      return true; // Both trees are empty
    }
    if (r1 == null || r2 == null) {
      return false; // One tree is empty, other is not
    }
    // Check current node values and recursively check left and right subtrees
    return (r1.val == r2.val)
        && areTreesSimilar(r1.left, r2.left)
        && areTreesSimilar(r1.right, r2.right);
  }
  // =================== Views of Trees ========================= //
  /*
   * This Section consists of the algos which gives us the view from
   * different angles of a tree
   *
   * 1. Top View of The Tree
   * 2. Left/Right view of The Tree
   * 3. Bottom view of the Tree
   */

  public class Views {
    public class Pair {
      TreeNode node;
      int hd; // horizontal distance

      Pair(TreeNode node, int hd) {
        this.node = node;
        this.hd = hd;
      }
    }

    // ---- Right Side View of the Tree ---- //
    public List<Integer> rightSideView(TreeNode root) {
      List<Integer> rightView = new ArrayList<>();
      List<Integer> leftView = new ArrayList<>();
      if (root == null)
        return rightView;

      Queue<TreeNode> q = new ArrayDeque<>();
      q.offer(root);

      while (!q.isEmpty()) {
        int size = q.size();
        for (int i = 0; i < size; i++) {
          TreeNode curr = q.poll();
          if (i == 0)
            leftView.add(curr.val); // First element of each level is left view
          if (i == size - 1)
            rightView.add(curr.val); // Last element of each level is right view
          if (curr.left != null)
            q.offer(curr.left);
          if (curr.right != null)
            q.offer(curr.right);
        }
      }
      return rightView;
    }

    // --------- Top View of the Tree --------- //
    public List<Integer> topView(TreeNode root) {
      List<Integer> result = new ArrayList<>();
      if (root == null)
        return result;
      Map<Integer, Integer> map = new HashMap<>(); // for hd -> node.val
      Queue<Pair> q = new ArrayDeque<>();
      q.offer(new Pair(root, 0));

      while (!q.isEmpty()) {
        Pair curr = q.poll();
        int horizontalDist = curr.hd;
        TreeNode node = curr.node;

        if (!map.containsKey(horizontalDist)) {
          map.put(horizontalDist, node.val);
        }

        if (node.left != null) {
          q.offer(new Pair(node.left, horizontalDist - 1));
        }
        if (node.right != null) {
          q.offer(new Pair(node.right, horizontalDist + 1));
        }
      }
      // Extract the node from the map
      for (int value : map.values()) {
        result.add(value);
      }
      return result;
    }

    // ----- Bottom View of the Tree ----- //
    public List<Integer> bottomView(TreeNode root) {

      List<Integer> result = new ArrayList<>();
      if (root == null)
        return result;

      Map<Integer, Integer> map = new TreeMap<>();
      Queue<Pair> q = new ArrayDeque<>();
      q.offer(new Pair(root, 0));

      while (!q.isEmpty()) {
        Pair curr = q.poll();
        TreeNode node = curr.node;
        int hd = curr.hd;
        // Main logic : always update for the current hd
        map.put(hd, node.val);

        if (node.left != null)
          q.offer(new Pair(node.left, hd - 1));
        if (node.right != null)
          q.offer(new Pair(node.right, hd + 1));
      }

      result.addAll(map.values());
      return result;
    }

  }

  // ======================== TREE CONSTRUCTION ========================

  /**
   * Build tree from PREORDER and INORDER traversals
   */
  public static TreeNode buildTreeFromPreorderInorder(int[] preorder, int[] inorder) {
    Map<Integer, Integer> inorderMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
      inorderMap.put(inorder[i], i);
    }

    return buildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, inorderMap);
  }

  private static TreeNode buildTree(
      int[] preorder,
      int preStart,
      int preEnd,
      int[] inorder,
      int inStart,
      int inEnd,
      Map<Integer, Integer> inorderMap) {

    if (preStart > preEnd || inStart > inEnd)
      return null;

    // First element in preorder is always the root
    TreeNode root = new TreeNode(preorder[preStart]);

    // Find root position in inorder
    int rootIndex = inorderMap.get(preorder[preStart]);
    int leftSize = rootIndex - inStart;

    // Recursively build left and right subtrees
    root.left = buildTree(
        preorder, preStart + 1, preStart + leftSize,
        inorder, inStart, rootIndex - 1, inorderMap);
    root.right = buildTree(
        preorder, preStart + leftSize + 1, preEnd,
        inorder, rootIndex + 1, inEnd, inorderMap);

    return root;
  }

  // ======================== TREE PATHS & SEARCHES ========================

  // Find ALL PATHS from root to leaves
  public static List<List<Integer>> rootToLeafPaths(TreeNode root) {
    List<List<Integer>> paths = new ArrayList<>();
    if (root == null)
      return paths;

    findPaths(root, new ArrayList<>(), paths);
    return paths;
  }

  private static void findPaths(TreeNode node, List<Integer> currentPath,
      List<List<Integer>> paths) {

    if (node == null)
      return;
    currentPath.add(node.val);

    // If leaf node, add current path to the result
    if (node.left == null && node.right == null) {
      paths.add(new ArrayList<>(currentPath));
    } else {
      findPaths(node.left, currentPath, paths);
      findPaths(node.right, currentPath, paths);
    }
    // Backtract to explore other paths
    // Remove last added node
    currentPath.remove(currentPath.size() - 1);
  }

  /** PATH SUM: Check if there exists a path from root to leaf with given sum */
  public static boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null)
      return false;
    return hasPathSumHelper(root, targetSum);
  }

  private static boolean hasPathSumHelper(TreeNode node, int remaining) {
    if (node == null)
      return false;
    remaining = remaining - node.val;
    // If we reach a leaf node, check if remaining sum is zero
    if (node.left == null && node.right == null) {
      return remaining == 0;
    }
    // Recur for left and right subtrees
    return hasPathSumHelper(node.left, remaining) || hasPathSumHelper(node.right, remaining);
  }

  /**
   * LOWEST COMMON ANCESTOR (LCA) in Binary Tree Very important concept - appears
   * in many variations
   *
   * LCA of two nodes q and p is defined as teh lowest level node that is an
   * ancestor of both q and p
   *
   * for eg in a Tree like :
   * 0
   * / \
   * 1 2
   * / \ / \
   * 3 4 5 6
   */
  public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q)
      return root;

    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);

    // If both left and right are non-null, current node is LCA
    if (left != null && right != null)
      return root;

    // Otherwise, return the non-null one
    return left != null ? left : right;
  }

  // ======================== TREE TRANSFORMATIONS ========================

  /** INVERT/MIRROR a binary tree Swap left and right children of every node */
  public static TreeNode invertTree(TreeNode root) {
    if (root == null)
      return null;

    // Swap left and right children
    TreeNode temp = root.left;
    root.left = root.right;
    root.right = temp;

    // Recursively invert subtrees
    invertTree(root.left);
    invertTree(root.right);

    return root;
  }

  /**
   * FLATTEN tree to linked list (right-skewed tree) Follow preorder traversal
   * order
   */
  public static void flatten(TreeNode root) {
    if (root == null)
      return;

    flatten(root.left);
    flatten(root.right);

    TreeNode rightSubtree = root.right;
    root.right = root.left;
    root.left = null;

    // Find the rightmost node in the new right subtree
    TreeNode current = root;
    while (current.right != null) {
      current = current.right;
    }

    current.right = rightSubtree;
  }

  // ======================== ADVANCED TREE CONCEPTS ========================

  /**
   * SERIALIZE and DESERIALIZE tree Convert tree to string and back - very common
   * in tests
   */
  public static String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    serializeHelper(root, sb);
    return sb.toString();
  }

  private static void serializeHelper(TreeNode node, StringBuilder sb) {
    if (node == null) {
      sb.append("null,");
      return;
    }

    sb.append(node.val).append(",");
    serializeHelper(node.left, sb);
    serializeHelper(node.right, sb);
  }

  public static TreeNode deserialize(String data) {
    String[] nodes = data.split(",");
    Queue<String> queue = new LinkedList<>(Arrays.asList(nodes));
    return deserializeHelper(queue);
  }

  private static TreeNode deserializeHelper(Queue<String> queue) {
    String val = queue.poll();
    if (val.equals("null"))
      return null;

    TreeNode node = new TreeNode(Integer.parseInt(val));
    node.left = deserializeHelper(queue);
    node.right = deserializeHelper(queue);

    return node;
  }

  /**
   * TREE ITERATORS - Traverse tree using iterator pattern This shows how to
   * convert recursive
   * traversal to iterative
   */
  static class InorderIterator {
    private Stack<TreeNode> stack;

    public InorderIterator(TreeNode root) {
      stack = new Stack<>();
      pushLeft(root);
    }

    public boolean hasNext() {
      return !stack.isEmpty();
    }

    public int next() {
      TreeNode node = stack.pop();
      pushLeft(node.right);
      return node.val;
    }

    private void pushLeft(TreeNode node) {
      while (node != null) {
        stack.push(node);
        node = node.left;
      }
    }
  }

  // ======================== UTILITY METHODS ========================

  /** Create a sample tree for testing 1 / \ 2 3 / \ 4 5 */
  public static TreeNode createSampleTree() {
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.left.right = new TreeNode(5);
    return root;
  }

  /** Print tree in a visual format (sideways) */
  public static void printTree(TreeNode root) {
    printTree(root, "", true);
  }

  private static void printTree(TreeNode node, String prefix, boolean isLast) {
    if (node == null)
      return;

    System.out.println(prefix + (isLast ? "└── " : "├── ") + node.val);

    if (node.left != null || node.right != null) {
      if (node.right != null) {
        printTree(node.right, prefix + (isLast ? "    " : "│   "), node.left == null);
      }
      if (node.left != null) {
        printTree(node.left, prefix + (isLast ? "    " : "│   "), true);
      }
    }
  }

  // ======================== MAIN METHOD - TEST YOUR UNDERSTANDING
  // ========================

  public static void main(String[] args) {
    System.out.println("=== TREE BASICS DEMONSTRATION ===\n");

    // Create sample tree
    TreeNode root = createSampleTree();

    System.out.println("Sample Tree Structure:");
    printTree(root);

    System.out.println("\n=== TRAVERSALS ===");
    System.out.print("Preorder:  ");
    preorderTraversal(root);
    System.out.print("\nInorder:   ");
    inorderTraversal(root);
    System.out.print("\nPostorder: ");
    postorderTraversal(root);
    System.out.print("\nLevel Order: ");
    levelOrderTraversal(root);

    System.out.println("\n\n=== TREE PROPERTIES ===");
    System.out.println("Height: " + height(root));
    System.out.println("Size: " + size(root));
    System.out.println("Diameter: " + diameter(root));
    System.out.println("Is Balanced: " + isBalanced(root));
    System.out.println("Is Complete: " + isComplete(root));

    System.out.println("\n=== PATH OPERATIONS ===");
    System.out.println("All Root-to-Leaf Paths: " + rootToLeafPaths(root));
    System.out.println("Has Path Sum 7: " + hasPathSum(root, 7));
    System.out.println("Has Path Sum 10: " + hasPathSum(root, 10));

    System.out.println("\n=== SERIALIZATION ===");
    String serialized = serialize(root);
    System.out.println("Serialized: " + serialized);
    TreeNode deserialized = deserialize(serialized);
    System.out.println("Deserialized and re-serialized: " + serialize(deserialized));

    System.out.println("\nTree basics covered! Practice these concepts to ace your tests! 🌳");
  }
}

/*
 * KEY CONCEPTS TO REMEMBER FOR TESTS:
 *
 * 1. TREE vs GRAPH:
 * - Tree: Connected, Acyclic, N-1 edges for N nodes
 * - Graph: Can have cycles, multiple components
 *
 * 2. TRAVERSAL PATTERNS:
 * - Preorder: Root first (good for copying)
 * - Inorder: Root middle (gives sorted order in BST)
 * - Postorder: Root last (good for deletion)
 * - Level Order: BFS (good for level-by-level processing)
 *
 * 3. RECURSIVE THINKING:
 * - Most tree problems can be solved recursively
 * - Base case: null node
 * - Recursive case: solve for subtrees, combine results
 *
 * 4. COMMON PATTERNS:
 * - Tree construction from traversals
 * - Path finding problems
 * - Tree validation problems
 * - Tree transformation problems
 *
 * 5. TIME COMPLEXITIES:
 * - Most operations: O(n) where n is number of nodes
 * - Space complexity: O(h) where h is height (due to recursion stack)
 *
 * 6. EDGE CASES TO CONSIDER:
 * - Empty tree (root = null)
 * - Single node tree
 * - Skewed tree (all nodes on one side)
 * - Complete vs incomplete trees
 */
