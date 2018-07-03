import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            randomizedQueue.enqueue(StdIn.readString());
        }
        for (String string : randomizedQueue) {
            StdOut.println(string);
        }
    }
}
