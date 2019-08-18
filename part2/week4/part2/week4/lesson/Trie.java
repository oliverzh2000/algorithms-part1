package part2.week4.lesson;

public class Trie<T> {
    private static final int R = 256; // extended ASCII
    private Node root = new Node();

    private static class Node<T> {
        private T value;
        private Node[] next = new Node[R];
    }

    public void put(String key, T value) {
        root = put(root, key, value, 0);
    }

    private Node put(Node node, String key, T value, int charIndex) {
        if (node == null) {
            node = new Node();
        }
        if (charIndex == key.length()) {
            node.value = value;
        }
        char c = key.charAt(charIndex);
        node.next[c] = put(node.next[c], key, value, charIndex + 1);
        return node;
    }
}
