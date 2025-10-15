package CodeForces.src.com.contestOne;

/*
You are given an integer n which represents an array nums containing the numbers from 1 to n in order. Additionally, you are given a 2D array conflictingPairs, where conflictingPairs[i] = [a, b] indicates that a and b form a conflicting pair.

Remove exactly one element from conflictingPairs. Afterward, count the number of non-empty subarrays of nums which do not contain both a and b for any remaining conflicting pair [a, b].

Return the maximum number of subarrays possible after removing exactly one conflicting pair.
*/
/*
Hint 1
  Let f[i] (where i = 1, 2, 3, ..., n) be the end index of the longest valid subarray (without any conflicting pair) starting at index i.
Hint 2
  The answer is: sigma(f[i] - i + 1) for i in [1..n], which simplifies to: sigma(f[i]) - n * (n + 1) / 2 + n.
Hint 3
  Focus on maintaining f[i].
Hint 4
  If we have a conflicting pair (x, y) with x < y: 1. Sort the conflicting pairs by y values in non-increasing order. 2. Update each prefix of the f array accordingly.
Hint 5
  Use a segment tree or another suitable data structure to maintain the range update and sum query efficiently.
 */
public class abc {
  class Solution{

  }

  class Solution_Brute {
    public long maxSubarrays(int n, int[][] conflictingPairs) {
      long maxCount = 0;
      // we remvoe each conflictiong pair
      for (int rI = 0; rI < conflictingPairs.length; rI++)
        maxCount = Math.max(maxCount, countValidSubarrays(n, conflictingPairs, rI));

      return maxCount;
    }

    private long countValidSubarrays(int n, int[][] conflictingPairs, int skipIndex) {
      long validCount = 0;

      for (int i = 1; i <= n; i++)
        for (int j = i; j <= n; j++)
          if (isValidSubarray(i, j, conflictingPairs, skipIndex))
            validCount++;

      return validCount;
    }

    private boolean isValidSubarray(int start, int end, int[][] conflictingPairs, int skipIndex) {
      // Check if this subarray [start, end] violates any remaining conflicting pairs
      for (int i = 0; i < conflictingPairs.length; i++) {
        if (i == skipIndex)
          continue; // Skip the removed pair

        int a = conflictingPairs[i][0], b = conflictingPairs[i][1];

        // Check if both a and b are in the subarray [start, end]
        if (a >= start && a <= end && b >= start && b <= end)
          return false; // invalid -> a, b resides
      }
      return true; // Valid subarray
    }
  }
  /**
   * # Why TLE Occurs in Brute Force
   *
   * ## Time Complexity Breakdown:
   * - **k** = number of conflicting pairs (can be up to 10^5)
   * - **n** = size of array (can be up to 10^5)
   *
   * ### Brute Force Complexity:
   * ```
   * for each pair removal (k times):
   * for each starting position (n times):
   * for each ending position (n times):
   * check all remaining pairs (k times)
   * ```
   ** Total: O(k × n² × k) = O(k² × n²)**
   *
   * ### With typical constraints:
   * - k = 10^5, n = 10^5
   * - Operations = (10^5)² × (10^5)² = 10^20 operations
   * - Even at 10^9 operations/second, this takes 10^11 seconds! 💥
   *
   * ---
   *
   * # How f[i] Optimizes This
   *
   * ## Key Insight: Avoid Rechecking Subarrays
   *
   * Instead of checking each subarray individually, we calculate:
   * - **f[i]** = rightmost position where a subarray starting at i can end
   * validly
   *
   * ## Example with f[i]:
   * ```
   * n = 5, conflictingPairs = [[2,4]]
   * nums = [1, 2, 3, 4, 5]
   *
   * f[1] = 3 (subarray [1,2,3] is valid, [1,2,3,4] is not)
   * f[2] = 3 (subarray [2,3] is valid, [2,3,4] is not)
   * f[3] = 5 (subarray [3,4,5] is valid)
   * f[4] = 5 (subarray [4,5] is valid)
   * f[5] = 5 (subarray [5] is valid)
   * ```
   *
   * ## Counting Valid Subarrays:
   * From position i, valid subarrays are: [i], [i,i+1], ..., [i,f[i]]
   * Count = f[i] - i + 1
   **
   * Total valid subarrays = Σ(f[i] - i + 1) for i = 1 to n**
   *
   * ## Time Complexity with f[i]:
   * ```
   * for each pair removal (k times):
   * calculate f[i] for all positions (O(nk) with optimizations)
   * sum up the counts (O(n))
   * ```
   ** Total: O(k²n) instead of O(k²n²)** - much better!
   *
   * ---
   *
   * # Concrete Example
   *
   * Let's trace f[i] calculation:
   *
   * ## Setup:
   * - n = 6, conflictingPairs = [[2,5], [3,4]]
   * - Remove pair [2,5], remaining: [[3,4]]
   *
   * ## Calculate f[i]:
   * ```
   * f[1]: Start at 1, can we include 2? Yes. Include 3? Yes. Include 4?
   * This would include both 3 and 4 from pair [3,4] - STOP at 3
   * f[1] = 3
   *
   * f[2]: Start at 2, can we include 3? Yes. Include 4?
   * This would include both 3 and 4 from pair [3,4] - STOP at 3
   * f[2] = 3
   *
   * f[3]: Start at 3, can we include 4?
   * This would include both 3 and 4 from pair [3,4] - STOP at 3
   * f[3] = 3
   *
   * f[4]: Start at 4, can we include 5? Yes. Include 6? Yes.
   * f[4] = 6
   *
   * f[5]: Start at 5, can we include 6? Yes.
   * f[5] = 6
   *
   * f[6]: Start at 6, end of array.
   * f[6] = 6
   * ```
   *
   * ## Count valid subarrays:
   * - From position 1: f[1]-1+1 = 3-1+1 = 3 subarrays: [1], [1,2], [1,2,3]
   * - From position 2: f[2]-2+1 = 3-2+1 = 2 subarrays: [2], [2,3]
   * - From position 3: f[3]-3+1 = 3-3+1 = 1 subarray: [3]
   * - From position 4: f[4]-4+1 = 6-4+1 = 3 subarrays: [4], [4,5], [4,5,6]
   * - From position 5: f[5]-5+1 = 6-5+1 = 2 subarrays: [5], [5,6]
   * - From position 6: f[6]-6+1 = 6-6+1 = 1 subarray: [6]
   **
   * Total: 3+2+1+3+2+1 = 12 valid subarrays**
   *
   * This is MUCH faster than checking all (6×7/2) = 21 possible subarrays
   * individually!
   */


