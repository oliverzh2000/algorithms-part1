import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size = 0;
    private Node head;
    private Node tail;


    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        if (isEmpty()) {
            Node newFirst = new Node(item, null, null);
            head = newFirst;
            tail = newFirst;
        } else {
            Node oldFirst = head;
            head = new Node(item, null, oldFirst);
            oldFirst.prev = head;
        }
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        if (isEmpty()) {
            addFirst(item);
            return;
        } else {
            Node oldLast = tail;
            tail = new Node(item, oldLast, null);
            oldLast.next = tail;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item firstItem = head.item;
        if (size() == 1) {
            head = null;
            tail = null;
        } else {
            head.next.prev = null;
            head = head.next;
        }
        size--;
        return firstItem;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item lastItem = tail.item;
        if (size() == 1) {
            head = null;
            tail = null;
        } else {
            tail.prev.next = null;
            tail = tail.prev;
        }
        size--;
        return lastItem;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            int index = 0;
            Node currentNode = head;
            @Override
            public boolean hasNext() {
                return index < size();
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item currentItem = currentNode.item;
                currentNode = currentNode.next;
                index++;
                return currentItem;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        d.addFirst("gentlemen");
        d.addFirst("you");
        d.addFirst("can't");
        d.addFirst("fight");

        for (String word : d) {
            System.out.println(word);
        }
    }

    private class Node {
        Item item;
        Node prev;
        Node next;

        Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}