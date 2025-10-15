package com.monal.DP.DP_Partition;

/*
MCM


*/
public class P001 {
  class Solution {
    public int minimumSum(int[][] grid) {
      int n = grid.length, m = grid[0].length;
      int result = Integer.MAX_VALUE;

      // Try all possible partitions

      // 1. Three horizontal strips (two horizontal cuts)
      for (int cut1 = 1; cut1 < n; cut1++) {
        for (int cut2 = cut1 + 1; cut2 < n; cut2++) {
          int area1 = getAreaForRegion(grid, 0, cut1 - 1, 0, m - 1);
          int area2 = getAreaForRegion(grid, cut1, cut2 - 1, 0, m - 1);
          int area3 = getAreaForRegion(grid, cut2, n - 1, 0, m - 1);

          if (area1 > 0 && area2 > 0 && area3 > 0) {
            result = Math.min(result, area1 + area2 + area3);
          }
        }
      }

      // 2. Three vertical strips (two vertical cuts)
      for (int cut1 = 1; cut1 < m; cut1++) {
        for (int cut2 = cut1 + 1; cut2 < m; cut2++) {
          int area1 = getAreaForRegion(grid, 0, n - 1, 0, cut1 - 1);
          int area2 = getAreaForRegion(grid, 0, n - 1, cut1, cut2 - 1);
          int area3 = getAreaForRegion(grid, 0, n - 1, cut2, m - 1);

          if (area1 > 0 && area2 > 0 && area3 > 0) {
            result = Math.min(result, area1 + area2 + area3);
          }
        }
      }

      // 3. One horizontal cut + one vertical cut (6 different arrangements)

      // Top-left rectangle + two bottom rectangles
      for (int hCut = 1; hCut < n; hCut++) {
        for (int vCut = 1; vCut < m; vCut++) {
          int area1 = getAreaForRegion(grid, 0, hCut - 1, 0, m - 1);
          int area2 = getAreaForRegion(grid, hCut, n - 1, 0, vCut - 1);
          int area3 = getAreaForRegion(grid, hCut, n - 1, vCut, m - 1);

          if (area1 > 0 && area2 > 0 && area3 > 0) {
            result = Math.min(result, area1 + area2 + area3);
          }
        }
      }

      // Bottom-left rectangle + two top rectangles
      for (int hCut = 1; hCut < n; hCut++) {
        for (int vCut = 1; vCut < m; vCut++) {
          int area1 = getAreaForRegion(grid, 0, hCut - 1, 0, vCut - 1);
          int area2 = getAreaForRegion(grid, 0, hCut - 1, vCut, m - 1);
          int area3 = getAreaForRegion(grid, hCut, n - 1, 0, m - 1);

          if (area1 > 0 && area2 > 0 && area3 > 0) {
            result = Math.min(result, area1 + area2 + area3);
          }
        }
      }

      // Top-right rectangle + two bottom rectangles
      for (int hCut = 1; hCut < n; hCut++) {
        for (int vCut = 1; vCut < m; vCut++) {
          int area1 = getAreaForRegion(grid, 0, hCut - 1, 0, m - 1);
          int area2 = getAreaForRegion(grid, hCut, n - 1, 0, vCut - 1);
          int area3 = getAreaForRegion(grid, hCut, n - 1, vCut, m - 1);

          if (area1 > 0 && area2 > 0 && area3 > 0) {
            result = Math.min(result, area1 + area2 + area3);
          }
        }
      }

      // Bottom-right rectangle + two top rectangles
      for (int hCut = 1; hCut < n; hCut++) {
        for (int vCut = 1; vCut < m; vCut++) {
          int area1 = getAreaForRegion(grid, 0, hCut - 1, 0, vCut - 1);
          int area2 = getAreaForRegion(grid, 0, hCut - 1, vCut, m - 1);
          int area3 = getAreaForRegion(grid, hCut, n - 1, 0, m - 1);

          if (area1 > 0 && area2 > 0 && area3 > 0) {
            result = Math.min(result, area1 + area2 + area3);
          }
        }
      }

      // Left rectangle + two right rectangles (split vertically then horizontally)
      for (int vCut = 1; vCut < m; vCut++) {
        for (int hCut = 1; hCut < n; hCut++) {
          int area1 = getAreaForRegion(grid, 0, n - 1, 0, vCut - 1);
          int area2 = getAreaForRegion(grid, 0, hCut - 1, vCut, m - 1);
          int area3 = getAreaForRegion(grid, hCut, n - 1, vCut, m - 1);

          if (area1 > 0 && area2 > 0 && area3 > 0) {
            result = Math.min(result, area1 + area2 + area3);
          }
        }
      }

      // Right rectangle + two left rectangles (split vertically then horizontally)
      for (int vCut = 1; vCut < m; vCut++) {
        for (int hCut = 1; hCut < n; hCut++) {
          int area1 = getAreaForRegion(grid, 0, hCut - 1, 0, vCut - 1);
          int area2 = getAreaForRegion(grid, hCut, n - 1, 0, vCut - 1);
          int area3 = getAreaForRegion(grid, 0, n - 1, vCut, m - 1);

          if (area1 > 0 && area2 > 0 && area3 > 0) {
            result = Math.min(result, area1 + area2 + area3);
          }
        }
      }

      return result;
    }

    private int getAreaForRegion(int[][] grid, int r1, int r2, int c1, int c2) {
      int minRow = Integer.MAX_VALUE, maxRow = Integer.MIN_VALUE;
      int minCol = Integer.MAX_VALUE, maxCol = Integer.MIN_VALUE;
      boolean hasOne = false;

      for (int i = r1; i <= r2; i++) {
        for (int j = c1; j <= c2; j++) {
          if (grid[i][j] == 1) {
            hasOne = true;
            minRow = Math.min(minRow, i);
            maxRow = Math.max(maxRow, i);
            minCol = Math.min(minCol, j);
            maxCol = Math.max(maxCol, j);
          }
        }
      }

      if (!hasOne) return 0; // No 1s in this region
      return (maxRow - minRow + 1) * (maxCol - minCol + 1);
    }
  }
}