package CodeForces.src.com.contestOne;

public class dsfgs {
class Solution {
    public int countBinaryPalindromes(long n) {
        if (n == 0) return 1;

        long count = 1;
        int maxBits = 64 - Long.numberOfLeadingZeros(n);

        for (int len = 1; len <= maxBits; len++) {
            count += countPalindromesOfLength(len, n);
        }

        return (int) count;
    }

    private long countPalindromesOfLength(int len, long n) {
        int halfLen = (len + 1) / 2;
        boolean isOdd = (len % 2 == 1);

        long minPrefix = 1L << (halfLen - 1);
        long maxPrefix = (1L << halfLen) - 1;

        long left = minPrefix, right = maxPrefix;
        long validCount = 0;

        while (left <= right) {
            long mid = left + (right - left) / 2;
            long palindrome = constructPalindrome(mid, isOdd);

            if (palindrome <= n) {
                validCount = mid - minPrefix + 1;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return validCount;
    }

    private long constructPalindrome(long prefix, boolean isOdd) {
        long result = prefix;
        long temp = isOdd ? prefix >> 1 : prefix;
        while (temp > 0) {
            result = (result << 1) | (temp & 1);
            temp >>= 1;
        }
        return result;
    }
}
}
