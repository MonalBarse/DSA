package com.monal.Heaps;

import java.util.*;

public class P005 {

  // Tweet class to store tweetId and timestamp
  class Tweet {
    int tweetId;
    int time;

    public Tweet(int tweetId, int time) {
      this.tweetId = tweetId;
      this.time = time;
    }
  }

  // Twitter class to manage tweets and user relationships
  class Twitter {
    private int timestamp; // keep track - of order
    private Map<Integer, Set<Integer>> follows; // userId -> set of followees
    private Map<Integer, List<Tweet>> tweetsByUser; // userId -> list of tweets

    public Twitter() {
      timestamp = 0;
      follows = new HashMap<>();
      tweetsByUser = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
      tweetsByUser.putIfAbsent(userId, new ArrayList<>());
      tweetsByUser.get(userId).add(new Tweet(tweetId, timestamp++));
    }

    public List<Integer> getNewsFeed(int userId) {
      PriorityQueue<Tweet> maxHeap = new PriorityQueue<>((a, b) -> b.time - a.time);
      Set<Integer> followees = follows.getOrDefault(userId, new HashSet<>());
      followees.add(userId); // Always see own tweets

      for (int uid : followees) {
        List<Tweet> tweets = tweetsByUser.getOrDefault(uid, new ArrayList<>());
        for (Tweet tweet : tweets)
          maxHeap.offer(tweet);
      }
      List<Integer> result = new ArrayList<>();
      int count = 0;
      while (!maxHeap.isEmpty() && count < 10) {
        result.add(maxHeap.poll().tweetId);
        count++;
      }

      return result;
    }

    public void follow(int followerId, int followeeId) {
      follows.putIfAbsent(followerId, new HashSet<>());
      follows.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
      if (follows.containsKey(followerId)) {
        // Cannot unfollow self (optional but common rule)
        if (followerId != followeeId)
          follows.get(followerId).remove(followeeId);
      }
    }

  }

  /**
   * Your Twitter object will be instantiated and called as such:
   * Twitter obj = new Twitter();
   * obj.postTweet(userId,tweetId);
   * List<Integer> param_2 = obj.getNewsFeed(userId);
   * obj.follow(followerId,followeeId);
   * obj.unfollow(followerId,followeeId);
   */

  public static void main(String[] args) {
    P005.Twitter twitter = new P005().new Twitter();
    twitter.postTweet(1, 5);
    System.out.println(twitter.getNewsFeed(1)); // Should return [5]
    twitter.follow(1, 2);
    twitter.postTweet(2, 6);
    System.out.println(twitter.getNewsFeed(1)); // Should return [6, 5]
    twitter.unfollow(1, 2);
    System.out.println(twitter.getNewsFeed(1)); // Should return [5]
    twitter.postTweet(2, 7);
    System.out.println(twitter.getNewsFeed(2)); // Should return [7, 6]
    twitter.follow(2, 1);
    System.out.println(twitter.getNewsFeed(2)); // Should return [5, 7, 6]
    twitter.unfollow(2, 1);
    System.out.println(twitter.getNewsFeed(2)); // Should return [7, 6]
  }

}
