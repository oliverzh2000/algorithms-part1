import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {
    private static final int R = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String input = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(input);

        // Output index of row that original string ends up.
        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
            }
        }

        // Output last column in the sorted circular suffix array.
        for (int i = 0; i < csa.length(); i++) {
            BinaryStdOut.write(charAt(input, csa.index(i), csa.length() - 1));
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        char[] lastCol = BinaryStdIn.readString().toCharArray();
        char[] firstCol = lastCol.clone();

//        int first = 3;
//        char[] lastCol = {'A', 'R', 'D', '!', 'R', 'C', 'A', 'A', 'A', 'A', 'B', 'B'};
//        char[] firstCol = {'!', 'A', 'A', 'A', 'A', 'A', 'B', 'B', 'C', 'D', 'R', 'R'};

        Arrays.sort(firstCol);

        int[] next = new int[firstCol.length];
        int[] base = new int[R];
        Arrays.fill(next, -1);
        for (int i = 0; i < firstCol.length; i++) {
            // find smallest j where firstCol[i] == lastCol[j].
            for (int j = base[firstCol[i]]; j < lastCol.length; j++) {
                if (firstCol[i] == lastCol[j]) {
                    // TODO: keep track of chars to skip, for critical optimization.
                    next[i] = j;
                    base[firstCol[i]] = j + 1;
                    break;
                }
            }
        }

        // Apply inverse transform from the known next[] array.
        int currentIndex = first;
        for (int i = 0; i < firstCol.length; i++) {
            BinaryStdOut.write(firstCol[currentIndex], 8);
            currentIndex = next[currentIndex];
        }
        BinaryStdOut.close();
    }

    private static char charAt(String s, int suffixOffset, int i) {
        return s.charAt((suffixOffset + i) % s.length());
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        } else if (args[0].equals("+")) {
            inverseTransform();
        }
    }
}