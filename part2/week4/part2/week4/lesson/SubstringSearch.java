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
}
