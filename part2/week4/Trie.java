public class Trie {
    private static final int R = 26; // English alphabet
    private Node root = new Node();

    private static class Node{
        private Node[] next = new Node[R];
    }

    public void put(String key) {
        root = putNode(root, key, 0);
    }

    private Node putNode(Node node, String key, int charIndex) {
        if (node == null) {
            node = new Node();
        }
        if (charIndex == key.length()) {
            return node;
        }
        int ord = ord(key.charAt(charIndex));
        node.next[ord] = putNode(node.next[ord], key, charIndex + 1);
        return node;
    }

    public boolean isAPrefix(String prefix) {
        return getNode(root, prefix, 0) != null;
    }

    private Node getNode(Node node, String key, int charIndex) {
        if (node == null) {
            return null;
        }
        if (charIndex == key.length()) {
            return node;
        }
        int ord = ord(key.charAt(charIndex));
        return getNode(node.next[ord], key, charIndex + 1);
    }

    // Get the ordinal value of the uppercase character ('A' == 65).
    private int ord(char c) {
        return c - 65; // ord('A') == 0
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        t.put("U");
        t.put("US");
        t.put("A");
        t.put("ANNA");
        t.isAPrefix("USA");
    }
}
