package com.monal.BST;

import java.util.*;

/**
 * BINARY SEARCH TREE
 *
 * TOPICS COVERED:
 * 1. Basic BST Operations (Insert, Delete, Search)
 * 2. Tree Traversals (Inorder, Preorder, Postorder, Level Order)
 * 3. Tree Properties (Height, Size, Balance Check)
 * 4. Advanced Operations (LCA, Path Sum, Diameter)
 * 5. BST Validation and Construction
 * 6. Range Queries and Modifications
 * 7. BST to other data structures conversion
 * 8. Advanced BST Problems (Kth Smallest, Successor/Predecessor)
 */

/*
 * IMP points to remember for BST problems:
 *
 * 1. INORDER TRAVERSAL GIVES SORTED ORDER
 * - Use this for validation, kth element, range queries
 * - Template: inorder traversal with early termination
 *
 * 2. RECURSIVE WITH BOUNDS
 * - Validation, construction from traversals
 * - Template: helper(node, minVal, maxVal)
 *
 * 3. LCA PATTERN
 * - Distance between nodes, path problems
 * - Template: Use BST property to navigate
 *
 * 4. TWO POINTER / ITERATOR PATTERN
 * - Two sum in BST, merge operations
 * - Template: Controlled inorder traversal
 *
 * 5. RANGE QUERY PATTERN
 * - Sum/count in range, trimming
 * - Template: Prune branches outside range
 *
 * COMPLEXITY ANALYSIS TIPS:
 * - Always mention both average and worst case
 * - Height h = log2n for balanced, n for skewed
 * - Space complexity includes recursion stack
 *
 * To keep in mind:
 * - Always clarify duplicates handling
 * - Ask about tree balance guarantees
 * - Consider iterative solutions for space optimization
 * - Draw small examples to verify logic
 * - Handle edge cases: null root, single node, empty tree
 * - Morris traversal for O(1) space inorder
 * - Iterative solutions to avoid stack overflow
 * - Early termination when possible
 * - Use BST property to prune search space
 */
public class BST {

  private TreeNode root;

  // =============================================================================
  // SECTION 0: BASICS
  // =============================================================================
  /*
   * A BST is a binary tree where:
   * - Left subtree contains nodes with values less than the root
   * - Right subtree contains nodes with values greater than the root
   * - No duplicate values (or handled based on problem requirements)
   * - Every node follows the BST property recursively
   */

  // =============================================================================
  // SECTION 1: BASIC BST OPERATIONS
  // =============================================================================

  /**
   * INSERT OPERATION
   * Time: O(log n) average, O(n) worst case (skewed tree)
   * Space: O(log n) average, O(n) worst case (recursion stack for skewed tree)
   *
   * KEY POINTS FOR INTERVIEWS:
   * - BST property: left < root < right
   * - Duplicate handling varies by problem (here we ignore duplicates)
   * - Can be implemented iteratively to save space
   */
  public void insert(int val) {
    root = insertRecursive(root, val);
  }

  private TreeNode insertRecursive(TreeNode node, int val) {
    // Base case: found the position to insert
    if (node == null)
      return new TreeNode(val);

    if (val < node.val)
      node.left = insertRecursive(node.left, val);
    else if (val > node.val)
      node.right = insertRecursive(node.right, val);
    // If val == node.val, we ignore duplicates
    return node;
  }

  /**
   * ITERATIVE INSERT - More space efficient
   * Time: O(log n) average, O(n) worst case
   * Space: O(1)
   */
  public TreeNode insertIterative(int val, TreeNode root) {
    if (root == null)
      return new TreeNode(val);

    TreeNode current = root;
    TreeNode parent = null;

    while (current != null) {
      parent = current;
      if (val < current.val) {
        current = current.left;
      } else if (val > current.val) {
        current = current.right;
      } else {
        // If val == current.val, we ignore duplicates
        return root; // No insertion needed
      }
    }

    // Insert as left or right child of parent
    TreeNode newNode = new TreeNode(val);
    if (val < parent.val) {
      parent.left = newNode;
    } else {
      parent.right = newNode;
    }
    return root; // Return unchanged root
  }

  /**
   * SEARCH OPERATION
   * Time: O(log n) average, O(n) worst case
   * Space: O(log n) recursive, O(1) iterative
   *
   * INTERVIEW TIP: Always clarify if you should return the node or just boolean
   */
  public boolean search(int val) {
    return searchRecursive(root, val);
  }

