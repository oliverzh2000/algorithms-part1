package part2.week4.lesson;

public class SubstringSearch {
    private static final int R = 256;

    public static int bruteForce(String pattern, String text) {
        int N = text.length();
        int M = pattern.length();
        for (int i = 0; i < N; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (text.charAt(i + j) != text.charAt(j)) {
                    break;
                }
            }
            if (j == M) {
                return i;
            }
        }
        return -1;
    }

    public static int bruteForceExplicitBackup(String pattern, String text) {
        int i, j;
        int N = text.length();
        int M = pattern.length();
        for (i = 0, j = 0; i < N && j < M; i++) {
            if (text.charAt(i) == pattern.charAt(j)) {
                j++;
            } else {
                i -= j;
                j = 0;
            }
        }
        if (j == M) {
            return i - M;
        } else {
            return N;
        }
    }

    public static int KnuthMorrisPratt(String pattern, String text) {
        // In the deterministic finite state automaton (dfa),
        // state after reading text[i] is the number of characters in the pattern that
        // have been matched, or alternatively:
        // the length of the longest prefix of pattern that is also a suffix of text[0..i]
        int N = text.length();
        int M = pattern.length();
        int[][] dfa = new int[M][N];
        dfa[pattern.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            // Compute dfa[][j].
            for (int c = 0; c < R; c++) {
                dfa[c][j] = dfa[c][X];
            }
            dfa[pattern.charAt(j)][j] = j + 1;
            X = dfa[pattern.charAt(j)][X];
        }

        int i, j;
        // Build dfa: match transitions
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[pattern.charAt(i)][text.charAt(j)];
        }
        if (j == M) {
            return i - M;
        }
        return -1;
    }

    public static void main(String[] args) {
        bruteForceExplicitBackup("abacadabra", "abra");
    }
}
