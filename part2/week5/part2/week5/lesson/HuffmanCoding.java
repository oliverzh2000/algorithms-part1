package part2.week5.lesson;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

public class HuffmanCoding {
    private static final int R = 256; // Radix

    public static void compress() {
        String string = BinaryStdIn.readString();
        char[] input = string.toCharArray();

        // Tabulate frequency counts.
        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++) {
            freq[input[i]]++;
        }

        // Build Huffman code trie.
        Node root = buildTrie(freq);

        // Build code table (recursive).
        String[] codeTable = new String[R];
        buildCode(codeTable, root, "");

        writeTrie(root);
        BinaryStdOut.write(input.length);

        // Use Huffman code to encode input.
        for (int i = 0; i < input.length; i++) {
            String code = codeTable[input[i]];
            for (int j = 0; j < code.length(); j++)
                if (code.charAt(j) == '1') {
                    BinaryStdOut.write(true);
                } else {
                    BinaryStdOut.write(false);
                }
        }
        BinaryStdOut.close();
    }

    private static String[] buildCode(Node root) { // Make a lookup table from trie.
        String[] st = new String[R];
        buildCode(st, root, "");
        return st;
    }

    private static void buildCode(String[] st, Node x, String s) { // Make a lookup table from trie (recursive).
        if (x.isLeaf()) {
            st[x.ch] = s;
            return;
        }
        buildCode(st, x.left, s + '0');
        buildCode(st, x.right, s + '1');
    }

    public void expand() {
        Node root = readTrie();
        int N = BinaryStdIn.readInt();

        for (int i = 0; i < N; i++) {
            Node currentNode = root;
            while (!currentNode.isLeaf()) {
                if (!BinaryStdIn.readBoolean()) {
                    currentNode = currentNode.left;
                } else {
                    currentNode = currentNode.right;
                }
            }
            BinaryStdOut.write(currentNode.ch, 8);
        }
        BinaryStdOut.close();
    }

    private static Node buildTrie(int[] freq) {
        // Start with one Node corresponding to each char i (with weight freq[i]) on a MinPQ.
        // Repeat until MinPQ has only a single trie left:
        // - Select the two tries with min total freq.
        // - Merge these two tries and insert back unto minPQ.

        MinPQ<Node> huffmanForrest = new MinPQ<>();
        // Initialize PQ with singleton tries
        for (char i = 0; i < R; i++) {
            if (freq[i] > 0) {
                huffmanForrest.insert(new Node(i, freq[i], null, null));
            }
        }

        // Continue merging the two least-freq tries until only one left.
        while (!huffmanForrest.isEmpty()) {
            Node left = huffmanForrest.delMin();
            Node right = huffmanForrest.delMin();
            huffmanForrest.insert(new Node('\0', left.freq + right.freq, left, right));
        }
        return huffmanForrest.delMin();
    }

    private static void writeTrie(Node x) {
        // Write preorder traversal of trie.
        // Mark leaf nodes with 1 and internal nodes with 0.
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    private static Node readTrie() {
        // Reconstruct from preorder traversal of trie.
        // Leaf nodes were marked with 1 and internal nodes with 0.
        if (BinaryStdIn.readBoolean()) {
            char c = BinaryStdIn.readChar(8);
            return new Node(c, 0, null, null);
        }
        Node x = readTrie();
        Node y = readTrie();
        // Only the leaf nodes store characters in them.
        return new Node('\0', 0, x, y);
    }

    private static class Node implements Comparable<Node> {
        private final char ch; // Used only for leaf nodes
        private final int freq; // Used only for compress
        private final Node left, right;

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }
}
