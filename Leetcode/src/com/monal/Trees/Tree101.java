/*

// PATTERN 1: DFS Recursive (for path/property problems)

public ReturnType dfsRecursive(TreeNode root) {
  // Base case
  if (root == null)
    return baseValue;

  // Process current node
  // ... logic for current node

  // Recurse on children
  ReturnType left = dfsRecursive(root.left);
  ReturnType right = dfsRecursive(root.right);

  // Combine results
  return combineResults(left, right, root.val);
}

// PATTERN 2: BFS Level-by-Level (for level-related problems)
public ReturnType bfsLevelOrder(TreeNode root) {
  if (root == null)
    return defaultValue;

  Queue<TreeNode> queue = new LinkedList<>();
  queue.offer(root);

  while (!queue.isEmpty()) {
    int levelSize = queue.size();

    // Process entire level
    for (int i = 0; i < levelSize; i++) {
      TreeNode node = queue.poll();

      // Process current node
      // ... logic for current node

      // Add children for next level
      if (node.left != null)
        queue.offer(node.left);
      if (node.right != null)
        queue.offer(node.right);
    }

    // Process level results
    // ... logic after completing a level
  }

  return result;
}

// PATTERN 3: BFS with Extra Data (like our width problem)
public ReturnType bfsWithData(TreeNode root) {
  if (root == null)
    return defaultValue;

  Queue<NodeData> queue = new LinkedList<>();
  queue.offer(new NodeData(root, initialData));

  while (!queue.isEmpty()) {
    int levelSize = queue.size();

    // Track level-specific data
    DataType levelData = initializeLevelData();

    for (int i = 0; i < levelSize; i++) {
      NodeData current = queue.poll();
      TreeNode node = current.node;

      // Process with extra data
      processNode(node, current.data, levelData);

      // Add children with calculated data
      if (node.left != null) {
        queue.offer(new NodeData(node.left, calculateChildData(current.data)));
      }
      if (node.right != null) {
        queue.offer(new NodeData(node.right, calculateChildData(current.data)));
      }
    }

    // Process level results
    updateGlobalResult(levelData);
  }

  return result;
}

// Helper class for carrying extra data
class NodeData {
  TreeNode node;
  DataType data; // index, depth, path, etc.

  NodeData(TreeNode node, DataType data) {
    this.node = node;
    this.data = data;
  }
}

*/