  private boolean searchRecursive(TreeNode node, int val) {
    if (node == null)
      return false;
    if (node.val == val)
      return true;

    return val < node.val ? searchRecursive(node.left, val) : searchRecursive(node.right, val);
  }

  /**
   * ITERATIVE SEARCH - More space efficient
   */
  public boolean searchIterative(int val) {
    TreeNode current = root;
    while (current != null) {
      if (current.val == val)
        return true;
      current = val < current.val ? current.left : current.right;
    }
    return false;
  }

  /**
   * DELETE OPERATION - The most complex BST operation
   * Time: O(log n) average, O(n) worst case
   * Space: O(log n) recursion stack
   *
   * THREE CASES TO HANDLE:
   * 1. Node has no children (leaf) - simply remove
   * 2. Node has one child - replace with child
   * 3. Node has two children - replace with inorder successor/predecessor
   *
   * INTERVIEW TIP: This is a favorite question. Practice all three cases!
   */
  public void delete(int val) {
    root = deleteRecursive(root, val);
  }

  private TreeNode deleteRecursive(TreeNode node, int val) {
    // Base case: value not found
    if (node == null)
      return null;

    // Navigate to the node to delete
    if (val < node.val) {
      node.left = deleteRecursive(node.left, val);
    } else if (val > node.val) {
      node.right = deleteRecursive(node.right, val);
    } else {
      // Found the node to delete - handle three cases

      // Case 1: Node has no children (leaf node)
      if (node.left == null && node.right == null) {
        return null;
      }

      // Case 2: Node has one child
      if (node.left == null)
        return node.right;
      if (node.right == null)
        return node.left;

      // Case 3: Node has two children
      // Find inorder successor (smallest in right subtree)
      TreeNode successor = findMin(node.right);

      // Replace node's value with successor's value
      node.val = successor.val;

      // Delete the successor (which has at most one right child)
      node.right = deleteRecursive(node.right, successor.val);
    }

    return node;
  }

  /**
   * Find minimum value node in a subtree
   * Used in delete operation for finding inorder successor
   */
  private TreeNode findMin(TreeNode node) {
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }

  /**
   * Find maximum value node in a subtree
   * Used for finding inorder predecessor
   */
  private TreeNode findMax(TreeNode node) {
    while (node.right != null) {
      node = node.right;
    }
    return node;
  }

  // =============================================================================
  // SECTION 2: TREE TRAVERSALS
  // =============================================================================

  /**
   * INORDER TRAVERSAL (Left -> Root -> Right)
   * Time: O(n), Space: O(h) where h is height
   *
   * SPECIAL PROPERTY: For BST, inorder gives sorted sequence!
   * This is crucial for many BST problems.
   */
  public List<Integer> inorderTraversal() {
    List<Integer> result = new ArrayList<>();
    inorderHelper(root, result);
    return result;
  }

  private void inorderHelper(TreeNode node, List<Integer> result) {
    if (node != null) {
      inorderHelper(node.left, result);
      result.add(node.val);
      inorderHelper(node.right, result);
    }
  }

  /*
   * ITERATIVE INORDER - Important for interview
   * Space: O(h) for stack
   */
  public List<Integer> inorderIterative() {
    List<Integer> result = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode current = root;

    while (current != null || !stack.isEmpty()) {
      // Go to leftmost node
      while (current != null) {
        stack.push(current);
        current = current.left;
      }
      // Process current node
      current = stack.pop();
      result.add(current.val);

      // Move to right subtree
      current = current.right;
    }
    return result;
  }

  /*
   * PREORDER TRAVERSAL (Root -> Left -> Right)
   * Used for creating copy of tree, expression trees
   */
  public List<Integer> preorderTraversal() {
    List<Integer> result = new ArrayList<>();
    preorderHelper(root, result);
    return result;
  }

  private void preorderHelper(TreeNode node, List<Integer> result) {
    if (node != null) {
      result.add(node.val);
      preorderHelper(node.left, result);
      preorderHelper(node.right, result);
    }
  }

  /**
   * POSTORDER TRAVERSAL (Left -> Right -> Root)
   * Used for deleting tree, calculating size/height
   */
  public List<Integer> postorderTraversal() {
    List<Integer> result = new ArrayList<>();
    postorderHelper(root, result);
    return result;
  }

