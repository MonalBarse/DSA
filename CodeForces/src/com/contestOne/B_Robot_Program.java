
   import java.util.*;
public class B_Robot_Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            int n = sc.nextInt();
            int x = sc.nextInt();
            long k = sc.nextLong();
            String commands = sc.next();

            System.out.println(solve(n, x, k, commands));
        }
    }

    public static long handleStartingAtZero(int n, long k, String commands) {
        // Start count at 1
        long zeroCount = 1;
        long cycleLength = 0;
        int position = 0;

        for (int i = 0; i < n; i++) {
            // if L -1 else 1 (no other option other than L or R)
            position = position + (commands.charAt(i) == 'L') ? -1 : 1;
            if (position == 0) {
                cycleLength = i + 1;
                long completeCycles = k / cycleLength;
                long remainder = k % cycleLength;
                zeroCount += completeCycles;

                position = 0;
                for (int j = 0; j < remainder; j++) {
                    position += (commands.charAt(j) == 'L') ? -1 : 1;
                    if (position == 0) {
                        zeroCount++;
                        break;
                    }
                }
                return zeroCount;
            }
        }
        return zeroCount;
    }


    public static long solve(int n, int x, long k, String commands) {

    // Handeling edge case when robot starts at zero
        if (x == 0) return handleStartingAtZero(n, k, commands);

        int position = x, cmdIndex = 0;
        long zeroCount = 0, time = 0;

        // This did for memoization
        Map<String, Long> seen = new HashMap<>();

        while (time < k) {
            String state = position + "," + cmdIndex;

            if (seen.containsKey(state)) {
                long cycleStart = seen.get(state);
                long cycleLength = time - cycleStart;
                long zerosInCycle = countZeros(n, position, cmdIndex, commands, cycleLength);

                long remainingTime = k - time;
                long completeCycles = remainingTime / cycleLength;
                long remainder = remainingTime % cycleLength;

                zeroCount += zerosInCycle * completeCycles;
                zeroCount += countZeros(n, position, cmdIndex, commands, remainder);
                return zeroCount;
            }

            seen.put(state, time);

            if (time >= k) return zeroCount;

            if (cmdIndex < n) {
                position += (commands.charAt(cmdIndex) == 'L') ? -1 : 1;
                cmdIndex++;
                time++;

                if (position == 0) {
                    zeroCount++;
                    cmdIndex = 0;
                }

                if (cmdIndex == n && position != 0) return zeroCount;
            } else {
                return zeroCount;
            }
        }
        return zeroCount;
    }

    public static long countZeros(int n, int position, int cmdIndex, String commands, long steps) {
        if (steps == 0) return 0;
        long zeroCount = 0;

        for (long i = 0; i < steps; i++) {
            if (cmdIndex < n) {
                position += (commands.charAt(cmdIndex) == 'L') ? -1 : 1;
                cmdIndex++;

                if (position == 0) {
                    zeroCount++;
                    cmdIndex = 0;
                }

                if (cmdIndex == n && position != 0) return zeroCount;
            } else {
                return zeroCount;
            }
        }
        return zeroCount;
    }

}
