package com.monal.Recursion;

import java.util.ArrayList;
import java.util.List;

/*
    Given an integer N , Print all binary strings of size N which do not contain consecutive 1s.
    A binary string is that string which contains only 0 and 1.
    Example 1:
        Input:
        N = 3
        Output:
        000 , 001 , 010 , 100 , 101
    Explanation:
    None of the above strings contain consecutive 1s. "110" is not an answer as it has '1's occuring consecutively.

    Expected Time Complexity: O(2N)
    Expected Auxiliary Space: O(N)
*/
public class P001 {

    public List<String> printBinaryStrings(int N) {

        List<String> result = new ArrayList<>();
        // Decided to use 'lastChar' to avoid using a counter for 1's and 0's
        generateStrings(N, "", result, '0'); // Start with an empty string and a last character as '0'
        return result;
    }

    private void generateStrings(int N, String str, List<String> res, char lastChar) {
        if (str.length() == N) {
            res.add(str);
            return;
        }

        // generate string by appending '0' to the current string
        generateStrings(N, str + '0', res, '0');

        // generate string by appending '1' to the current string only if the last
        // character is '0'
        if (lastChar == '0') {
            generateStrings(N, str + '1', res, '1');
        }

    }

    public static void main(String[] args) {
        P001 p001 = new P001();
        System.out.println(p001.printBinaryStrings(3));
    }

}