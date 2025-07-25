package com.monal.Graphs.MST;

import java.util.*;

/*
 * 1258. Synonymous Sentences
 * You are given a list of equivalent words synonyms and a sentence text.
 * Return all possible synonymous sentences sorted lexicographically.
 * The list of synonyms is not necessarily transitive, for example,
 *  if "happy" is a synonym of "joyful", and "joyful" is a synonym of "cheerful",
 * then "happy" is not a synonym of "cheerful".
 * The list of synonyms is also not necessarily reflexive,
 * meaning that a word is not necessarily a synonym of itself.
 * The list of synonyms is not necessarily symmetric,
 * meaning that if "happy" is a synonym of "joyful", then "joyful" is not necessarily a synonym of "happy".
 * The list of synonyms is not necessarily complete, meaning that a word may not have any synonyms at all.

 * Example 1:
 * Input: synonyms = [["happy","joy"],["sad","sorrow"],["joy","cheerful"]], text = "I am happy today but was sad yesterday"
 * Output: ["I am cheerful today but was sad yesterday","I am cheerful today but was sorrow yesterday","I am happy today but was sad yesterday","I am happy today but was sorrow yesterday","I am joy today but was sad yesterday","I am joy today but was sorrow yesterday"]
 *
 * Example 2:
 * Input: synonyms = [["happy","joy"],["cheerful","glad"]], text = "I am happy today but was sad yesterday"
 * Output: ["I am happy today but was sad yesterday","I am joy today but was sad yesterday"]
 */

public class P009 {

  public List<String> generateSynonymSentences(List<List<String>> synonyms, String text) {
    // Map words to unique IDs
    Map<String, Integer> wordToId = new HashMap<>(); // word -> id
    int id = 0;
    for (List<String> pair : synonyms) for (String word : pair) wordToId.putIfAbsent(word, id++);

    DisjointSet djs = new DisjointSet(id);
    for (List<String> pair : synonyms) djs.union(wordToId.get(pair.get(0)), wordToId.get(pair.get(1)));

    // Group words by their root
    Map<Integer, Set<String>> groups = new HashMap<>();
    for (Map.Entry<String, Integer> key : wordToId.entrySet()) {
      int root = djs.find(key.getValue());
      groups.computeIfAbsent(root, k -> new TreeSet<>()).add(key.getKey());
    }

    String[] words = text.split(" ");
    List<String> result = new ArrayList<>();
    generate(0, words, wordToId, groups, djs, new StringBuilder(), result);
    return result;
  }

  private void generate(int idx, String[] sentence, Map<String, Integer> wordToId,
      Map<Integer, Set<String>> groups, DisjointSet dsu,
      StringBuilder current, List<String> result) {
    if (idx == sentence.length) {
      result.add(current.toString().trim());
      return;
    }

    String word = sentence[idx];
    Set<String> synonyms = wordToId.containsKey(word) ? groups.get(dsu.find(wordToId.get(word))) : Set.of(word);

    for (String synonym : synonyms) {
      int len = current.length();
      current.append(synonym).append(" ");
      generate(idx + 1, sentence, wordToId, groups, dsu, current, result);
      current.setLength(len);
    }
  }

  class DisjointSet {
    int[] parent, rank;

    DisjointSet(int n) {
      parent = new int[n];
      rank = new int[n];
      for (int i = 0; i < n; i++)
        parent[i] = i;
    }

    int find(int x) {
      return parent[x] == x ? x : (parent[x] = find(parent[x]));
    }

    void union(int x, int y) {
      int px = find(x), py = find(y);
      if (px == py)
        return;

      if (rank[px] < rank[py])
        parent[px] = py;
      else if (rank[px] > rank[py])
        parent[py] = px;
      else {
        parent[py] = px;
        rank[px]++;
      }
    }
  }
}