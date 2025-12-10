package com.monal;

import java.util.*;

public static void main(String[] args){

  // Sieve of Eratosthenes - it gives all prime numbers up to n

  private List<Integer> sieve(int n){
    List<Integer> primes = new ArrayList<>();
    boolean[] isPrime = new boolean[n + 1];
    Arrays.fill(isPrime, true);
    if(n < 2) return primes;

    isPrime[0] = isPrime[1] = false;

    for(int i = 2; i * i <= n; i++){
      if(isPrime[i]){
        for(int j = i * i; j <= n; j += i){
          isPrime[j] = false;
        }
      }
    }

    // Collecting all prime numbers
    for(int i = 2; i <= n; i++) if(isPrime[i]) primes.add(i);  
    return primes;

  }
}