  private void postorderHelper(TreeNode node, List<Integer> result) {
    if (node != null) {
      postorderHelper(node.left, result);
      postorderHelper(node.right, result);
      result.add(node.val);
    }
  }

  /**
   * LEVEL ORDER TRAVERSAL (BFS)
   * Time: O(n), Space: O(w) where w is maximum width
   *
   * INTERVIEW TIP: Very common question. Master this pattern!
   */
  public List<List<Integer>> levelOrder() {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null)
      return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      List<Integer> currentLevel = new ArrayList<>();

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

  // =============================================================================
  // SECTION 3: TREE PROPERTIES
  // =============================================================================

  /**
   * CALCULATE HEIGHT/DEPTH
   * Time: O(n), Space: O(h)
   *
   * Height = longest path from root to leaf
   * Depth = distance from root to a node
   */
  public int height() {
    return height(root);
  }

  private int height(TreeNode node) {
    if (node == null)
      return -1; // Height of empty tree is -1
    return 1 + Math.max(height(node.left), height(node.right));
  }

  /**
   * CALCULATE SIZE (number of nodes)
   * Time: O(n), Space: O(h)
   */
  public int size() {
    return size(root);
  }

  private int size(TreeNode node) {
    if (node == null)
      return 0;
    return 1 + size(node.left) + size(node.right);
  }

  /**
   * CHECK IF TREE IS BALANCED
   * Time: O(n), Space: O(h)
   *
   * A tree is balanced if height difference between left and right subtrees
   * is at most 1 for every node.
   */
  public boolean isBalanced() {
    return checkBalance(root) != -1;
  }

  private int checkBalance(TreeNode node) {
    if (node == null)
      return 0;

    int leftHeight = checkBalance(node.left);
    if (leftHeight == -1)
      return -1; // Left subtree is unbalanced

    int rightHeight = checkBalance(node.right);
    if (rightHeight == -1)
      return -1; // Right subtree is unbalanced

    // Check if current node is balanced
    if (Math.abs(leftHeight - rightHeight) > 1)
      return -1;

    return 1 + Math.max(leftHeight, rightHeight);
  }

  /**
   * Check if tree is a valid BST
   * Time: O(n), Space: O(h)
   *
   * APPROACH 1: Check if inorder traversal is sorted
   * APPROACH 2: Use min/max bounds (implemented below)
   */
  public boolean isValidBST() {
    return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
  }

  private boolean isValidBST(TreeNode node, long minVal, long maxVal) {
    if (node == null)
      return true;

    // Check if current node violates BST property
    if (node.val <= minVal || node.val >= maxVal)
      return false;

    // Recursively validate left and right subtrees with updated bounds
    return isValidBST(node.left, minVal, node.val) &&
        isValidBST(node.right, node.val, maxVal);
  }

  /**
   * Alternative BST validation using inorder traversal
   */
  public boolean isValidBSTInorder() {
    List<Integer> inorder = inorderTraversal();
    for (int i = 1; i < inorder.size(); i++) {
      if (inorder.get(i) <= inorder.get(i - 1))
        return false;
    }
    return true;
  }

  // =============================================================================
  // SECTION 4: ADVANCED BST OPERATIONS
  // =============================================================================

  /**
   * LOWEST COMMON ANCESTOR (LCA) in BST
   * Time: O(h), Space: O(h) recursive, O(1) iterative
   *
   * BST PROPERTY: LCA is the first node where paths to p and q diverge
   */
  public TreeNode lowestCommonAncestor(int p, int q) {
    return lcaHelper(root, p, q);
  }

  private TreeNode lcaHelper(TreeNode node, int p, int q) {
    if (node == null)
      return null;

    // If both p and q are smaller, LCA is in left subtree
    if (p < node.val && q < node.val) {
      return lcaHelper(node.left, p, q);
    }

    // If both p and q are larger, LCA is in right subtree
    if (p > node.val && q > node.val) {
      return lcaHelper(node.right, p, q);
    }

    // If p and q are on different sides, current node is LCA
    return node;
  }

  /**
   * ITERATIVE LCA - More space efficient
   */
  public TreeNode lcaIterative(int p, int q) {
    TreeNode current = root;

    while (current != null) {
      if (p < current.val && q < current.val) {
        current = current.left;
      } else if (p > current.val && q > current.val) {
        current = current.right;
      } else {
        return current; // Found LCA
      }
    }

    return null;
  }

