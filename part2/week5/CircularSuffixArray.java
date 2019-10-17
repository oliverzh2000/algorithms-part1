import java.util.Arrays;

public class CircularSuffixArray {
    // We define index[i] to be the index of the original suffix that appears ith in the sorted array.
    // For example, index[11] = 2 means that the 2nd original
    // suffix appears 11th in the sorted order (i.e., last alphabetically).
    private int[] indices;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("s must not be null");
        }
        
        CircularSuffix[] suffixesArray = new CircularSuffix[s.length()];
        for (int i = 0; i < s.length(); i++) {
            suffixesArray[i] = new CircularSuffix(s, i);
        }
        Arrays.sort(suffixesArray);

        indices = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            indices[i] = suffixesArray[i].suffixOffest;
        }
    }

    // length of s
    public int length() {
        return indices.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= indices.length) {
            throw new IllegalArgumentException("i is out of range");
        }
        return indices[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < csa.length(); i++) {
            System.out.println(csa.index(i));
        }
    }

    private class CircularSuffix implements Comparable<CircularSuffix> {
        String s;
        int suffixOffest;

        CircularSuffix(String original, int start_index) {
            s = original;
            this.suffixOffest = start_index;
        }

        private char charAt(int i) {
            return s.charAt((suffixOffest + i) % s.length());
        }

        @Override
        public int compareTo(CircularSuffix other) {
            if (this == other) return 0;
            for (int i = 0; i < s.length(); i++) {
                if (this.charAt(i) > other.charAt(i)) return 1;
                if (this.charAt(i) < other.charAt(i)) return -1;
            }
            return 0;
        }
    }
}