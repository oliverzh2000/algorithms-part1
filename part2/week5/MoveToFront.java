import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;

public class MoveToFront {
    private static final int R = 256; // Radix

    // Apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        // char[] ordering is the moved-to-front sequence.
        LinkedList<Character> ordering = new LinkedList<>();
        for (char c = 0; c < R; c++) {
            ordering.add(c);
        }
        for (char c : BinaryStdIn.readString().toCharArray()) {
            // Output index of c in ordering and move c to front.
            int index = ordering.indexOf(c);
            BinaryStdOut.write((char) index);
            ordering.addFirst(ordering.remove(index));
        }
        BinaryStdOut.close();
    }

    // Apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        // char[] ordering is the moved-to-front sequence.
        LinkedList<Character> ordering = new LinkedList<>();
        for (char c = 0; c < R; c++) {
            ordering.add(c);
        }
        for (int c : BinaryStdIn.readString().toCharArray()) {
            // Output the cth character in the ordering, and move it to front.
            BinaryStdOut.write(ordering.get(c));
            ordering.addFirst(ordering.remove(c));
        }
        BinaryStdOut.close();
    }

    // If args[0] is "-", apply move-to-front encoding
    // If args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        } else if (args[0].equals("+")) {
            decode();
        } else {
            throw new IllegalArgumentException("Usage: '-' to encode and '+' to decode");
        }
    }
}