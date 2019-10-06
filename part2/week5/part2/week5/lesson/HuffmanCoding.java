package part2.week5.lesson;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

public class HuffmanCoding {
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