  /**
   * KTH SMALLEST ELEMENT - Classic BST problem
   * Time: O(h + k), Space: O(h)
   *
   * APPROACH: Use inorder traversal (gives sorted order in BST)
   */
  public int kthSmallest(int k) {
    return kthSmallestHelper(root, new int[] { k });
  }

  private int kthSmallestHelper(TreeNode node, int[] k) {
    if (node == null)
      return -1;

    // Check left subtree first (smaller elements)
    int left = kthSmallestHelper(node.left, k);
    if (left != -1)
      return left; // Found in left subtree

    // Process current node
    k[0]--;
    if (k[0] == 0)
      return node.val;

    // Check right subtree
    return kthSmallestHelper(node.right, k);
  }

  /**
   * ITERATIVE KTH SMALLEST using stack
   */
  public int kthSmallestIterative(int k) {
    Stack<TreeNode> stack = new Stack<>();
    TreeNode current = root;

    while (current != null || !stack.isEmpty()) {
      while (current != null) {
        stack.push(current);
        current = current.left;
      }

      current = stack.pop();
      k--;
      if (k == 0)
        return current.val;

      current = current.right;
    }

    return -1; // k is larger than number of nodes
  }

  /**
   * INORDER SUCCESSOR - Next larger element
   * Time: O(h), Space: O(1)
   *
   * Two cases:
   * 1. Node has right subtree: successor is leftmost in right subtree
   * 2. No right subtree: successor is the ancestor where we came from left
   */
  public TreeNode inorderSuccessor(TreeNode node) {
    if (node == null)
      return null;

    // Case 1: Node has right subtree
    if (node.right != null) {
      return findMin(node.right);
    }

    // Case 2: Find ancestor from which we came from left
    TreeNode successor = null;
    TreeNode current = root;

    while (current != null) {
      if (node.val < current.val) {
        successor = current; // Potential successor
        current = current.left;
      } else if (node.val > current.val) {
        current = current.right;
      } else {
        break; // Found the node
      }
    }

    return successor;
  }

  /**
   * INORDER PREDECESSOR - Previous smaller element
   */
  public TreeNode inorderPredecessor(TreeNode node) {
    if (node == null)
      return null;

    // Case 1: Node has left subtree
    if (node.left != null) {
      return findMax(node.left);
    }

    // Case 2: Find ancestor from which we came from right
    TreeNode predecessor = null;
    TreeNode current = root;

    while (current != null) {
      if (node.val > current.val) {
        predecessor = current; // Potential predecessor
        current = current.right;
      } else if (node.val < current.val) {
        current = current.left;
      } else {
        break; // Found the node
      }
    }

    return predecessor;
  }

  /**
   * TREE DIAMETER - Longest path between any two nodes
   * Time: O(n), Space: O(h)
   */
  public int diameter() {
    int[] maxDiameter = new int[1];
    calculateHeight(root, maxDiameter);
    return maxDiameter[0];
  }

  private int calculateHeight(TreeNode node, int[] maxDiameter) {
    if (node == null)
      return 0;

    int leftHeight = calculateHeight(node.left, maxDiameter);
    int rightHeight = calculateHeight(node.right, maxDiameter);

    // Update diameter (path through current node)
    maxDiameter[0] = Math.max(maxDiameter[0], leftHeight + rightHeight);

    return 1 + Math.max(leftHeight, rightHeight);
  }

  // =============================================================================
  // SECTION 5: RANGE QUERIES AND PATH PROBLEMS
  // =============================================================================

  /**
   * RANGE SUM QUERY - Sum of all nodes in range [L, R]
   * Time: O(n) worst case, O(h + k) average where k is nodes in range
   */
  public int rangeSumBST(int low, int high) {
    return rangeSumHelper(root, low, high);
  }

  private int rangeSumHelper(TreeNode node, int low, int high) {
    if (node == null)
      return 0;

    // If current node is outside range, prune appropriately
    if (node.val < low) {
      return rangeSumHelper(node.right, low, high);
    }
    if (node.val > high) {
      return rangeSumHelper(node.left, low, high);
    }

    // Current node is in range
    return node.val +
        rangeSumHelper(node.left, low, high) +
        rangeSumHelper(node.right, low, high);
  }

