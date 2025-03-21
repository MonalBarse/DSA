package com.monal.contestOne;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class A_FizzBuzz_Remixed {
    public static void main(String[] args) throws IOException {
        BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder str = new StringBuilder();

        int t = Integer.parseInt(b.readLine());

        while (t-- > 0) {
            int n = Integer.parseInt(b.readLine());

            int fullBlocks = n / 15;
            int remainder = n % 15;

            // crucial part - if remainder is 0, then we need to add 1 to the fullBlocks
            int extra = Math.min(3, remainder + 1);

            str.append(fullBlocks * 3 + extra).append("\n");
        }

        System.out.print(str);
    }
}
