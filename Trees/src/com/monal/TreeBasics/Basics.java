package com.monal.TreeBasics;

import java.util.*;

/**
 * COMPREHENSIVE BINARY TREES GUIDE FOR DSA INTERVIEWS
 *
 * This code covers ALL essential tree concepts that commonly appear in:
 * - Technical interviews (Google, Amazon, Microsoft, etc.)
 * - Online assessments (LeetCode, HackerRank, etc.)
 * - Competitive programming
 *
 * Study Strategy:
 * 1. Read through each section carefully
 * 2. Run the code and trace through examples
 * 3. Practice implementing each algorithm from scratch
 * 4. Solve variations of each problem type
 */

// Basic Tree Node Structure - Foundation of all tree problems
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class Basics {

    // ==================== SECTION 1: TREE TRAVERSALS ====================
    // These are FUNDAMENTAL - Master these first!

    /**
     * INORDER TRAVERSAL (Left -> Root -> Right)
     * Time: O(n), Space: O(h) where h is height
     *
     * KEY INSIGHT: For BST, inorder gives sorted sequence
     * INTERVIEW TIP: Always mention both recursive and iterative approaches
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;

        inorderHelper(node.left, result);   // Left
        result.add(node.val);               // Root
        inorderHelper(node.right, result);  // Right
    }

    /**
     * ITERATIVE INORDER - Often asked in follow-up questions
     * TRICK: Use stack to simulate recursion
     */
    public List<Integer> inorderIterative(TreeNode root) {
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

    /**
     * PREORDER TRAVERSAL (Root -> Left -> Right)
     * USAGE: Tree serialization, expression trees
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        result.add(root.val);                    // Root
        result.addAll(preorderTraversal(root.left));  // Left
        result.addAll(preorderTraversal(root.right)); // Right

        return result;
    }

    /**
     * POSTORDER TRAVERSAL (Left -> Right -> Root)
     * USAGE: Tree deletion, calculating directory sizes
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        result.addAll(postorderTraversal(root.left));  // Left
        result.addAll(postorderTraversal(root.right)); // Right
        result.add(root.val);                          // Root

        return result;
    }

    /**
     * LEVEL ORDER TRAVERSAL (BFS)
     * CRITICAL for many interview problems
     * Time: O(n), Space: O(w) where w is maximum width
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // KEY: Capture size before processing
            List<Integer> currentLevel = new ArrayList<>();

            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);

                // Add children for next level
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(currentLevel);
        }

        return result;
    }

    // ==================== SECTION 2: TREE PROPERTIES ====================

    /**
     * MAXIMUM DEPTH (HEIGHT) - Classic recursive problem
     * INSIGHT: Height of tree = 1 + max(height of left, height of right)
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    /**
     * MINIMUM DEPTH - Tricky variation
     * GOTCHA: Must reach a LEAF node, not just any null
     */
    public int minDepth(TreeNode root) {
        if (root == null) return 0;

        // If one subtree is empty, return depth of other + 1
        if (root.left == null) return 1 + minDepth(root.right);
        if (root.right == null) return 1 + minDepth(root.left);

        // Both subtrees exist
        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }

    /**
     * BALANCED BINARY TREE - Amazon/Google favorite
     * DEFINITION: Height difference between left and right subtrees ≤ 1
     * OPTIMIZATION: Calculate height and check balance in single pass
     */
    public boolean isBalanced(TreeNode root) {
        return checkBalance(root) != -1;
    }

    private int checkBalance(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = checkBalance(node.left);
        if (leftHeight == -1) return -1; // Left subtree unbalanced

        int rightHeight = checkBalance(node.right);
        if (rightHeight == -1) return -1; // Right subtree unbalanced

        // Check if current node is balanced
        if (Math.abs(leftHeight - rightHeight) > 1) return -1;

        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * DIAMETER OF BINARY TREE - Microsoft/Facebook favorite
     * DEFINITION: Longest path between any two nodes
     * KEY INSIGHT: Path may or may not pass through root
     */
    private int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;
        calculateHeight(root);
        return maxDiameter;
    }

    private int calculateHeight(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = calculateHeight(node.left);
        int rightHeight = calculateHeight(node.right);

        // Update diameter: path through current node
        maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);

        return 1 + Math.max(leftHeight, rightHeight);
    }

    // ==================== SECTION 3: TREE COMPARISON ====================

    /**
     * SAME TREE - Fundamental recursive pattern
     * APPROACH: Compare structure and values simultaneously
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // Base cases
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;

        // Compare current nodes and recurse
        return p.val == q.val &&
               isSameTree(p.left, q.left) &&
               isSameTree(p.right, q.right);
    }

    /**
     * SYMMETRIC TREE - Mirror image check
     * TRICK: Compare left subtree with right subtree in mirror fashion
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;

        return left.val == right.val &&
               isMirror(left.left, right.right) &&
               isMirror(left.right, right.left);
    }

    /**
     * SUBTREE OF ANOTHER TREE
     * STRATEGY: For each node in main tree, check if subtree starts there
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) return false;

        // Check if subtree starts at current node
        if (isSameTree(root, subRoot)) return true;

        // Check left and right subtrees
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    // ==================== SECTION 4: PATH PROBLEMS ====================

    /**
     * BINARY TREE PATHS - Generate all root-to-leaf paths
     * TECHNIQUE: Backtracking with string manipulation
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;

        findPaths(root, "", result);
        return result;
    }

    private void findPaths(TreeNode node, String path, List<String> result) {
        if (node == null) return;

        // Add current node to path
        path += node.val;

        // If leaf node, add path to result
        if (node.left == null && node.right == null) {
            result.add(path);
            return;
        }

        // Continue to children
        path += "->";
        findPaths(node.left, path, result);
        findPaths(node.right, path, result);
    }

    /**
     * PATH SUM - Check if root-to-leaf path with given sum exists
     * PATTERN: Reduce target sum as we traverse
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;

        // If leaf node, check if sum matches
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }

        // Recurse with reduced sum
        int remainingSum = targetSum - root.val;
        return hasPathSum(root.left, remainingSum) ||
               hasPathSum(root.right, remainingSum);
    }

    /**
     * PATH SUM II - Find all root-to-leaf paths with given sum
     * TECHNIQUE: Backtracking with list manipulation
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> currentPath = new ArrayList<>();

        pathSumHelper(root, targetSum, currentPath, result);
        return result;
    }

    private void pathSumHelper(TreeNode node, int targetSum,
                              List<Integer> currentPath, List<List<Integer>> result) {
        if (node == null) return;

        // Add current node to path
        currentPath.add(node.val);

        // If leaf and sum matches, add path to result
        if (node.left == null && node.right == null && node.val == targetSum) {
            result.add(new ArrayList<>(currentPath)); // IMPORTANT: Create new list
        }

        // Recurse to children
        int remainingSum = targetSum - node.val;
        pathSumHelper(node.left, remainingSum, currentPath, result);
        pathSumHelper(node.right, remainingSum, currentPath, result);

        // BACKTRACK: Remove current node from path
        currentPath.remove(currentPath.size() - 1);
    }

    // ==================== SECTION 5: BINARY SEARCH TREE ====================

    /**
     * VALIDATE BST - Extremely common interview question
     * GOTCHA: Must check against global bounds, not just parent
     * APPROACH: Pass valid range for each node
     */
    public boolean isValidBST(TreeNode root) {
        return validateBST(root, null, null);
    }

    private boolean validateBST(TreeNode node, Integer minVal, Integer maxVal) {
        if (node == null) return true;

        // Check if current node violates BST property
        if ((minVal != null && node.val <= minVal) ||
            (maxVal != null && node.val >= maxVal)) {
            return false;
        }

        // Recurse with updated bounds
        return validateBST(node.left, minVal, node.val) &&
               validateBST(node.right, node.val, maxVal);
    }

    /**
     * SEARCH IN BST - Utilize BST property for O(log n) search
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null || root.val == val) return root;

        // Use BST property to decide direction
        return val < root.val ? searchBST(root.left, val) : searchBST(root.right, val);
    }

    /**
     * INSERT INTO BST - Maintain BST property
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);

        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }

        return root;
    }

    /**
     * DELETE FROM BST - Complex but important
     * CASES:
     * 1. Node has no children - simply remove
     * 2. Node has one child - replace with child
     * 3. Node has two children - replace with inorder successor
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            // Node to be deleted found

            // Case 1: No children
            if (root.left == null && root.right == null) {
                return null;
            }

            // Case 2: One child
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            // Case 3: Two children
            // Find inorder successor (smallest in right subtree)
            TreeNode successor = findMin(root.right);
            root.val = successor.val;
            root.right = deleteNode(root.right, successor.val);
        }

        return root;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // ==================== SECTION 6: TREE CONSTRUCTION ====================

    /**
     * BUILD TREE FROM PREORDER AND INORDER
     * KEY INSIGHT: First element of preorder is root
     * TECHNIQUE: Use HashMap for O(1) inorder lookups
     */
    public TreeNode buildTreePreIn(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inorderMap = new HashMap<>();

        // Build map for O(1) inorder index lookup
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        return buildHelper(preorder, 0, preorder.length - 1,
                          inorder, 0, inorder.length - 1, inorderMap);
    }

    private TreeNode buildHelper(int[] preorder, int preStart, int preEnd,
                                int[] inorder, int inStart, int inEnd,
                                Map<Integer, Integer> inorderMap) {
        if (preStart > preEnd || inStart > inEnd) return null;

        // Root is first element in preorder
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        // Find root position in inorder
        int rootIndex = inorderMap.get(rootVal);
        int leftSize = rootIndex - inStart;

        // Build left and right subtrees
        root.left = buildHelper(preorder, preStart + 1, preStart + leftSize,
                               inorder, inStart, rootIndex - 1, inorderMap);
        root.right = buildHelper(preorder, preStart + leftSize + 1, preEnd,
                                inorder, rootIndex + 1, inEnd, inorderMap);

        return root;
    }

    // ==================== SECTION 7: ADVANCED ALGORITHMS ====================

    /**
     * LOWEST COMMON ANCESTOR (LCA) - Very popular in interviews
     * INSIGHT: First node where paths to p and q diverge
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // If both sides return non-null, current node is LCA
        if (left != null && right != null) return root;

        // Return whichever side has result
        return left != null ? left : right;
    }

    /**
     * SERIALIZE AND DESERIALIZE BINARY TREE
     * TECHNIQUE: Use preorder traversal with null markers
     */
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null,");
            return;
        }

        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    public TreeNode deserialize(String data) {
        String[] nodes = data.split(",");
        int[] index = {0}; // Use array to pass by reference
        return deserializeHelper(nodes, index);
    }

    private TreeNode deserializeHelper(String[] nodes, int[] index) {
        if (nodes[index[0]].equals("null")) {
            index[0]++;
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(nodes[index[0]]));
        index[0]++;

        node.left = deserializeHelper(nodes, index);
        node.right = deserializeHelper(nodes, index);

        return node;
    }

    /**
     * MAXIMUM PATH SUM - Google/Amazon favorite
     * INSIGHT: Path can start and end at any nodes
     * TECHNIQUE: For each node, calculate max gain from its subtree
     */
    private int maxPathSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxPathSum = Integer.MIN_VALUE;
        maxPathSumHelper(root);
        return maxPathSum;
    }

    private int maxPathSumHelper(TreeNode node) {
        if (node == null) return 0;

        // Get max gain from left and right subtrees (ignore negative gains)
        int leftGain = Math.max(0, maxPathSumHelper(node.left));
        int rightGain = Math.max(0, maxPathSumHelper(node.right));

        // Update global maximum (path through current node)
        maxPathSum = Math.max(maxPathSum, node.val + leftGain + rightGain);

        // Return max gain from current node (can only go one direction)
        return node.val + Math.max(leftGain, rightGain);
    }

    /**
     * FLATTEN BINARY TREE TO LINKED LIST
     * REQUIREMENT: Transform tree to right-skewed linked list (preorder)
     * TECHNIQUE: Reverse preorder traversal
     */
    private TreeNode prev = null;

    public void flatten(TreeNode root) {
        if (root == null) return;

        // Process in reverse preorder (right -> left -> root)
        flatten(root.right);
        flatten(root.left);

        // Make current node point to previous processed node
        root.right = prev;
        root.left = null;
        prev = root;
    }

    // ==================== SECTION 8: UTILITY METHODS ====================

    /**
     * PRINT TREE STRUCTURE - For debugging and visualization
     */
    public void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }

        printTreeHelper(root, "", true);
    }

    private void printTreeHelper(TreeNode node, String prefix, boolean isLast) {
        if (node == null) return;

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

    /**
     * CREATE SAMPLE TREE FOR TESTING
     */
    public static TreeNode createSampleTree() {
        /*
         * Creating tree:
         *       3
         *      / \
         *     9   20
         *        /  \
         *       15   7
         */
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        return root;
    }

    // ==================== MAIN METHOD - DEMONSTRATION ====================

    public static void main(String[] args) {
        Basics btmc = new Basics();
        TreeNode root = createSampleTree();

        System.out.println("=== BINARY TREE OPERATIONS DEMO ===\n");

        // Tree structure
        System.out.println("Tree Structure:");
        btmc.printTree(root);

        // Traversals
        System.out.println("\n=== TRAVERSALS ===");
        System.out.println("Inorder: " + btmc.inorderTraversal(root));
        System.out.println("Preorder: " + btmc.preorderTraversal(root));
        System.out.println("Postorder: " + btmc.postorderTraversal(root));
        System.out.println("Level Order: " + btmc.levelOrder(root));

        // Properties
        System.out.println("\n=== PROPERTIES ===");
        System.out.println("Max Depth: " + btmc.maxDepth(root));
        System.out.println("Min Depth: " + btmc.minDepth(root));
        System.out.println("Is Balanced: " + btmc.isBalanced(root));
        System.out.println("Diameter: " + btmc.diameterOfBinaryTree(root));

        // Paths
        System.out.println("\n=== PATHS ===");
        System.out.println("All Paths: " + btmc.binaryTreePaths(root));
        System.out.println("Has Path Sum (22): " + btmc.hasPathSum(root, 22));
        System.out.println("Path Sum (22): " + btmc.pathSum(root, 22));

        // Serialization
        System.out.println("\n=== SERIALIZATION ===");
        String serialized = btmc.serialize(root);
        System.out.println("Serialized: " + serialized);
        TreeNode deserialized = btmc.deserialize(serialized);
        System.out.println("Same after deserialization: " + btmc.isSameTree(root, deserialized));

        System.out.println("\n=== STUDY COMPLETED ===");
        System.out.println("Practice these patterns and you'll ace your interviews!");
    }
}

