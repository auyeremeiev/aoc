package common;

public class ArraysUtils {

    public static void removeAndShift(int[] array, int index) {
        removeAndShift(array, index, 1);
    }

    public static void removeAndShift(int[] array, int index, int length) {
        try {
            System.arraycopy(array, index + length, array, index, array.length - index - length);
            for (int i = array.length - length; i < array.length; i++) {
                array[i] = -1;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static <T> void removeAndShift(T[] array, int index) {
        removeAndShift(array, index, 1);
    }

    public static <T> void removeAndShift(T[] array, int index, int length) {
        // 1 2 [3 4] 5 6 7 8
        // i = 2
        // l = 4
        // li = 2
        // 8 - 2 - 2
        // 1 2 5 6 7 8 [7 8] -> 6, 7
        System.arraycopy(array, index + length, array, index, array.length - index - length);
        for (int i = array.length - length; i < array.length; i++) {
            array[i] = null;
        }
    }
}
