import java.util.Arrays;

public class Week3 {
    static void selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int minValue = array[i];
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < minValue) {
                    minValue = array[j];
                    minIndex = j;
                }
            }
            swap(array, i, minIndex);
        }
    }

    static void insertionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (array[j - 1] > array[j]) {
                    swap(array, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    static void mergeSort(int[] array, int[] aux, int lo, int hi) {
        if ((hi - lo) <= 1) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        mergeSort(array, aux, lo, mid);
        mergeSort(array, aux, mid , hi);
        merge(array, aux, lo, mid, hi);
    }

    static void merge(int[] array, int[] aux, int lo, int mid, int hi) {
        assert isSorted(array, lo, mid);
        assert isSorted(array, mid, hi);
        System.arraycopy(array, lo, aux, lo, hi - lo);
        int i = lo;     
        int j = mid;
        for (int k = lo; k < hi; k++) {
            if (i >= mid) {
                array[k] = aux[j++];
            } else if (j >= hi) {
                array[k] = aux[i++];
            } else if (aux[i] < aux[j]) {
                array[k] = aux[i++];
            } else {
                array[k] = aux[j++];
            }
        }
        assert isSorted(array, lo, hi);
    }

    static boolean isSorted(int[] array, int lo, int hi) {
        for (int i = lo + 1; i < hi; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    static void bottomUpMergeSort(int[] array) {
        int[] aux = new int[array.length];
        for (int size = 1; size < array.length; size *= 2) {
            for (int lo = 0; lo < array.length - size; lo += size * 2) {
                int mid = lo + size;
                merge(array, aux, lo, mid, Math.min(lo + size * 2, array.length));
            }
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{6, 5, 3, 1, 8, 7, 2, 4};
        insertionSort(array);

        System.out.println(Arrays.toString(array));
    }
}