  /**
   * PATH SUM - Check if there exists a root-to-leaf path with given sum
   * Time: O(n), Space: O(h)
   */
  public boolean hasPathSum(int targetSum) {
    return pathSumHelper(root, targetSum);
  }

  private boolean pathSumHelper(TreeNode node, int remainingSum) {
    if (node == null)
      return false;

    remainingSum -= node.val;

    // If leaf node, check if we've found the target sum
    if (node.left == null && node.right == null) {
      return remainingSum == 0;
    }

    // Check both subtrees
    return pathSumHelper(node.left, remainingSum) ||
        pathSumHelper(node.right, remainingSum);
  }

  /**
   * ALL PATH SUMS - Return all root-to-leaf paths with given sum
   */
  public List<List<Integer>> pathSum(int targetSum) {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> currentPath = new ArrayList<>();
    pathSumAllHelper(root, targetSum, currentPath, result);
    return result;
  }

  private void pathSumAllHelper(TreeNode node, int remainingSum,
      List<Integer> currentPath,
      List<List<Integer>> result) {
    if (node == null)
      return;

    currentPath.add(node.val);
    remainingSum -= node.val;

    // If leaf and sum matches, add path to result
    if (node.left == null && node.right == null && remainingSum == 0) {
      result.add(new ArrayList<>(currentPath));
    } else {
      // Continue exploring
      pathSumAllHelper(node.left, remainingSum, currentPath, result);
      pathSumAllHelper(node.right, remainingSum, currentPath, result);
    }

    // Backtrack
    currentPath.remove(currentPath.size() - 1);
  }

  // =============================================================================
  // SECTION 6: TREE CONSTRUCTION AND CONVERSION
  // =============================================================================

  /**
   * BUILD BST FROM SORTED ARRAY
   * Time: O(n), Space: O(log n)
   *
   * STRATEGY: Use middle element as root, recursively build left and right
   */
  public static TreeNode sortedArrayToBST(int[] nums) {
    return sortedArrayHelper(nums, 0, nums.length - 1);
  }

  private static TreeNode sortedArrayHelper(int[] nums, int left, int right) {
    if (left > right)
      return null;

    int mid = left + (right - left) / 2;
    TreeNode root = new TreeNode(nums[mid]);

    root.left = sortedArrayHelper(nums, left, mid - 1);
    root.right = sortedArrayHelper(nums, mid + 1, right);

    return root;
  }

