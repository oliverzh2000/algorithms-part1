import java.util.Arrays;

public class HeapSort {
    public static void sort(int[] array) {
        int N = array.length - 1;
        // construct heap.
        for (int i = N / 2; i >= 0; i--) {
            sink(array, i, N);
        }
        System.out.println(Arrays.toString(array));
        // sort the heap.
        while (N > 0) {
            System.out.println(N);
            swap(array, N--, 0);
            sink(array, 0, N);
        }
    }

    private static void swim(int[] array, int i, int N) {
        while (i > 1 && array[parent(i)] < array[i]) {
            swap(array, i, parent(i));
            i = parent(i);
        }
    }

    private static void sink(int[] array, int i, int N) {
        while (leftChild(i) <= N) {
            int largestChild = leftChild(i);
            if (rightChild(i) <= N && array[rightChild(i)] > array[leftChild(i)]) {
                largestChild = rightChild(i);
            }
            if (!(array[largestChild] > array[i])) { break; }
            // sink only if largest child is larger than current child.
            swap(array, largestChild, i);
            i = largestChild;
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static int parent(int i) {
        return (i + 1) / 2 - 1;
    }

    private static int leftChild(int i) {
        return (i + 1) * 2 - 1;
    }

    private static int rightChild(int i) {
        return leftChild(i) + 1;
    }

    public static void main(String[] args) {
        int[] array = {5,9,8,7,4,1,3,2,6};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
