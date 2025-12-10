package Companies.Medianet;

/*
You discovered a treasure full of magical gems — but they’re cursed! Each gem will bring you “bad luck” if not destroyed in time. You want to minimize the total bad luck you receive.
There are N gems, and for each gem i, you are given two integers:

B[i][0] = Time (in seconds) for which the gem gives bad luck once started.
B[i][1] = Total amount of bad luck the gem gives if not destroyed before finishing.
The bad luck intensity for a gem is defined as:
badLuck[i] = |B[i][0] - B[i][1]|

Rules:
You can destroy gems, one per second, in any order.
When a gem starts giving bad luck, it cannot be destroyed. You must endure its full bad luck for the full duration B[i][0].
While receiving bad luck from one gem, you can simultaneously destroy other gems at the rate of 1 gem per second.
After one gem finishes giving bad luck, another must start immediately, if any gems remain.
No overlap of bad luck from two gems is allowed.
Your goal: maximize the bad luck you avoid (i.e., destroy gems strategically so you never have to endure bad luck from them).
Output
  Return the maximum amount of bad luck you can avoid.

Example:

Input:
  B = [[2, 5], [1, 2], [3, 1]]j


 */
public class P001 {

  public class Solution {
    public int minIntensity(int A, int[][] B, int[] C, int D, int E) {
      List<List<Integer>> graph = new ArrayList<>();
      for (int i = 0; i <= A; i++) graph.add(new ArrayList<>());
      for (int[] edge : B) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
      }

      Set<Integer> speakers = new HashSet<>();
      for (int c : C) speakers.add(c);

      if (D == E) return 0;

      int left = 0, right = A, ans = -1;

      while (left <= right) {
        int mid = (left + right) / 2;
        if (canReach(graph, speakers, D, E, mid)) {
          ans = mid;
          right = mid - 1;
        } else {
          left = mid + 1;
        }
      }

      return ans;
    }

    private boolean canReach(List<List<Integer>> graph, Set<Integer> speakers, int D, int E, int x) {
      Queue<int[]> q = new LinkedList<>();
      boolean[] visited = new boolean[graph.size()];

      // [city, distance from origin speaker]
      q.offer(new int[] { D, 0 });
      visited[D] = true;

      while (!q.isEmpty()) {
        int[] curr = q.poll();
        int city = curr[0], dist = curr[1];

        if (city == E)
          return true;

        // Activate new speakers
        if (speakers.contains(city) && dist <= x) {
          dist = 0; // reset distance because new speaker starts here
        }

        for (int nei : graph.get(city)) {
          if (!visited[nei] && dist + 1 <= x) {
            visited[nei] = true;
            q.offer(new int[] { nei, dist + 1 });
          }
        }
      }

      return false;
    }

    public static void main(String[] args) {
      Solution sol = new Solution();

      int A = 6;
      int[][] B = { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 }, { 5, 6 } };
      int[] C = { 1, 3, 5 };
      int D = 1, E = 6;

      System.out.println(sol.minIntensity(A, B, C, D, E)); // 2
    }
  }

}
