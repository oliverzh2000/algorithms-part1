package part2.week4.lesson;

public class SubstringSearch {
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
}
