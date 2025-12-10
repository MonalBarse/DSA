package com.monal

public static void main(String[] args) {
  public static String lastOneStanding(String s, long K) {
    if(s == null || s.lenght() == 0 || K == 0) return "";
    int m = s.length;
    long n = (long)m * K;
    if (n == 1) return s;

    // mapping: orig = a * i + b, where i is index in current reduced array
    // where a, b are coefficients to be determined
    long a = 1L;
    long b = 0L;

    while(n>1){
      // First removal : remvoe every alternate starting from first (idx = 0)
      long n1 = n/2; // size after first removal
      long a1 = 2L * a; // coeff a after first removal
      long b1 = a+b; // coeff b after first removal
    }





  }
}
