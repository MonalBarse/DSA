package com.monal;

public class BitManipulation {

  public static void main(String[] args) {
    System.out.println(findMagicNumber(3)); // 30
  }

  // -------------------------------------------------------------------- //
  public static int findMagicNumber(int n) {
    // A nth magic number is a number which can be represented as the sum of powers of 5;
    // 1st magic number = 1 in binary is 1 so , Math.pow(5, 1) = 5
    // 2nd magic number = 2 in binary is 10 so, Math.pow(5, 2) + Math.pow(0,1)= 25
    // 3rd magic number = 3 in binary is 11 so, Math.pow(5, 2) + Math.pow(5,1) = 30 .... and so on
    // we can solve this problem using bitwise operators

    int pow = 1, answer = 0;
    while (n > 0) {
      pow = pow * 5;
      if ((n & 1) == 1) {
        answer += pow;
      }
      n = n >> 1; // We cannot obtain length or loop through a no. so we shift the bits to the
      // right and get last bit of the number
    }
    return answer;
  }

  // -------------------------------------------------------------------- //
  public static int oneOutOfThreeRepeating(int[] arr) {
    /* An array has all elements repeated thrice except for one element which is repeated once find that element */
    // We can solve this problem using XOR operator

    int ones = 0, twos = 0;
    int common_bit_mask;
    for (int i = 0; i < arr.length; i++) {
      twos = twos | (ones & arr[i]);
      ones = ones ^ arr[i];
      common_bit_mask = ~(ones & twos);
      ones &= common_bit_mask;
      twos &= common_bit_mask;
    }
    return ones;
    /* int[] arr = {2, 2, 1, 3, 4, 5, 6, 4, 3, 5, 1, 2, 1, 3, 4, 5};
    system.out.println(finduniqueelement(arr)); // 6 */

  }

  // -------------------------------------------------------------------- //
  public static int finduniqueelement(int[] arr) {
    // array to store the count of bits
    int[] bitcounts = new int[32];

    // count the bits for each position
    for (int num : arr) {
      for (int j = 0; j < 32; j++) {
        // increment bit count for the j-th bit
        bitcounts[j] = bitcounts[j] + ((num >> j) & 1);
        // num >> j will shift the bit to the right by j positions and & 1 will give the last bit
      }
    }

    // construct the unique number
    int result = 0;
    for (int i = 0; i < 32; i++) {
      // if bit count is not a multiple of 3, set the bit in the result
      if (bitcounts[i] % 3 != 0) {
        result = result | (1 << i);
        // 1 << i will shift the bit to the left by i positions and | will set the the bit (if it is
        // 1) in the result
      }
    }

    return result;
    /*
     arr --> [3,3,1,2,3,2,2]   3 --> 011 , 1 --> 001 , 2 --> 010 and the bitcounts will look like

    int[] arr = {2, 2, 1, 3, 4, 5, 6, 4, 3, 5, 1, 2, 1, 3, 4, 5};
    System.out.println(findUniqueElement(arr)); // 6
     */
  }

  // -------------------------------------------------------------------- //
  public static int rightsetbit(int num) {
    // given a numberm find the postioon of the rightmost setbit
    int positionfromright = 1;
    while (iseven(num)) {
      num = num >> 1;
      positionfromright++;
    }
    return positionfromright;
    /*
     * int ans = rightsetbit(43); // 43 --> 101011 --> 1
     * int ans2 = rightsetbit(32); // 32 --> 100000 --> 6
     * system.out.println(ans2);
     */
  }

  // -------------------------------------------------------------------- //
  public static int ithbit(int num, int position) {
    // given an  number find it's i'th bit
    // we can create a mask and do & operation with it
    // so let's say if we have been given a number 54 and we want to find its 3rd bit (from right)
    // 54 = 110110  --> 3rd bit from right is 1
    // 1 << 3 = 1000 (mask) --> 54 & 1000 = 1000 (8) --> 8 is the 3rd bit from right
    // if we want to display its both bit and value, we can use the following code

    int mask = 1 << position;
    int n = num & mask;
    int binary = n >> position; // to get the actual bit value
    return binary;

    /*  system.out.println(ithbit(43, 2)); // 0
     *  system.out.println(ithbit(42, 3)); // 1
     *  system.out.println(ithbit(54, 3)); // 1
     *  system.out.println(ithbit(54, 2)); // 0 (54--> 110110)
     */
  }

  // -------------------------------------------------------------------- //
  public static int uniqueelement(int[] arr) {
    // given an array of integers, every element appears twice except for one. find that single one
    // eg [2,2,1] --> 1 , [4,1,2,1,2] --> 4
    // we can solve this problem using xor operator since a xor a = 0 and a xor 0 = a
    int result = 0;

    for (int elem : arr) {
      result ^= elem;
    }
    return result;

    /*
     * int arr[] = {2, 2, 1, 3, 4, 5, 6, 4, 3, 5, 1};
     * system.out.println(uniqueelement(arr)); // 6
     */
  }

  // -------------------------------------------------------------------- //
  public static void bitwiseoperator() {
    // bitwise operators
    // note: negative numbers are stored in 2's complement form in memory --> 2's complement of a
    // number is the bitwise not of the number + 1
    // eg -5 = 1111 1011 (1's complement of 5) + 1 = 1111 1100 , eg2 -10 = 1111 0110 (1's complement
    // of 10) + 1 = 1111 0111
    // and operator
    System.out.println(5 & 6); // 4 (101 & 110 = 100)
    // or operator
    System.out.println(5 | 6); // 7 (101 | 110 = 111)
    ;
    // xor operator
    // xor operator returns 1 if both bits are different, otherwise it returns 0
    System.out.println(5 ^ 6); // 3 (101 ^ 110 = 011) --> n ^ 1 = ~n , n ^ 0 = n , n ^ n = 0
    // not operator (~) --> returns the complement of a number by changing 1 to 0 and 0 to 1

  }

  public static int numberOfBinary(int n, int base) {

    // given a decimal number n, find the number of digits it take to represent it in base b (2, 8,
    // 16)
    // we can solve this problem using log base b of n + 1
    // eg 10 in binary = 1010 --> 4 digits , 10 in octal = 12 --> 2 digits
    // 10 in hexadecimal = A --> 1 digit

    return (int) (Math.log(n) / Math.log(base) + 1);
  }

  // -------------------------------------------------------------------- //
  public static boolean iseven(int num) {

    /*  we can use bitwise and to check if a number is even or odd
      if a number is even, its last bit will be 0 and if it is odd, its last bit will be 1
      so, if we do a & 1, we will get 0 for even numbers and 1 for odd numbers
      this is better than using modulo operator because it is faster (bitwise operations are faster than arithmetic operations)
      and it works for negative numbers as well

      /*
        system.out.println(iseven(10)); // true
        system.out.println(iseven(5)); // false
        system.out.println(iseven(-10)); // true
        system.out.println(iseven(-5)); // false
      /
    */
    int lastbit = num & 1;
    if (lastbit == 0) {
      return true;
    } else {
      return false;
    }
  }
}
