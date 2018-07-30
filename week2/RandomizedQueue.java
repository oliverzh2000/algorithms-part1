import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.items = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        if (size >= items.length) {
            resize(items.length * 2);
        }
        items[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (size <= items.length / 4) {
            resize(items.length / 2);
        }
        return items[--size];
    }

    private void resize(int newSize) {
        Item[] newItems = (Item[]) new Object[newSize];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Item[] itemsShuffled = (Item[]) new Object[size];
            int index = 0;
            {
                System.arraycopy(items , 0, itemsShuffled, 0, size);
                StdRandom.shuffle(itemsShuffled);
                int index = 0;
            }
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return itemsShuffled[index++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.enqueue(40);
        queue.enqueue(50);
        queue.enqueue(60);

        for (Integer i : queue) {
            System.out.println(i);
        }
    }
}