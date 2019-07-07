public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N;
    private int capacity;

    public MaxPQ(int capacity) {
        this.capacity = capacity;
        this.pq = (Key[]) new Comparable[capacity + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key key) {
        pq[++N] = key;
        swim(N);
    }

    public Key delMax() {
        Key max = pq[1];
        swap(1, N--);
        sink(1);
        pq[N + 1] = null;
        return max;
    }

    private void swim(int i) {
        while (i > 1 && pq[parent(i)].compareTo(pq[i]) < 0) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private void sink(int i) {
        while (leftChild(i) <= N) {
            int largestChild = leftChild(i);
            if (rightChild(i) < N && pq[rightChild(i)].compareTo(pq[leftChild(i)]) > 0) {
                largestChild = rightChild(i);
            }
            if (!(pq[largestChild].compareTo(pq[i]) > 0)) { break; }
                // sink only if largest child is larger than current child.
            swap(largestChild, i);
            i = largestChild;
        }
    }

    private void swap(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int leftChild(int i) {
        return i * 2;
    }

    private int rightChild(int i) {
        return leftChild(i) + 1;
    }

    private void printRec(int i, int depth) {
        System.out.println(i);
        System.out.println(new String(new char[depth]) + pq[i]);
        if (leftChild(i) < capacity) {
            printRec(leftChild(i), depth + 1);
        }
        if (rightChild(i) < capacity) {
            printRec(rightChild(i), depth + 1);
        }
    }

    public static void main(String[] args) {
        MaxPQ<Integer> maxPQ = new MaxPQ<>(15);
        for (int i = 0; i < 10; i++) {
            maxPQ.insert(i);
        }
        maxPQ.printRec(0, 0);
    }
}