  /**
   * BUILD BST FROM PREORDER TRAVERSAL
   * Time: O(n), Space: O(n)
   *
   * APPROACH: Use bounds to validate and construct
   */
  public static TreeNode bstFromPreorder(int[] preorder) {
    return preorderHelper(preorder, new int[] { 0 }, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private static TreeNode preorderHelper(int[] preorder, int[] index, int minVal, int maxVal) {
    if (index[0] >= preorder.length)
      return null;

    int val = preorder[index[0]];
    if (val < minVal || val > maxVal)
      return null;

    index[0]++;
    TreeNode root = new TreeNode(val);
    root.left = preorderHelper(preorder, index, minVal, val);
    root.right = preorderHelper(preorder, index, val, maxVal);

    return root;
  }

  /**
   * CONVERT BST TO DOUBLY LINKED LIST
   * Time: O(n), Space: O(h)
   *
   * APPROACH: Inorder traversal with pointer manipulation
   */
  public TreeNode convertToDoublyLinkedList() {
    if (root == null)
      return null;

    TreeNode[] prev = new TreeNode[1]; // Use array to pass by reference
    TreeNode[] head = new TreeNode[1];

    convertHelper(root, prev, head);

    return head[0];
  }

  private void convertHelper(TreeNode node, TreeNode[] prev, TreeNode[] head) {
    if (node == null)
      return;

    // Convert left subtree
    convertHelper(node.left, prev, head);

    // Process current node
    if (prev[0] == null) {
      head[0] = node; // First node becomes head
    } else {
      prev[0].right = node;
      node.left = prev[0];
    }
    prev[0] = node;

    // Convert right subtree
    convertHelper(node.right, prev, head);
  }

  // =============================================================================
  // SECTION 7: ADVANCED BST PROBLEMS
  // =============================================================================

  /**
   * RECOVER BST - Two nodes are swapped, fix the BST
   * Time: O(n), Space: O(h)
   *
   * APPROACH: Find two swapped nodes using inorder traversal
   */
  public void recoverTree() {
    TreeNode[] first = new TreeNode[1];
    TreeNode[] second = new TreeNode[1];
    TreeNode[] prev = new TreeNode[1];

    findSwappedNodes(root, first, second, prev);

    // Swap the values
    if (first[0] != null && second[0] != null) {
      int temp = first[0].val;
      first[0].val = second[0].val;
      second[0].val = temp;
    }
  }

  private void findSwappedNodes(TreeNode node, TreeNode[] first,
      TreeNode[] second, TreeNode[] prev) {
    if (node == null)
      return;

    findSwappedNodes(node.left, first, second, prev);

    // Check if current node violates BST property
    if (prev[0] != null && prev[0].val > node.val) {
      if (first[0] == null) {
        first[0] = prev[0]; // First violation
      }
      second[0] = node; // Always update second
    }
    prev[0] = node;

    findSwappedNodes(node.right, first, second, prev);
  }

  /**
   * SERIALIZE AND DESERIALIZE BST
   * More efficient than general binary tree serialization
   */
  public String serialize() {
    StringBuilder sb = new StringBuilder();
    serializeHelper(root, sb);
    return sb.toString();
  }

  private void serializeHelper(TreeNode node, StringBuilder sb) {
    if (node == null)
      return;

    sb.append(node.val).append(",");
    serializeHelper(node.left, sb);
    serializeHelper(node.right, sb);
  }

  public TreeNode deserialize(String data) {
    if (data.isEmpty())
      return null;

    String[] values = data.split(",");
    int[] preorder = new int[values.length];
    for (int i = 0; i < values.length; i++) {
      preorder[i] = Integer.parseInt(values[i]);
    }

    return bstFromPreorder(preorder);
  }

  /**
   * CLOSEST VALUE IN BST
   * Time: O(h), Space: O(1) iterative
   */
  public int closestValue(double target) {
    int closest = root.val;
    TreeNode current = root;

    while (current != null) {
      if (Math.abs(current.val - target) < Math.abs(closest - target)) {
        closest = current.val;
      }

      current = target < current.val ? current.left : current.right;
    }

    return closest;
  }

  /**
   * COUNT NODES IN RANGE [low, high]
   * Time: O(h + k) where k is result count
   */
  public int countNodesInRange(int low, int high) {
    return countInRangeHelper(root, low, high);
  }

  private int countInRangeHelper(TreeNode node, int low, int high) {
    if (node == null)
      return 0;

    if (node.val < low) {
      return countInRangeHelper(node.right, low, high);
    }
    if (node.val > high) {
      return countInRangeHelper(node.left, low, high);
    }

    return 1 + countInRangeHelper(node.left, low, high) +
        countInRangeHelper(node.right, low, high);
  }

  // =============================================================================
  // SECTION 8: UTILITY METHODS AND TREE DISPLAY
  // =============================================================================

  /**
   * PRETTY PRINT TREE - Useful for debugging
   */
  public void printTree() {
    printTreeHelper(root, "", true);
  }

  private void printTreeHelper(TreeNode node, String prefix, boolean isLast) {
    if (node != null) {
      System.out.println(prefix + (isLast ? "└── " : "├── ") + node.val);

      if (node.left != null || node.right != null) {
        if (node.left != null) {
          printTreeHelper(node.left, prefix + (isLast ? "    " : "│   "),
              node.right == null);
        }
        if (node.right != null) {
          printTreeHelper(node.right, prefix + (isLast ? "    " : "│   "), true);
        }
      }
    }
  }

  /**
   * GET ALL ELEMENTS IN SORTED ORDER
   * Time: O(n), Space: O(n)
   */
  public int[] toSortedArray() {
    List<Integer> inorder = inorderTraversal();
    return inorder.stream().mapToInt(Integer::intValue).toArray();
  }

  /**
   * CLEAR THE ENTIRE TREE
   * Time: O(1), Space: O(1)
   */
  public void clear() {
    root = null;
  }

  /**
   * CHECK IF TREE IS EMPTY
   */
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * GET ROOT VALUE (for testing purposes)
   */
  public Integer getRootValue() {
    return root == null ? null : root.val;
  }

  // =============================================================================
  // SECTION 9: COMPETITIVE PROGRAMMING SPECIFIC PROBLEMS
  // =============================================================================

  /**
   * MERGE TWO BSTs
   * Time: O(m + n), Space: O(m + n)
   *
   * APPROACH: Get inorder of both, merge sorted arrays, build BST
   */
  public static TreeNode mergeTwoBSTs(TreeNode root1, TreeNode root2) {
    // Get inorder traversals (sorted arrays)
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();

    inorderToList(root1, list1);
    inorderToList(root2, list2);

    // Merge two sorted lists
    List<Integer> merged = mergeSortedLists(list1, list2);

    // Convert merged list to BST
    return sortedListToBST(merged, 0, merged.size() - 1);
  }

  private static void inorderToList(TreeNode node, List<Integer> list) {
    if (node != null) {
      inorderToList(node.left, list);
      list.add(node.val);
      inorderToList(node.right, list);
    }
  }

  private static List<Integer> mergeSortedLists(List<Integer> list1, List<Integer> list2) {
    List<Integer> merged = new ArrayList<>();
    int i = 0, j = 0;

    while (i < list1.size() && j < list2.size()) {
      if (list1.get(i) <= list2.get(j)) {
        merged.add(list1.get(i++));
      } else {
        merged.add(list2.get(j++));
      }
    }

    while (i < list1.size())
      merged.add(list1.get(i++));
    while (j < list2.size())
      merged.add(list2.get(j++));

    return merged;
  }

  private static TreeNode sortedListToBST(List<Integer> list, int start, int end) {
    if (start > end)
      return null;

    int mid = start + (end - start) / 2;
    TreeNode root = new TreeNode(list.get(mid));

    root.left = sortedListToBST(list, start, mid - 1);
    root.right = sortedListToBST(list, mid + 1, end);

    return root;
  }

  /**
   * FIND MODE IN BST (most frequent element)
   * Time: O(n), Space: O(h)
   *
   * APPROACH: Use inorder traversal property of BST
   */
  public int[] findMode() {
    List<Integer> modes = new ArrayList<>();
    int[] maxCount = { 0 };
    int[] currentCount = { 0 };
    TreeNode[] prev = { null };

    findModeHelper(root, modes, maxCount, currentCount, prev);

    return modes.stream().mapToInt(i -> i).toArray();
  }

  private void findModeHelper(TreeNode node, List<Integer> modes,
      int[] maxCount, int[] currentCount, TreeNode[] prev) {
    if (node == null)
      return;

    findModeHelper(node.left, modes, maxCount, currentCount, prev);

    // Process current node
    if (prev[0] == null || prev[0].val != node.val) {
      currentCount[0] = 1;
    } else {
      currentCount[0]++;
    }

    if (currentCount[0] > maxCount[0]) {
      maxCount[0] = currentCount[0];
      modes.clear();
      modes.add(node.val);
    } else if (currentCount[0] == maxCount[0]) {
      modes.add(node.val);
    }

    prev[0] = node;

    findModeHelper(node.right, modes, maxCount, currentCount, prev);
  }

  /**
   * TRIM BST - Remove nodes outside range [low, high]
   * Time: O(n), Space: O(h)
   */
  public TreeNode trimBST(int low, int high) {
    return trimHelper(root, low, high);
  }

  private TreeNode trimHelper(TreeNode node, int low, int high) {
    if (node == null)
      return null;

    if (node.val < low) {
      return trimHelper(node.right, low, high);
    }
    if (node.val > high) {
      return trimHelper(node.left, low, high);
    }

    node.left = trimHelper(node.left, low, high);
    node.right = trimHelper(node.right, low, high);

    return node;
  }

  /**
   * SUM OF LEFT LEAVES
   * Time: O(n), Space: O(h)
   */
  public int sumOfLeftLeaves() {
    return sumLeftLeavesHelper(root, false);
  }

  private int sumLeftLeavesHelper(TreeNode node, boolean isLeft) {
    if (node == null)
      return 0;

    // If it's a left leaf
    if (isLeft && node.left == null && node.right == null) {
      return node.val;
    }

    return sumLeftLeavesHelper(node.left, true) +
        sumLeftLeavesHelper(node.right, false);
  }

  /**
   * FIND DISTANCE BETWEEN TWO NODES
   * Time: O(h), Space: O(h)
   */
  public int findDistance(int p, int q) {
    TreeNode lca = lowestCommonAncestor(p, q);
    return distanceFromNode(lca, p) + distanceFromNode(lca, q);
  }

  private int distanceFromNode(TreeNode node, int target) {
    if (node == null)
      return -1;
    if (node.val == target)
      return 0;

    int distance = target < node.val ? distanceFromNode(node.left, target) : distanceFromNode(node.right, target);

    return distance == -1 ? -1 : distance + 1;
  }

  // =============================================================================
  // SECTION 10: STRESS TESTING AND BENCHMARKING
  // =============================================================================

  /**
   * MAIN METHOD WITH COMPREHENSIVE TESTING
   * Use this to test all functionality and understand BST operations
   */
  public static void main(String[] args) {
    System.out.println("=== COMPREHENSIVE BST TESTING ===\n");

    // Create and populate BST
    BST bst = new BST();
    int[] values = { 50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45 };

    System.out.println("1. INSERTION TEST:");
    System.out.print("Inserting values: ");
    for (int val : values) {
      bst.insert(val);
      System.out.print(val + " ");
    }
    System.out.println("\n");

    // Display tree structure
    System.out.println("Tree structure:");
    bst.printTree();
    System.out.println();

    // Test basic properties
    System.out.println("2. BASIC PROPERTIES:");
    System.out.println("Height: " + bst.height());
    System.out.println("Size: " + bst.size());
    System.out.println("Is Balanced: " + bst.isBalanced());
    System.out.println("Is Valid BST: " + bst.isValidBST());
    System.out.println();

    // Test traversals
    System.out.println("3. TRAVERSALS:");
    System.out.println("Inorder: " + bst.inorderTraversal());
    System.out.println("Preorder: " + bst.preorderTraversal());
    System.out.println("Postorder: " + bst.postorderTraversal());
    System.out.println("Level Order: " + bst.levelOrder());
    System.out.println();

    // Test search operations
    System.out.println("4. SEARCH OPERATIONS:");
    int[] searchValues = { 25, 55, 80, 100 };
    for (int val : searchValues) {
      System.out.println("Search " + val + ": " + bst.search(val));
    }
    System.out.println();

    // Test advanced operations
    System.out.println("5. ADVANCED OPERATIONS:");
    System.out.println("3rd smallest: " + bst.kthSmallest(3));
    System.out.println("Range sum [25, 65]: " + bst.rangeSumBST(25, 65));
    System.out.println("LCA of 20 and 45: " + bst.lowestCommonAncestor(20, 45).val);
    System.out.println("Closest to 42.7: " + bst.closestValue(42.7));
    System.out.println("Nodes in range [30, 60]: " + bst.countNodesInRange(30, 60));
    System.out.println();

    // Test deletion
    System.out.println("6. DELETION TEST:");
    System.out.println("Before deletion - Inorder: " + bst.inorderTraversal());

    // Delete leaf node
    bst.delete(10);
    System.out.println("After deleting 10 (leaf): " + bst.inorderTraversal());

    // Delete node with one child
    bst.delete(25);
    System.out.println("After deleting 25 (one child): " + bst.inorderTraversal());

    // Delete node with two children
    bst.delete(30);
    System.out.println("After deleting 30 (two children): " + bst.inorderTraversal());
    System.out.println();

    // Final tree state
    System.out.println("Final tree structure:");
    bst.printTree();
    System.out.println();

    // Test tree construction
    System.out.println("7. TREE CONSTRUCTION:");
    int[] sortedArray = { 1, 2, 3, 4, 5, 6, 7 };
    TreeNode balancedRoot = sortedArrayToBST(sortedArray);
    BST balancedBST = new BST();
    balancedBST.root = balancedRoot;
    System.out.println("BST from sorted array [1,2,3,4,5,6,7]:");
    balancedBST.printTree();
    System.out.println("Is balanced: " + balancedBST.isBalanced());

    System.out.println("\n=== ALL TESTS COMPLETED ===");

    // Performance note
    System.out.println("\nPERFORMANCE NOTES:");
    System.out.println("- Balanced BST: O(log n) for all operations");
    System.out.println("- Skewed BST: O(n) worst case");
    System.out.println("- Consider AVL or Red-Black trees for guaranteed balance");
    System.out.println("- This implementation is optimized for interview questions");
  }

  static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }
}
