package part2.week3.lesson;

public class LSDStringSort {
    private final static int R = 256; // Radix for 8-bit chars

    // W is length of fixed-length Strings in a
    public static void sort(String[] a, int W) {
        int N = a.length;
        String[] aux = new String[N];

        // Use key-index counting to sort each character from right to left.
        for (int charIndex = W - 1; charIndex >= 0; charIndex--) {
            int[] count = new int[R+1];
            for (String string : a) {
                count[string.charAt(charIndex) + 1]++;
            }
            for (int r = 0; r < R; r++) {
                count[r+1] += count[r];
            }
            for (String string : a) {
                aux[count[string.charAt(charIndex)]] = string;
                count[string.charAt(charIndex)]++;
            }
            System.arraycopy(aux, 0, a, 0, N);
        }
    }
}
