package Bit;

public class BitManipulation {

    // ===============================
    // PART 1: FUNDAMENTAL OPERATIONS
    // ===============================

    public void demonstrateBasicOperations() {
        System.out.println("📚 PART 1: FUNDAMENTAL BIT OPERATIONS");
        System.out.println("=====================================\n");

        int n = 12; // Binary: 1100
        System.out.println("Working with n = " + n + " (binary: " + Integer.toBinaryString(n) + ")");

        // PATTERN 1: Check if bit is set at position i
        System.out.println("\n🔍 PATTERN 1: Check if bit is set");
        for (int i = 0; i < 4; i++) {
            boolean isSet = checkBit(n, i);
            System.out.println("Bit at position " + i + ": " + isSet);
        }

        // PATTERN 2: Set bit at position i
        System.out.println("\n🔧 PATTERN 2: Set bit");
        int afterSet = setBit(n, 1);
        System.out.println("After setting bit 1: " + afterSet + " (binary: " + Integer.toBinaryString(afterSet) + ")");

        // PATTERN 3: Clear bit at position i
        System.out.println("\n🧹 PATTERN 3: Clear bit");
        int afterClear = clearBit(n, 2);
        System.out.println(
                "After clearing bit 2: " + afterClear + " (binary: " + Integer.toBinaryString(afterClear) + ")");

        // PATTERN 4: Toggle bit at position i
        System.out.println("\n🔄 PATTERN 4: Toggle bit");
        int afterToggle = toggleBit(n, 0);
        System.out.println(
                "After toggling bit 0: " + afterToggle + " (binary: " + Integer.toBinaryString(afterToggle) + ")");

        System.out.println("\n" + "=".repeat(50) + "\n");
    }

    // CORE PATTERN TEMPLATES - MEMORIZE THESE!

    /**
     * 🎯 TEMPLATE 1: Check if bit is set at position i
     * PATTERN: (n & (1 << i)) != 0
     * WHY: 1 << i creates mask with only bit i set, & isolates that bit
     */
    public boolean checkBit(int n, int i) {
        return (n & (1 << i)) != 0;
        // Alternative: return ((n >> i) & 1) == 1;
    }

    /**
     * 🎯 TEMPLATE 2: Set bit at position i
     * PATTERN: n | (1 << i)
     * WHY: OR with mask ensures bit i becomes 1, others unchanged
     */
    public int setBit(int n, int i) {
        return n | (1 << i);
    }

    /**
     * 🎯 TEMPLATE 3: Clear bit at position i
     * PATTERN: n & ~(1 << i)
     * WHY: AND with inverted mask makes bit i = 0, others unchanged
     */
    public int clearBit(int n, int i) {
        return n & ~(1 << i);
    }

    /**
     * 🎯 TEMPLATE 4: Toggle bit at position i
     * PATTERN: n ^ (1 << i)
     * WHY: XOR flips the bit - 0^1=1, 1^1=0
     */
    public int toggleBit(int n, int i) {
        return n ^ (1 << i);
    }

    // ===============================
    // PART 2: INTERVIEW PATTERNS
    // ===============================

    public void demonstrateCommonPatterns() {
        System.out.println("🎯 PART 2: COMMON INTERVIEW PATTERNS");
        System.out.println("====================================\n");

        // PATTERN 5: Check if number is power of 2
        System.out.println("🔋 PATTERN 5: Power of 2 check");
        int[] testNumbers = { 8, 12, 16, 15 };
        for (int num : testNumbers) {
            System.out.println(num + " is power of 2: " + isPowerOfTwo(num));
        }

        // PATTERN 6: Count set bits (Brian Kernighan's algorithm)
        System.out.println("\n📊 PATTERN 6: Count set bits");
        for (int num : testNumbers) {
            System.out.println("Set bits in " + num + ": " + countSetBits(num));
        }

        // PATTERN 7: Find rightmost set bit
        System.out.println("\n👉 PATTERN 7: Rightmost set bit");
        for (int num : testNumbers) {
            System.out.println("Rightmost set bit of " + num + ": " + getRightmostSetBit(num));
        }

        // PATTERN 8: Clear rightmost set bit
        System.out.println("\n🧹 PATTERN 8: Clear rightmost set bit");
        for (int num : testNumbers) {
            int cleared = clearRightmostSetBit(num);
            System.out.println(num + " -> " + cleared + " (binary: " +
                    Integer.toBinaryString(num) + " -> " + Integer.toBinaryString(cleared) + ")");
        }

        System.out.println("\n" + "=".repeat(50) + "\n");
    }

