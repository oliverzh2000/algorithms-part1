package part2.week5.lesson;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class RunLengthCoding {
    private final static int R = 256; // Radix
    private final static int lgR = 8; // Bits per count

    public static void compress() {
        // See textbook.
    }

    public static void expaned() {
        // Assume first bit false. If it isn't, then the length of the false run will be 0.
        boolean bitValue = false;
        while (!BinaryStdIn.isEmpty()) {
            int runLength = BinaryStdIn.readInt(lgR);
            for (int i = 0; i < runLength; i++) {
                BinaryStdOut.write(bitValue);
            }
            bitValue = !bitValue;
        }
        BinaryStdOut.close();
    }
}
