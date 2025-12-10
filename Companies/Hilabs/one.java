import Companies.Hilabs; 

public class one {

  public class Solution {
    private final int MAX = 200;
    private boolean[] sieveOfEratosthenes(int n){
      boolean[] prime = new boolean[n + 1];
      for (int i = 2; i <= n; i++) prime[i] = true;
      for(int i = 2; i * i <= n; i++){
        if(prime[i])
          for(int j = i * i; j <= n; j+= i) prime[j] = false;
      }
      return prime;
    }

    public boolean solve(int[][] stones){
      int prime[] = new int[50];
      boolean isPrime[] = sieveOfEratosthenes(MAX);
      int idx = 0;
      for(int i = 2; i <= MAX; i++){
        if(isPrime[i]) prim]
      }

    }
  }
}
