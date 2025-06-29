// ---------- Binary Tree Construction from Traversals ------------- //
package com.monal.Trees.GoodProblems;

import com.monal.Trees.TreeNode;
import java.util.*;
/*
 Theory:
  => What orders do you require to create a Unique Binary Tree?
    => Just Preorder (root, left, right) + Postorder (left, right, root) is NOT sufficient.
    => Example:
        Preorder:  [1, 2, 3]
        Postorder: [3, 2, 1]
    => Multiple valid trees can match these traversals:

      Tree 1:             Tree 2:
          1                   1
        /                   /
        2                   2
      /                     \
      3                       3

    => Both trees give same Pre + Post, but structure is different.
    => So, Preorder + Postorder cannot uniquely identify a binary tree.


 So you might question What orders do you require to create a Unique Binary Tree?

 Let's analyze all combinations:

 1) IN-ORDER alone:
    => NOT SUFFICIENT!
    => Multiple tree structures can have same in-order
    => Example: [1,2,3] could be linear left, balanced, or linear right

 2) PRE-ORDER alone or POST-ORDER alone:
    => NOT SUFFICIENT!
    => Same reason as in-order - multiple structures possible

 3) PRE-ORDER + POST-ORDER:
    => NOT SUFFICIENT for unique construction!
    => Why? Both tell us about root, but we can't distinguish left vs right subtrees
    => Example:
       Pre-order: [1, 2]  Post-order: [2, 1]
       This could be:
         1        OR      1
        /                  \
       2                    2
    => We can't tell if 2 is left child or right child!

 4) PRE-ORDER + IN-ORDER:
    => SUFFICIENT for unique construction! ✅
    => Why?
       - Pre-order gives us root
       - In-order tells us which nodes are in left vs right subtree
    => Algorithm:
       - First element in pre-order = root
       - Find root in in-order to split left/right subtrees
       - Recursively construct left and right subtrees

 5) POST-ORDER + IN-ORDER:
    => SUFFICIENT for unique construction! ✅
    => Why?
       - Post-order gives us root (last element)
       - In-order tells us which nodes are in left vs right subtree
    => Algorithm:
       - Last element in post-order = root
       - Find root in in-order to split left/right subtrees
       - Recursively construct left and right subtrees

 6) LEVEL-ORDER + IN-ORDER:
    => SUFFICIENT for unique construction! ✅
    => Why?
       - Level-order gives us root (first element)
       - In-order tells us left vs right subtree division

 KEY INSIGHT: You need IN-ORDER + one other traversal (except post+pre combination)

 Why is IN-ORDER special?
 => In-order traversal has the unique property that it visits left subtree,
    then root, then right subtree. This gives us the "boundary" information
    needed to split the tree into left and right parts.

 Edge Case for PRE+POST:
 => There's ONE exception where pre+post works: when all internal nodes have exactly 2 children
 => In this case, we can determine left/right because:
    - In pre-order: root, then all left subtree, then all right subtree
    - In post-order: all left subtree, all right subtree, then root
    - If we know the size of left subtree, we can split correctly
    - But this requires additional constraints!
*/

public class P006 {

  /*
   * Problem 1: Construct Binary Tree from Preorder and Inorder Traversal
   * Given two integer arrays preorder and inorder where preorder is the preorder
   * traversal of a binary tree and inorder is the inorder traversal of the same
   * tree,
   * construct and return the binary tree.
   *
   * Example:
   * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
   * Output:
   * 3
   * / \
   * 9 20
   * / \
   * 15 7
   */
  public TreeNode buildTreePreIn(int[] preorder, int[] inorder) {

    Map<Integer, Integer> inorderMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
      inorderMap.put(inorder[i], i);
    }

