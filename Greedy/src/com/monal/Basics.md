# Greedy Problem Recognition Guide

## 🚨 RED FLAGS: "This is Greedy!"

### Pattern 1: **Sorting + Making Choices**

- **Keywords**: "minimum", "maximum", "optimal"
- **Structure**: Sort array, then iterate making local decisions
- **Examples**:
  - Activity Selection (sort by end time)
  - Fractional Knapsack (sort by value/weight ratio)
  - Meeting Rooms (sort by start/end time)

### Pattern 2: **"At Each Step, Pick the Best"**

- **Keywords**: "at each moment", "immediate benefit"
- **Structure**: Priority queue or sorting
- **Examples**:
  - Huffman Coding (pick two smallest frequencies)
  - Dijkstra's Algorithm (pick minimum distance)
  - Prim's MST (pick minimum edge)

### Pattern 3: **Exchange Argument Works**

- **Test**: If you have optimal solution, can you swap elements without making it worse?
- **Examples**:
  - Job Scheduling (swap adjacent jobs by deadline)
  - Fractional Knapsack (can't improve by swapping items)

## ⚠️ WARNING SIGNS: "NOT Greedy!"

### Anti-Pattern 1: **Multiple Constraints**

- 0/1 Knapsack (weight AND value constraints)
- Longest Common Subsequence (two strings to match)
- **Why**: Local choice affects future possibilities

### Anti-Pattern 2: **"All Possible Ways"**

- **Keywords**: "count all", "find all paths", "generate all"
- **Solution**: Usually DP or Backtracking

### Anti-Pattern 3: **Dependencies Between Choices**

- **Example**: Cutting rod (cutting at position X affects future cuts)
- **Test**: If choosing X makes some future choices impossible/suboptimal

## 🧪 The "Greedy Test" Framework

### Step 1: **Identify the Choice**

What decision do you make at each step?

### Step 2: **Prove Optimal Substructure**

If I make the greedy choice, is the remaining problem independent?

### Step 3: **Exchange Argument**

Can you prove that swapping any element in optimal solution with greedy choice doesn't make it worse?

### Step 4: **Counterexample Check**

Try to find a case where greedy fails. If you can't, it's likely greedy!

## 📋 Common Greedy Problem Types

| Problem Type            | Greedy Strategy                     | Key Insight                      |
| ----------------------- | ----------------------------------- | -------------------------------- |
| **Activity Selection**  | Sort by end time                    | Earliest end leaves most room    |
| **Fractional Knapsack** | Sort by value/weight                | Best ratio first                 |
| **Huffman Coding**      | Pick two smallest                   | Frequent chars get shorter codes |
| **Job Scheduling**      | Sort by deadline/penalty            | Handle urgent jobs first         |
| **Gas Station**         | Start from any valid point          | One pass is enough               |
| **Jump Game**           | Always jump to furthest reachable   | Max reach strategy               |
| **Candy Distribution**  | Two passes (left→right, right→left) | Handle constraints separately    |

## 🎯 Decision Tree

```
Can you sort the input meaningfully?
├── Yes → Does best local choice lead to global optimum?
│   ├── Yes → GREEDY! 🎉
│   └── No → Try DP or other approaches
└── No → Is there a clear "best next step"?
    ├── Yes → Might be greedy (test with examples)
    └── No → Likely DP/Backtracking
```

## 💡 Pro Tips

1. **Start with small examples** - If greedy works on all small cases, it might work globally
2. **Look for mathematical properties** - Can you prove the greedy choice is always safe?
3. **Check problem constraints** - Greedy often works when constraints are "nice" (positive weights, no cycles, etc.)
4. **When in doubt, try to break it** - Spend 5 minutes trying to find a counterexample

## 🚫 Common Mistakes

- **Assuming greedy works without proof** - Always verify!
- **Missing the right sorting criteria** - Sometimes you need creative sorting
- **Not considering all constraints** - Make sure greedy choice doesn't violate anything
- **Forgetting edge cases** - Empty arrays, single elements, etc.

---

## The 30-Second Greedy Test

When you see a problem, ask these questions:

1. Can I sort the input in a meaningful way? (90% of greedy problems involve sorting)
2. Does making the "obviously best" choice at each step feel right?
3. Are there multiple conflicting constraints? (If yes, probably NOT greedy)
4. Can I think of a counterexample where greedy fails? (If yes, definitely NOT greedy)

- Real Example - Meeting Rooms
  Problem: Given meeting intervals, find maximum number of non-overlapping meetings.
  Greedy Test:

  - ✅ Can sort by end time
  - ✅ "Pick meeting that ends earliest" feels right
  - ✅ No conflicting constraints
  - ✅ Can't think of counterexample

    Conclusion: GREEDY! ✅

- Counter-Example - 0/1 Knapsack
  Problem: Items with weights and values, maximize value within weight limit.
  Greedy Test:

  - ❌ Sorting by value/weight ratio doesn't always work
  - ❌ Multiple constraints (weight AND value)
  - ❌ Easy counterexample: Items [(60,10), (100,20), (120,30)], capacity=50

  Greedy: Take (60,10) + (100,20) = 160 value
  Optimal: Take (100,20) + (120,30) = 220 value

  Conclusion: NOT GREEDY! Use DP instead.

- Mental Model
  Think of greedy as "being selfish at each step". It works when:

  - Being selfish now doesn't hurt your future options
  - The problem has a natural ordering/priority
  - Local optimization leads to global optimization
