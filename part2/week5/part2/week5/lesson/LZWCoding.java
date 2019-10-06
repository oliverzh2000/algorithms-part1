package part2.week5.lesson;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.TST;

public class LZWCoding {
    private static final int R = 256; // Radix, or number of possible input chars
    private static final int W = 8; // W-bit codewords
    private static final int L = 2 << W; // Number of codewords = 2^W

    public static void compress() {
        String input = BinaryStdIn.readString();

        // Codewords for single char, radix R keys.
        TST<Integer> st = new TST<>();
        for (int i = 0; i < R; i++) {
            st.put("" + (char) i, i);
        }
        int code = R + 1;

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);
            BinaryStdOut.write(st.get(s), W);
            int t = s.length();
            if (t < input.length() && code < L) {
                st.put(input.substring(0, t + 1), code++); // Add s to symbol table
            }
            input = input.substring(t); // Scan past s in input
        }
        BinaryStdOut.write(R, W); // Write EOF
        BinaryStdOut.close();
    }
}
