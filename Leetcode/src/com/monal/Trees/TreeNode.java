// People can import this class directly from the package com.monal.Trees
package com.monal.Trees;

import java.util.*;

/**
 * Comprehensive TreeNode class for all LeetCode tree problems
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    // Basic constructors
    public TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    /**
     * Create tree from array representation (level order)
     * null values represent missing nodes
     * Example: [3,9,20,null,null,15,7] creates standard binary tree
     */
    public static TreeNode fromArray(Integer[] arr) {
        if (arr == null || arr.length == 0 || arr[0] == null)
            return null;

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < arr.length) {
            TreeNode node = queue.poll();

            // Left child
            if (i < arr.length && arr[i] != null) {
                node.left = new TreeNode(arr[i]);
                queue.offer(node.left);
            }
            i++;

            // Right child
            if (i < arr.length && arr[i] != null) {
                node.right = new TreeNode(arr[i]);
                queue.offer(node.right);
            }
            i++;
        }
        return root;
    }

    /**
     * Convert tree to array representation (level order)
     * Useful for testing and visualization
     */
    public static List<Integer> toArray(TreeNode root) {
        if (root == null)
            return new ArrayList<>();

        List<Integer> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node != null) {
                result.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                result.add(null);
            }
        }

        // Remove trailing nulls
        while (!result.isEmpty() && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }

        return result;
    }

    /**
     * Pretty print tree structure
     * Great for debugging and visualization
     */
    public static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("Empty tree");
            return;
        }
        printTreeHelper(root, "", true);
    }

    private static void printTreeHelper(TreeNode node, String prefix, boolean isLast) {
        if (node != null) {
            System.out.println(prefix + (isLast ? "└── " : "├── ") + node.val);

            if (node.left != null || node.right != null) {
                if (node.left != null) {
                    printTreeHelper(node.left, prefix + (isLast ? "    " : "│   "), node.right == null);
                }
                if (node.right != null) {
                    printTreeHelper(node.right, prefix + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }

    // ===== COMMON TREE OPERATIONS =====

    /**
     * Get height/depth of tree
     */
    public static int height(TreeNode root) {
        if (root == null)
            return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    /**
     * Count total number of nodes
     */
    public static int countNodes(TreeNode root) {
        if (root == null)
            return 0;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    /**
     * Check if tree is empty
     */
    public static boolean isEmpty(TreeNode root) {
        return root == null;
    }

    /**
     * Find a node with given value
     */
    public static TreeNode findNode(TreeNode root, int target) {
        if (root == null || root.val == target)
            return root;

        TreeNode left = findNode(root.left, target);
        if (left != null)
            return left;

        return findNode(root.right, target);
    }

    /**
     * Check if two trees are identical
     */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;
        if (p == null || q == null)
            return false;
        return p.val == q.val &&
                isSameTree(p.left, q.left) &&
                isSameTree(p.right, q.right);
    }

    // ===== TRAVERSAL METHODS =====

    /**
     * Inorder traversal (Left, Root, Right)
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private static void inorderHelper(TreeNode node, List<Integer> result) {
        if (node != null) {
            inorderHelper(node.left, result);
            result.add(node.val);
            inorderHelper(node.right, result);
        }
    }

    /**
     * Preorder traversal (Root, Left, Right)
     */
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private static void preorderHelper(TreeNode node, List<Integer> result) {
        if (node != null) {
            result.add(node.val);
            preorderHelper(node.left, result);
            preorderHelper(node.right, result);
        }
    }

    /**
     * Postorder traversal (Left, Right, Root)
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    private static void postorderHelper(TreeNode node, List<Integer> result) {
        if (node != null) {
            postorderHelper(node.left, result);
            postorderHelper(node.right, result);
            result.add(node.val);
        }
    }

    /**
     * Level order traversal (BFS)
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
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

    // ===== VALIDATION METHODS =====

    /**
     * Check if tree is a valid BST
     */
    public static boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean isValidBSTHelper(TreeNode node, long min, long max) {
        if (node == null)
            return true;
        if (node.val <= min || node.val >= max)
            return false;
        return isValidBSTHelper(node.left, min, node.val) &&
                isValidBSTHelper(node.right, node.val, max);
    }

    /**
     * Check if tree is balanced
     */
    public static boolean isBalanced(TreeNode root) {
        return balancedHeight(root) != -1;
    }

    private static int balancedHeight(TreeNode node) {
        if (node == null)
            return 0;

        int leftHeight = balancedHeight(node.left);
        if (leftHeight == -1)
            return -1;

        int rightHeight = balancedHeight(node.right);
        if (rightHeight == -1)
            return -1;

        if (Math.abs(leftHeight - rightHeight) > 1)
            return -1;
        return Math.max(leftHeight, rightHeight) + 1;
    }

    // ===== HELPER METHODS FOR COMMON PATTERNS =====

    /**
     * Find path from root to target node
     */
    public static List<Integer> pathToNode(TreeNode root, int target) {
        List<Integer> path = new ArrayList<>();
        findPath(root, target, path);
        return path;
    }

    private static boolean findPath(TreeNode node, int target, List<Integer> path) {
        if (node == null)
            return false;

        path.add(node.val);
        if (node.val == target)
            return true;

        if (findPath(node.left, target, path) || findPath(node.right, target, path)) {
            return true;
        }

        path.remove(path.size() - 1); // Backtrack
        return false;
    }

    /**
     * Find Lowest Common Ancestor
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q)
            return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null)
            return root;
        return left != null ? left : right;
    }

    // ===== TREE BUILDING HELPERS =====

    /**
     * Quick method to build small test trees
     */
    public static class TreeBuilder {
        public static TreeNode build(Integer... values) {
            return fromArray(values);
        }

        // Common test trees
        public static TreeNode perfectBinaryTree() {
            return fromArray(new Integer[] { 1, 2, 3, 4, 5, 6, 7 });
        }

        public static TreeNode leftSkewedTree() {
            return fromArray(new Integer[] { 1, 2, null, 3, null, 4, null });
        }

        public static TreeNode rightSkewedTree() {
            return fromArray(new Integer[] { 1, null, 2, null, 3, null, 4 });
        }

        public static TreeNode singleNode() {
            return new TreeNode(1);
        }
    }

    // ===== OVERRIDE METHODS =====

    @Override
    public String toString() {
        return "TreeNode{val=" + val + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        TreeNode other = (TreeNode) obj;
        return isSameTree(this, other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, left, right);
    }
}