    return buildTreeHelper(preorder, 0, preorder.length - 1,
        inorder, 0, inorder.length - 1, inorderMap);
  }

  private TreeNode buildTreeHelper(
      int[] preorder, int preStart, int preEnd,
      int[] inorder, int inStart, int inEnd,
      Map<Integer, Integer> inorderMap) {

    // Base case: if indices are invalid, return null
    if (preStart > preEnd || inStart > inEnd) {
      return null;
    }

    // Root is the first element in preorder
    int rootVal = preorder[preStart];
    TreeNode root = new TreeNode(rootVal);

    // Find root position in inorder
    int rootIndex = inorderMap.get(rootVal);
    // Calculate size of left subtree
    int leftSubtreeSize = rootIndex - inStart;
    // Recursively build left and right subtrees
    root.left = buildTreeHelper(
        preorder, preStart + 1, preStart + leftSubtreeSize,
        inorder, inStart, rootIndex - 1, inorderMap);
    root.right = buildTreeHelper(preorder, preStart + leftSubtreeSize + 1, preEnd,
        inorder, rootIndex + 1, inEnd, inorderMap);

    return root;
  }

  /*
   * Problem 2: Construct Binary Tree from Postorder and Inorder Traversal
   * Given two integer arrays inorder and postorder where inorder is the inorder
   * traversal of a binary tree and postorder is the postorder traversal of the
   * same tree,
   * construct and return the binary tree.
   *
   * Example:
   * Input: inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
   * Output:
   * 3
   * / \
   * 9 20
   * / \
   * 15 7
   */
  public TreeNode buildTreePostIn(int[] inorder, int[] postorder) {
    Map<Integer, Integer> inorderMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) {
      inorderMap.put(inorder[i], i);
    }

    return buildTreePostHelper(postorder, 0, postorder.length - 1,
        inorder, 0, inorder.length - 1, inorderMap);
  }

  private TreeNode buildTreePostHelper(
      int[] postorder, int postStart, int postEnd,
      int[] inorder, int inStart, int inEnd,
      Map<Integer, Integer> inorderMap) {
    if (postStart > postEnd || inStart > inEnd) {
      return null;
    }

    // Root is the last element in postorder
    int rootVal = postorder[postEnd];
    TreeNode root = new TreeNode(rootVal);

    // Find root position in inorder
    int rootIndex = inorderMap.get(rootVal);

    // Calculate size of left subtree
    int leftSubtreeSize = rootIndex - inStart;

    // Recursively build left and right subtrees
    root.left = buildTreePostHelper(
        postorder, postStart, postStart + leftSubtreeSize - 1,
        inorder, inStart, rootIndex - 1, inorderMap);

    root.right = buildTreePostHelper(
        postorder, postStart + leftSubtreeSize, postEnd - 1,
        inorder, rootIndex + 1, inEnd, inorderMap);

    return root;
  }

  /*
   * Problem 3: Verify if given traversals can form a valid binary tree
   *
   * Given preorder and postorder traversals, determine if they can represent
   * a valid binary tree. If yes, construct one possible tree.
   *
   * Note: This will work only for full binary trees (every internal node has 2
   * children)
   * or when we make assumptions about structure.
   *
   * Example:
   * Input: preorder = [1,2,4,5,3,6,7], postorder = [4,5,2,6,7,3,1]
   * Output: Valid - can construct tree
   *
   * Key insight: For full binary trees, we can determine structure:
   * - preorder[1] is root of left subtree
   * - Find preorder[1] in postorder to determine left subtree size
   */
  public TreeNode buildTreePrePost(int[] preorder, int[] postorder) {
    return buildTreePrePostHelper(preorder, 0, preorder.length - 1,
        postorder, 0, postorder.length - 1);
  }

  private TreeNode buildTreePrePostHelper(int[] preorder, int preStart, int preEnd,
      int[] postorder, int postStart, int postEnd) {
    if (preStart > preEnd)
      return null;

    TreeNode root = new TreeNode(preorder[preStart]);

    // If only one node
    if (preStart == preEnd)
      return root;

    // Find the root of left subtree (second element in preorder)
    int leftRootVal = preorder[preStart + 1];

    // Find this value in postorder to determine left subtree size
    int leftRootPostIndex = postStart;
    for (int i = postStart; i <= postEnd; i++) {
      if (postorder[i] == leftRootVal) {
        leftRootPostIndex = i;
        break;
      }
    }

    // Size of left subtree
    int leftSubtreeSize = leftRootPostIndex - postStart + 1;

    // Build left and right subtrees
    root.left = buildTreePrePostHelper(preorder, preStart + 1, preStart + leftSubtreeSize,
        postorder, postStart, leftRootPostIndex);

    root.right = buildTreePrePostHelper(preorder, preStart + leftSubtreeSize + 1, preEnd,
        postorder, leftRootPostIndex + 1, postEnd - 1);

    return root;
  }

  /*
   * Challenge Problem: Given three traversals (preorder, inorder, postorder),
   * verify if they represent the same binary tree.
   *
   * Approach:
   * 1. Construct tree from preorder + inorder
   * 2. Generate postorder from constructed tree
   * 3. Compare with given postorder
   */
  public boolean verifyTraversals(int[] preorder, int[] inorder, int[] postorder) {
    // Build tree from pre + in
    TreeNode tree = buildTreePreIn(preorder, inorder);

    // Generate postorder from tree
    List<Integer> generatedPost = new ArrayList<>();
    generatePostorder(tree, generatedPost);

    // Compare with given postorder
    if (generatedPost.size() != postorder.length)
      return false;

    for (int i = 0; i < postorder.length; i++) {
      if (generatedPost.get(i) != postorder[i]) {
        return false;
      }
    }

    return true;
  }

  private void generatePostorder(TreeNode root, List<Integer> result) {
    if (root == null)
      return;

    generatePostorder(root.left, result);
    generatePostorder(root.right, result);
    result.add(root.val);
  }
}