    /**
     * 🎯 TEMPLATE 5: Check if power of 2
     * PATTERN: n > 0 && (n & (n-1)) == 0
     * INTUITION: Power of 2 has only one bit set. n-1 flips all bits after and
     * including that bit.
     * EXAMPLE: 8 (1000) & 7 (0111) = 0
     */
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    /**
     * 🎯 TEMPLATE 6: Count set bits (Brian Kernighan's Algorithm)
     * PATTERN: while(n) { count++; n = n & (n-1); }
     * MAGIC: n & (n-1) removes the rightmost set bit!
     * TIME: O(number of set bits) instead of O(32)
     */
    public int countSetBits(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n - 1); // Remove rightmost set bit
        }
        return count;
    }

    /**
     * 🎯 TEMPLATE 7: Get rightmost set bit
     * PATTERN: n & (-n) or n & (~n + 1)
     * MAGIC: Two's complement makes this work!
     * EXAMPLE: 12 (1100) & -12 gives 4 (0100) - the rightmost set bit
     */
    public int getRightmostSetBit(int n) {
        return n & (-n);
    }

    /**
     * 🎯 TEMPLATE 8: Clear rightmost set bit
     * PATTERN: n & (n-1)
     * This is the CORE of Brian Kernighan's algorithm
     */
    public int clearRightmostSetBit(int n) {
        return n & (n - 1);
    }

    // ===============================
    // PART 3: REAL INTERVIEW PROBLEMS
    // ===============================

    public void demonstrateInterviewProblems() {
        System.out.println("💼 PART 3: REAL INTERVIEW PROBLEMS");
        System.out.println("===================================\n");

        // Problem 1: Single Number
        System.out.println("🔍 PROBLEM 1: Single Number (XOR pattern)");
        int[] arr1 = { 2, 2, 1 };
        System.out.println("Array: [2, 2, 1], Single number: " + singleNumber(arr1));

        // Problem 2: Missing Number
        System.out.println("\n🔍 PROBLEM 2: Missing Number");
        int[] arr2 = { 3, 0, 1 };
        System.out.println("Array: [3, 0, 1], Missing number: " + missingNumber(arr2));

        // Problem 3: Number of 1 Bits
        System.out.println("\n🔍 PROBLEM 3: Number of 1 Bits");
        int num = 11;
        System.out.println("Number of 1 bits in " + num + ": " + hammingWeight(num));

        // Problem 4: Reverse Bits
        System.out.println("\n🔍 PROBLEM 4: Reverse Bits");
        int original = 43261596;
        int reversed = reverseBits(original);
        System.out.println("Original: " + Integer.toBinaryString(original));
        System.out.println("Reversed: " + Integer.toBinaryString(reversed));

        // Problem 5: Power of Four
        System.out.println("\n🔍 PROBLEM 5: Power of Four");
        int[] testNums = { 16, 5, 1, 8 };
        for (int n : testNums) {
            System.out.println(n + " is power of four: " + isPowerOfFour(n));
        }

        System.out.println("\n" + "=".repeat(50) + "\n");
    }

    /**
     * 🎯 INTERVIEW PROBLEM 1: Single Number (Leetcode 136)
     * PATTERN: XOR all numbers - duplicates cancel out!
     * KEY INSIGHT: a ^ a = 0, a ^ 0 = a
     * TIME: O(n), SPACE: O(1)
     */
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num; // XOR cancels duplicates
        }
        return result;
    }

    /**
     * 🎯 INTERVIEW PROBLEM 2: Missing Number (Leetcode 268)
     * PATTERN: XOR all indices with all values
     * INTUITION: Missing number will be left after all cancellations
     */
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int result = n; // Start with the largest expected number

        for (int i = 0; i < n; i++) {
            result ^= i ^ nums[i]; // XOR index with value
        }
        return result;
    }

    /**
     * 🎯 INTERVIEW PROBLEM 3: Number of 1 Bits (Leetcode 191)
     * This is just our countSetBits with Brian Kernighan's algorithm
     */
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n &= (n - 1); // Remove rightmost set bit
        }
        return count;
    }

    /**
     * 🎯 INTERVIEW PROBLEM 4: Reverse Bits (Leetcode 190)
     * PATTERN: Process bit by bit, build result from right to left
     */
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result = (result << 1) | (n & 1); // Shift result left, add current bit
            n >>= 1; // Move to next bit
        }
        return result;
    }

    /**
     * 🎯 INTERVIEW PROBLEM 5: Power of Four (Leetcode 342)
     * PATTERN: Must be power of 2 AND set bit must be at even position
     * MAGIC NUMBER: 0x55555555 has bits set at all even positions
     */
    public boolean isPowerOfFour(int n) {
        return n > 0 && (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
    }

    // ===============================
    // PART 4: ADVANCED TRICKS & OPTIMIZATION
    // ===============================

    public void demonstrateAdvancedTricks() {
        System.out.println("🚀 PART 4: ADVANCED TRICKS & OPTIMIZATIONS");
        System.out.println("==========================================\n");

        // Trick 1: Swap without temporary variable
        System.out.println("🔄 TRICK 1: XOR Swap");
        int a = 5, b = 10;
        System.out.println("Before swap: a=" + a + ", b=" + b);
        // XOR swap
        a ^= b;
        b ^= a;
        a ^= b;
        System.out.println("After XOR swap: a=" + a + ", b=" + b);

        // Trick 2: Fast multiplication/division by powers of 2
        System.out.println("\n⚡ TRICK 2: Fast multiplication/division");
        int x = 20;
        System.out.println(x + " * 4 = " + (x << 2)); // Left shift by 2 = multiply by 4
        System.out.println(x + " / 4 = " + (x >> 2)); // Right shift by 2 = divide by 4

        // Trick 3: Check if two numbers have opposite signs
        System.out.println("\n🎭 TRICK 3: Opposite signs check");
        System.out.println("5 and -3 have opposite signs: " + haveOppositeSigns(5, -3));
        System.out.println("5 and 3 have opposite signs: " + haveOppositeSigns(5, 3));

        // Trick 4: Turn off rightmost contiguous 1s
        System.out.println("\n🔧 TRICK 4: Advanced bit manipulation");
        int num = 156; // 10011100
        System.out.println("Original: " + Integer.toBinaryString(num));
        System.out.println("After turning off rightmost 1s: " +
                Integer.toBinaryString(turnOffRightmost1s(num)));

        // Trick 5: Isolate rightmost 0 bit
        num = 156; // 10011100 -> rightmost 0 is at position 1
        System.out.println("\nIsolate rightmost 0 in " + Integer.toBinaryString(num) +
                ": " + Integer.toBinaryString(isolateRightmost0(num)));

        System.out.println("\n" + "=".repeat(50) + "\n");

        // FINAL SUMMARY
        printCheatSheet();
    }

    /**
     * 🚀 ADVANCED TRICK: Check opposite signs
     * PATTERN: (x ^ y) < 0
     * WHY: XOR of numbers with opposite signs has MSB = 1 (negative)
     */
    public boolean haveOppositeSigns(int x, int y) {
        return (x ^ y) < 0;
    }

    /**
     * 🚀 ADVANCED TRICK: Turn off rightmost contiguous 1s
     * PATTERN: n & (n + 1)
     * EXAMPLE: 156 (10011100) -> 144 (10010000)
     */
    public int turnOffRightmost1s(int n) {
        return n & (n + 1);
    }

    /**
     * 🚀 ADVANCED TRICK: Isolate rightmost 0 bit
     * PATTERN: ~n & (n + 1)
     */
    public int isolateRightmost0(int n) {
        return ~n & (n + 1);
    }

    // ===============================
    // CHEAT SHEET - MEMORIZE THIS!
    // ===============================

    public void printCheatSheet() {
        System.out.println("📋 BIT MANIPULATION CHEAT SHEET");
        System.out.println("===============================");
        System.out.println("CHECK BIT i:        (n & (1 << i)) != 0");
        System.out.println("SET BIT i:          n | (1 << i)");
        System.out.println("CLEAR BIT i:        n & ~(1 << i)");
        System.out.println("TOGGLE BIT i:       n ^ (1 << i)");
        System.out.println("POWER OF 2:         n > 0 && (n & (n-1)) == 0");
        System.out.println("COUNT SET BITS:     while(n) { count++; n &= (n-1); }");
        System.out.println("RIGHTMOST SET BIT:  n & (-n)");
        System.out.println("CLEAR RIGHTMOST:    n & (n-1)");
        System.out.println("XOR PROPERTIES:     a^a=0, a^0=a, a^b^b=a");
        System.out.println("MULTIPLY BY 2^k:    n << k");
        System.out.println("DIVIDE BY 2^k:      n >> k");
        System.out.println("OPPOSITE SIGNS:     (a ^ b) < 0");
        System.out.println("TURN OFF RIGHT 1s:  n & (n+1)");
        System.out.println("ISOLATE RIGHT 0:    ~n & (n+1)");

        System.out.println("\n🎯 INTERVIEW FREQUENCY RANKING:");
        System.out.println("1. XOR patterns (Single Number, Missing Number)");
        System.out.println("2. Bit counting (Brian Kernighan)");
        System.out.println("3. Power of 2 checks");
        System.out.println("4. Basic set/clear/check operations");
        System.out.println("5. Bit reversal and manipulation");

        System.out.println("\n💡 PRO TIPS FOR INTERVIEWS:");
        System.out.println("• Always ask about input constraints (32-bit? signed?)");
        System.out.println("• Draw out binary representations for small examples");
        System.out.println("• Remember: bit positions start from 0 (rightmost)");
        System.out.println("• XOR is your friend for 'find the different' problems");
        System.out.println("• Brian Kernighan's algorithm is faster than naive bit counting");
        System.out.println("• Left shift multiplies, right shift divides by powers of 2");

        System.out.println("\n🚨 COMMON PITFALLS:");
        System.out.println("• Forgetting to handle negative numbers");
        System.out.println("• Off-by-one errors in bit positions");
        System.out.println("• Not considering integer overflow");
        System.out.println("• Mixing up & (AND) vs && (logical AND)");
        System.out.println("• Forgetting operator precedence (use parentheses!)");
    }

    public static void main(String[] args) {
        BitManipulation bm = new BitManipulation();

        System.out.println("=== BIT MANIPULATION CRASH COURSE ===\n");

        // Run all demonstrations
        bm.demonstrateBasicOperations();
        bm.demonstrateCommonPatterns();
        bm.demonstrateInterviewProblems();
        bm.demonstrateAdvancedTricks();
    }
}
