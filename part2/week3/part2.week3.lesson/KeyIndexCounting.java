package part2.week3.lesson;

public class KeyIndexCounting {
    // R is radix. elements in a must be between 0 and R - 1
    public static void sort(int[] a, int R) {
        int N = a.length;
        int[] count = new int[R + 1];

        // Count frequency of each element in a[]
        for (int i = 0; i < N; i++) {
            count[a[i] + 1]++;
        }

        // Compute frequency cumulates
        for (int i = 0; i < R; i++) {
            count[i + 1] += count[i];
        }

        // Use cumulates to move elements into auxilliary array
        int[] aux = new int[N];
        for (int i = 0; i < N; i++) {
            aux[count[a[i]]] = a[i];
            count[a[i]]++;
        }

        // Move elements from aux[] back into a[]
        for (int i = 0; i < N; i++) {
            a[i] = aux[i];
        }
    }
}
