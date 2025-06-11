# Algorithm Abstraction Guide: BFS vs Dijkstra's

## Core Abstract Components

### 1. **The Queue/Priority Queue (The "Frontier")**

```
What it represents: "Things I still need to explore"
- BFS: Simple queue (FIFO) - explores in breadth-first order
- Dijkstra: Priority queue - always explores the "cheapest" option first
```

- **Adding to queue** = "I found a new path worth exploring"
- **Polling from queue** = "Let me explore the next most promising option"
- **Empty queue** = "I've exhausted all possibilities"

### 2. **The Distance/Cost Array (The "Memory")**

```
What it represents: "Best solution I've found so far to reach each node"
- Initialized to infinity (haven't found any path yet)
- Updated when we find better paths
```

- **cost[node] = value** = "The best way I know to reach 'node' costs 'value'"
- **cost[node] == infinity** = "I haven't found any way to reach this node yet"

### 3. **The Main While Loop (The "Exploration Engine")**

```
Abstract pattern:
while (frontier is not empty) {
    current = get_next_candidate_from_frontier()

    if (should_skip_this_candidate()) continue;

    for each neighbor of current {
        if (found_better_path_to_neighbor()) {
            update_best_known_path_to_neighbor()
            add_neighbor_to_frontier_for_future_exploration()
        }
    }
}
```

- **While Loop Starts** = This is the core exploration loop.
- **At each iteration** = you’re expanding one possible path to look one step further.
- **End of while loop** = you’ve exhausted all ways to explore within k stops.
- **For Loop** = "For each possible next step from the current node"
- **If found_better_path_to_neighbor()** = "I found a way to reach this neighbor that is cheaper/better than before"
- **Skip candidate** = "This path is no longer optimal/valid"
- **Don't skip** = "This path might lead to better solutions"

## Key Differences in Abstract Behavior

### **Dijkstra's Algorithm**

```java
// Abstract pattern:
PriorityQueue<Node> pq = new PriorityQueue<>(by_cost);
pq.offer(start_with_cost_0);

while (!pq.isEmpty()) {
    current = pq.poll(); // Always gets cheapest unprocessed option

    if (already_processed(current)) continue; // Skip if we found better path already
    mark_as_processed(current);

    for (neighbor : current.neighbors) {
        new_cost = current.cost + edge_weight;
        if (new_cost < best_known_cost[neighbor]) {
            best_known_cost[neighbor] = new_cost;
            pq.offer(neighbor_with_new_cost);
        }
    }
}
```

**Abstract behavior:**

- **Greedy exploration**: Always explores the globally cheapest option first
- **Optimal substructure**: Once processed, a node's cost is final (optimal)
- **No revisiting**: Processed nodes are never explored again

### **BFS with Constraints (Flight Problem)**

```java
// Abstract pattern:
Queue<State> q = new ArrayDeque<>();
q.offer(initial_state);

while (!q.isEmpty()) {
    current = q.poll(); // Gets next state in order of discovery

    if (violates_constraints(current)) continue; // Skip invalid states

    for (neighbor : current.neighbors) {
        new_state = extend_current_state_to_neighbor(current);
        if (is_improvement(new_state)) {
            update_best_known(neighbor);
            q.offer(new_state);
        }
    }
}
```

**Abstract behavior:**

- **Breadth-first exploration**: Explores all states at distance k before distance k+1
- **Constraint checking**: Must validate each state against problem constraints
- **State-based**: Tracks more than just cost (e.g., stops, time, etc.)

## Abstract Patterns for Problem Variations

### **When to use which pattern:**

**Use Dijkstra's pattern when:**

- You want the globally optimal solution
- No additional constraints beyond minimizing cost
- Once you reach a node optimally, you never need to reconsider it

**Use BFS pattern when:**

- You have additional constraints (max stops, max time, etc.)
- The "best" path might not be the shortest in one dimension
- You need to track multiple state variables

### **Key Abstract Modifications for Variations:**

#### **1. Changing the State Space**

```
Standard: [cost, node]
With time constraints: [cost, node, time_taken]
With stops constraint: [cost, node, stops_used]
With multiple resources: [cost, node, fuel_left, time_left]
```

#### **2. Changing the Skip Condition**

```
Dijkstra: if (already_processed) skip
Flight problem: if (too_many_stops) skip
Time-constrained: if (time_limit_exceeded) skip
Resource-constrained: if (any_resource_exhausted) skip
Multiple objective: if(objective1 > limit1 OR objective2 > limit2) skip
```

#### **3. Changing the Improvement Check**

```
Standard shortest path: if (new_cost < old_cost)
Multi-objective: if (new_solution dominates old_solution)
With constraints: if (new_cost < old_cost AND constraints_satisfied)
```

## Mental Model for Problem Solving

Think of it as **"Systematic Exploration with Memory"**:

1. **Frontier Management**: "What options do I have to explore next?"
2. **Memory Management**: "What's the best way I know to reach each place?"
3. **Exploration Strategy**: "In what order should I explore my options?"
4. **Constraint Checking**: "Is this path valid according to my rules?"
5. **Improvement Recognition**: "Did I just find a better way?"

When you see a new variation:

1. **Identify the state space**: What do I need to track?
2. **Define the constraints**: What makes a path invalid?
3. **Choose exploration order**: BFS (by steps) or Dijkstra (by cost)?
4. **Define improvement**: When is one path better than another?
