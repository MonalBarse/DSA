package com.monal.Graphs.MST;

import java.util.*;

/*
Given a list of `accounts` where each element `accounts[i]` is a list of strings, where the first element `accounts[i][0]` is a name, and the rest of the elements are **emails** representing emails of the account.
Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.
After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails **in sorted order**. The accounts themselves can be returned in **any order**.
 
**Example 1:**
  Input: accounts = [["John","johnsmith@mail.com","john_newyork@mail.com"],["John","johnsmith@mail.com","john00@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
  Output: [["John","john00@mail.com","john_newyork@mail.com","johnsmith@mail.com"],["Mary","mary@mail.com"],["John","johnnybravo@mail.com"]]
  Explanation:
  The first and second John's are the same person as they have the common email "johnsmith@mail.com".
  The third John and Mary are different people as none of their email addresses are used by other accounts.
  We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'],
  ['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.

**Example 2:**

Input: accounts = [["Gabe","Gabe0@m.co","Gabe3@m.co","Gabe1@m.co"],["Kevin","Kevin3@m.co","Kevin5@m.co","Kevin0@m.co"],["Ethan","Ethan5@m.co","Ethan4@m.co","Ethan0@m.co"],["Hanzo","Hanzo3@m.co","Hanzo1@m.co","Hanzo0@m.co"],["Fern","Fern5@m.co","Fern1@m.co","Fern0@m.co"]]
Output: [["Ethan","Ethan0@m.co","Ethan4@m.co","Ethan5@m.co"],["Gabe","Gabe0@m.co","Gabe1@m.co","Gabe3@m.co"],["Hanzo","Hanzo0@m.co","Hanzo1@m.co","Hanzo3@m.co"],["Kevin","Kevin0@m.co","Kevin3@m.co","Kevin5@m.co"],["Fern","Fern0@m.co","Fern1@m.co","Fern5@m.co"]]

 */
public class P003 {
  /*
   * What to union: We need to group accounts that share emails
   * How to identify connections: If two accounts have any email in common, they
   * belong to the same person
   * What to track: We need to map emails to account indices to find connections
   *
   * Approach:
   * -  Build email-to-account mapping: For each email, track which accounts contains
   * - Union accounts: If an email appears in multiple accounts, union those acc
   * - Group results: Collect all emails for each connected component
   * - Format output: Sort emails and add the name
   */
  class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
      int n = accounts.size();
      UnionFind uf = new UnionFind(n);

      // Map email to the first account index that contains it
      Map<String, Integer> emailToIndex = new HashMap<>();

      // Build the email to account index mapping and union accounts
      for (int i = 0; i < n; i++) {
        List<String> account = accounts.get(i);
        // Skip the name (index 0), process emails from index 1
        for (int j = 1; j < account.size(); j++) {
          String email = account.get(j);
          if (emailToIndex.containsKey(email)) {
            // Email already seen, union current account with the account that has this
            // email
            uf.union(i, emailToIndex.get(email));
          } else {
            // First time seeing this email, map it to current account
            emailToIndex.put(email, i);
          }
        }
      }

      // Group emails by their root parent (connected component)
      Map<Integer, Set<String>> componentToEmails = new HashMap<>();

      for (int i = 0; i < n; i++) {
        int root = uf.find(i);
        componentToEmails.putIfAbsent(root, new TreeSet<>()); // TreeSet for automatic sorting

        // Add all emails from this account to the component
        List<String> account = accounts.get(i);
        for (int j = 1; j < account.size(); j++) {
          componentToEmails.get(root).add(account.get(j));
        }
      }

      // Build the result
      List<List<String>> result = new ArrayList<>();

      for (Map.Entry<Integer, Set<String>> entry : componentToEmails.entrySet()) {
        int accountIndex = entry.getKey();
        Set<String> emails = entry.getValue();

        List<String> mergedAccount = new ArrayList<>();
        mergedAccount.add(accounts.get(accountIndex).get(0)); // Add name
        mergedAccount.addAll(emails); // Add sorted emails

        result.add(mergedAccount);
      }

      return result;
    }
  }

  class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int n) {
      parent = new int[n];
      rank = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        rank[i] = 0;
      }
    }

    public int find(int x) {
      if (parent[x] != x) {
        parent[x] = find(parent[x]); // Path compression
      }
      return parent[x];
    }

    public void union(int x, int y) {
      int rootX = find(x);
      int rootY = find(y);

      if (rootX != rootY) {
        if (rank[rootX] < rank[rootY]) {
          parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
          parent[rootY] = rootX;
        } else {
          parent[rootY] = rootX;
          rank[rootX]++;
        }
      }
    }
  }
}