  class Solution_withoutSegementTree {

    public long maxSubarrays(int n, int[][] conflictingPairs) {
      long maxCount = 0;
      // we remvoe each conflictiong pair
      for (int rI = 0; rI < conflictingPairs.length; rI++)
        maxCount = Math.max(maxCount, countValidSubarrays(n, conflictingPairs, rI));

      return maxCount;
    }

    private long countValidSubarrays(int n, int[][] conflictingPairs, int skipIndex) {
      int[] f = new int[n + 1]; // f[i] = rightmost valid ending position for start i

      // Initialize f[i] to maximum possible (no conflicts)
      for (int i = 1; i <= n; i++)
        f[i] = n;

      // Apply constraints from remaining conflicting pairs
      for (int i = 0; i < conflictingPairs.length; i++) {
        int a = conflictingPairs[i][0], b = conflictingPairs[i][1];
        if (i == skipIndex)
          continue; // Skip the removed pair

        // Ensure a <= b for easier processing
        if (a > b) {
          int temp = a;
          a = b;
          b = temp;
        }

        // For any subarray that contains both a and b, it's invalid
        // So for starting positions 1 to a, we can't go beyond b-1
        for (int start = 1; start <= a; start++)
          f[start] = Math.min(f[start], b - 1);
      }

      long totalCount = 0; // Valid Sub count
      for (int j = 1; j <= n; j++)
        if (f[j] >= j)
          totalCount += f[j] - j + 1;

      return totalCount;
    }
  }
/*
# Current TLE Bottleneck Analysis

## Where is the TLE happening NOW?

### Current Time Complexity: O(k² × n)
```java
for (int removeIndex = 0; removeIndex < k; removeIndex++) {        // k times
    for (int pairIndex = 0; pairIndex < k; pairIndex++) {          // k times
        for (int start = 1; start <= a; start++) {                 // n times
            f[start] = Math.min(f[start], b - 1);                  // O(1)
        }
    }
}
```

### The Bottleneck:
**This inner loop is the killer:**
```java
for (int start = 1; start <= a; start++) {
    f[start] = Math.min(f[start], b - 1);
}
```

We're doing **range updates** on f[i] array, but doing it naively in O(n) time per update.

### With typical constraints:
- k = 10⁵ pairs
- n = 10⁵ array size
- Operations = k × k × n = 10⁵ × 10⁵ × 10⁵ = 10¹⁵
- Still too slow! 😭

---

# What Data Structures Can Help?

## 1. **Difference Array** (Simple but effective)
**Good for: Multiple range updates, single final query**

```java
// Instead of updating f[1..a] directly
for (int start = 1; start <= a; start++) {
    f[start] = Math.min(f[start], b - 1);  // O(n) total
}

// Use difference array for updates
diff[1] += (b-1);     // O(1)
diff[a+1] -= (b-1);   // O(1)
// Then build f[i] at the end: O(n) once
```

## 2. **Segment Tree with Lazy Propagation**
**Good for: Multiple range updates + range queries**
- Range update: O(log n)
- Range query: O(log n)
- Perfect for our case!

## 3. **Fenwick Tree (BIT)**
**Good for: Point updates + prefix sum queries**
- Not ideal here since we need range updates

## 4. **Square Root Decomposition**
**Good for: Range updates when segment tree is overkill**
- Range update: O(√n)
- Simpler to implement than segment tree

---

# Why Choose Segment Tree?

## Our specific needs:
1. **Multiple range updates**: We update f[1..a] many times
2. **Need final values**: We need f[i] values to calculate answer
3. **Range minimum updates**: f[start] = min(f[start], b-1)

## Segment Tree advantages:
- ✅ Range updates in O(log n)
- ✅ Supports min/max operations
- ✅ Can handle complex update patterns
- ✅ Query individual values efficiently

## Time complexity improvement:
- **Before**: O(k² × n) = 10¹⁵ operations
- **After**: O(k² × log n) = 10¹⁵ → 10¹² operations (1000x faster!)

---

# Alternative: Difference Array Approach

For this specific problem, we might be able to use a simpler approach:

## Key insight:
Since we only need final f[i] values (not intermediate queries), difference array might work:

```java
// For each conflicting pair [a,b], we want:
// f[1] = min(f[1], b-1)
// f[2] = min(f[2], b-1)
// ...
// f[a] = min(f[a], b-1)

// We can track the minimum constraint for each position
int[] minConstraint = new int[n+1];
Arrays.fill(minConstraint, n); // Initialize to maximum

// For each pair [a,b]:
for (int start = 1; start <= a; start++) {
    minConstraint[start] = Math.min(minConstraint[start], b-1);
}

// Final f[i] = minConstraint[i]
```

**Still O(k²n) though!** 😞

---

# The Real Solution: Smarter Algorithm

## The hints suggest a completely different approach:

1. **Sort pairs by their right endpoint (b values)**
2. **Process pairs in specific order**
3. **Use segment tree for efficient range updates**
4. **Avoid the k² factor entirely**

This changes the algorithm fundamentally, not just the data structure!

## Next steps:
1. Understand WHY sorting by right endpoint helps
2. Learn basic segment tree operations
3. Implement the optimized algorithm
*/
/*
# Segment Tree Fundamentals

## What is a Segment Tree?

A **segment tree** is a binary tree data structure that allows:
- **Range queries** in O(log n)
- **Range updates** in O(log n) (with lazy propagation)

## Visual Example:

Let's say we have array: `[3, 1, 4, 2, 5]` (indices 1-5)

```
                 [1,5] = min(3,1,4,2,5) = 1
                /                        \
        [1,3] = 1                        [4,5] = 2
        /       \                        /       \
   [1,2] = 1   [3,3] = 4          [4,4] = 2   [5,5] = 5
   /       \
[1,1] = 3  [2,2] = 1
```

Each node stores the **minimum value** of its range.

## Key Concepts:

### 1. **Tree Structure**
- **Leaf nodes**: Individual array elements
- **Internal nodes**: Minimum of their children's ranges
- **Root**: Minimum of entire array

### 2. **Node Indexing**
```
Node i has:
- Left child: 2*i
- Right child: 2*i + 1
- Parent: i/2
```

### 3. **Range Representation**
Each node covers range [start, end]:
- Leaf: [i, i] for element i
- Internal: [start, end] where end > start

---

# Lazy Propagation

## The Problem:
Normal segment tree updates are O(log n) for **point updates**, but our problem needs **range updates**.

Naive range update: Update each element → O(n log n) per range update 😞

## The Solution: Lazy Propagation
**Idea**: Don't update children immediately. Mark them as "needs update later" and update only when necessary.

### Example:
```
Update range [2,4] with value 10

Before:
                 [1,5]
                /     \
        [1,3]                [4,5]
        /    \               /    \
   [1,2]    [3,3]      [4,4]    [5,5]

After (with lazy):
                 [1,5]
                /     \
        [1,3]                [4,5]
      (lazy=10)            (lazy=10)
        /    \               /    \
   [1,2]    [3,3]      [4,4]    [5,5]
```

The children aren't updated yet, but marked as "will be updated with 10 later".

---

# For Our Problem

## We need:
1. **Range minimum updates**: `f[l..r] = min(f[l..r], value)`
2. **Point queries**: Get final `f[i]` value

## Segment Tree Operations:
- `updateRange(l, r, value)`: Apply min(current, value) to range [l,r]
- `query(i)`: Get final value at position i

## Why This Helps:
- **Before**: O(k² × n) - update each position individually
- **After**: O(k² × log n) - update ranges efficiently

Time improvement: **n / log n** factor (for n=10⁵, that's ~6000x faster!)
*/

}