/*
 * ==================== INTERVIEW TIPS AND PATTERNS ====================
 *
 * 1. ALWAYS ask about tree properties:
 *    - Is it a BST?
 *    - Is it balanced?
 *    - Can values be negative?
 *    - Are there duplicate values?
 *
 * 2. COMMON PATTERNS:
 *    - DFS (recursion/stack) for path problems
 *    - BFS (queue) for level-based problems
 *    - Two-pointer technique for BST problems
 *    - Divide and conquer for construction problems
 *
 * 3. EDGE CASES to always consider:
 *    - Empty tree (root == null)
 *    - Single node tree
 *    - Skewed tree (all nodes on one side)
 *    - Complete/perfect tree
 *
 * 4. COMPLEXITY ANALYSIS:
 *    - Time: Usually O(n) for traversing all nodes
 *    - Space: O(h) for recursion stack, O(w) for BFS queue
 *    - where h = height, w = maximum width
 *
 * 5. OPTIMIZATION TRICKS:
 *    - Use HashMap for O(1) lookups in construction problems
 *    - Combine multiple passes into single pass when possible
 *    - Use Morris traversal for O(1) space traversal (advanced)
 *    - Early termination when answer is found
 *
 * 6. DEBUGGING TIPS:
 *    - Draw the tree structure on paper
 *    - Trace through small examples
 *    - Use print statements to visualize traversal
 *    - Check base cases thoroughly
 *
 * 7. FOLLOW-UP QUESTIONS to expect:
 *    - "Can you do this iteratively?"
 *    - "What if the tree is very large?"
 *    - "How would you optimize for repeated queries?"
 *    - "What if the tree is modified frequently?"
 *
 * REMEMBER: Practice makes perfect! Start with basic traversals,
 * then move to more complex problems. Good luck with your interviews!
 */