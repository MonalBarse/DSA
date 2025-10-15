package com.monal.DP.DP_Knapsack;

/*
You are given an array books where books[i] = [thicknessi, heighti] indicates
the thickness and height of the ith book. You are also given an integer shelfWidth.

We want to place these books in order onto bookcase shelves that have a total width shelfWidth.

We choose some of the books to place on this shelf such that the sum of their thickness is less than
or equal to shelfWidth, then build another level of the shelf of the bookcase so that the total height
of the bookcase has increased by the maximum height of the books we just put down.
We repeat this process until there are no more books to place.

Note that at each step of the above process, the order of the books we place is the same order as the given sequence of books.

For example, if we have an ordered list of 5 books, we might place the first and second book onto the first shelf, the third book on the second shelf, and the fourth and fifth book on the last shelf.
Return the minimum possible height that the total bookshelf can be after placing shelves in this manner.

- Example 1:
    Input: books = [[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]], shelfWidth = 4
    Output: 6

    Explanation:
    The sum of the heights of the 3 shelves is 1 + 3 + 2 = 6.
    Notice that book number 2 does not have to be on the first shelf.

- Example 2:
    Input: books = [[1,3],[2,4],[3,2]], shelfWidth = 6
    Output: 4

*/
public class P010 {

    class Solution {
        public int minHeightShelves(int[][] books, int shelfWidth) {
            int n = books.length;
            // dp[i] = minimum height needed to place first i books
            int[] dp = new int[n + 1];
            dp[0] = 0; // No books, no height needed

            for (int i = 1; i <= n; i++) {
                dp[i] = Integer.MAX_VALUE;

                // Try placing books ending at position i-1 on the same shelf
                int currentShelfWidth = 0;
                int currentShelfMaxHeight = 0;

                // Go backwards from current book to see how many we can fit on same shelf
                for (int j = i - 1; j >= 0; j--) {
                    int bookThickness = books[j][0];
                    int bookHeight = books[j][1];

                    // Check if we can add this book to current shelf
                    if (currentShelfWidth + bookThickness > shelfWidth) {
                        break; // Can't fit more books on this shelf
                    }

                    // Add this book to current shelf
                    currentShelfWidth += bookThickness;
                    currentShelfMaxHeight = Math.max(currentShelfMaxHeight, bookHeight);

                    // Update minimum height: previous shelves + current shelf height
                    dp[i] = Math.min(dp[i], dp[j] + currentShelfMaxHeight);
                }
            }

            return dp[n];
        }
    }

    // Approach 2: Recursive with memoization (cleaner version of your code)
    class SolutionRecursive {
        private int[][] books;
        private int m;
        private Integer[] memo;

        public int minHeightShelves(int[][] books, int shelfWidth) {
            this.books = books;
            this.m = shelfWidth;
            this.memo = new Integer[books.length];
            return findMinHeight(0);
        }

        private int findMinHeight(int book_no) {
            // Base case: no more books to place
            if (book_no >= books.length) return 0;
            if (memo[book_no] != null) return memo[book_no];

            int minHeight = Integer.MAX_VALUE;
            int curr_wid = 0;
            int curr_max_height = 0;

            // Try placing books starting from bookIndex on the same shelf
            for (int i = book_no; i < books.length; i++) {
                int thickness = books[i][0];
                int height = books[i][1];

                // Check if current book fits on the shelf
                if (curr_wid + thickness > m) break;
                // Place current book on shelf
                curr_wid += thickness;
                curr_max_height = Math.max(curr_max_height, height);

                // Calculate total height: current shelf + remaining books
                int totalHeight = curr_max_height + findMinHeight(i + 1);
                minHeight = Math.min(minHeight, totalHeight);
            }

            return memo[book_no] = minHeight;
        }
    }

}
