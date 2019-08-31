package part2.week4.lesson;

public class TernarySearchTrie<T> {
    private static final int R = 256; // extended ASCII
    private Node root = new Node();

    private static class Node {
        private Object value;
        private char c;
        private Node left, mid, right;
    }

    public void put(String key, T value) {
        root = putNode(root, key, value, 0);
    }

    private Node putNode(Node node, String key, T value, int charIndex) {
        char c = key.charAt(charIndex);
        if (key == null) {
            node = new Node();
            node.c = c;
        }
        if (c < node.c) {
            node.left = putNode(node.left, key, value, charIndex + 1);
        } else if (c > node.c) {
            node.right = putNode(node.right, key, value, charIndex + 1);
        } else if (charIndex == key.length() - 1) { // last char
            node.value = value;
        } else {
            node.mid = putNode(node.mid, key, value, charIndex + 1);
        }
        return node;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public T get(String key) {
        Node retrieved = getNode(root, key, 0);
        if (retrieved == null) {
            return null;
        }
        return (T) retrieved.value;
    }

    private Node getNode(Node node, String key, int charIndex) {
        if (node == null) {
            return null;
        }
        char c = key.charAt(charIndex);
        if (c < node.c) {
            return getNode(node.left, key, charIndex + 1);
        } else if (c > node.c) {
            return getNode(node.right, key, charIndex + 1);
        } else if (charIndex == key.length() - 1) { // last char
            return node;
        } else {
            return getNode(node.mid, key, charIndex + 1);
        }
    }
}
