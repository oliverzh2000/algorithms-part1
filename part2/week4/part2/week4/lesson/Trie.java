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

    public boolean contains(String key) {
        return get(key) != null;
    }

    public T get(String key) {
        Node retrieved = get(root, key, 0);
        if (retrieved == null) {
            return null;
        }
        return (T) retrieved.value;
    }

    private Node get(Node node, String key, int charIndex) {
        if (node == null) {
            return null;
        }
        if (charIndex == key.length()) {
            return node;
        }
        char c = key.charAt(charIndex);
        return get(node.next[c], key, charIndex + 1);
    }
}
