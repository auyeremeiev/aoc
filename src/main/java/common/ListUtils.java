package common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ListUtils {

    public static <T> void swap(List<T> list, int i, int j) {
        var temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static <T> void moveAndShift(List<T> list, int i, int j) {
        var temp = list.remove(j);
        list.add(i, temp);
    }

    public static int minNumber(Collection<Integer> list) {
        int result = Integer.MAX_VALUE;
        for (Integer number : list) {
            if (number < result) {
                result = number;
            }
        }
        return result;
    }

    public static int maxNumber(Collection<Integer> list) {
        int result = Integer.MIN_VALUE;
        for (Integer number : list) {
            if (number > result) {
                result = number;
            }
        }
        return result;
    }

    public static Optional<Integer> findFirstEncounterIndex(List<Integer> list, int from, int toInclusive, int number) {
        return findFirstEncounterIndexForAny(list, from, toInclusive, List.of(number));
    }

    public static Optional<Integer> findFirstEncounterIndexForAny(List<Integer> list, int from, int toInclusive, Collection<Integer> numbers) {
        for (int i = from; i < toInclusive; i++) {
            if (numbers.contains(list.get(i))) {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }


    public static List<List<Integer>> createMatrix(int numberOfRows, int numberOfColumns) {
        return createMatrix(numberOfRows, numberOfColumns, () -> 0);
    }

    public static <T> List<List<T>> createMatrix(int numberOfRows, int numberOfColumns, Supplier<T> defaultValueSuppler) {
        List<List<T>> matrix = new ArrayList<>();
        for (int i = 0; i < numberOfRows; i++) {
            List<T> line = new ArrayList<>();
            for (int j = 0; j < numberOfColumns; j++) {
                line.add(defaultValueSuppler.get());
            }
            matrix.add(line);
        }
        return matrix;
    }

    public static List<List<Boolean>> createBooleanMatrix(int numberOfRows, int numberOfColumns) {
        List<List<Boolean>> matrix = new ArrayList<>();
        for (int i = 0; i < numberOfRows; i++) {
            List<Boolean> line = new ArrayList<>();
            for (int j = 0; j < numberOfColumns; j++) {
                line.add(false);
            }
            matrix.add(line);
        }
        return matrix;
    }

    public static <T> String printMatrix(List<List<T>> matrix) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < matrix.size(); i++) {
            List<String> line = new ArrayList<>();
            for (int j = 0; j < matrix.get(i).size(); j++) {
                line.add(matrix.get(i).get(j).toString());
            }
            builder.append(String.join(",", line)).append("\n");
        }

        return builder.toString();
    }

    public static void addToSortedList(List<Integer> list, Integer newElement) {
        addToSortedList(list, newElement, Integer::compare);
    }

    // Can be rewritten to log(n)
    public static <T> void addToSortedList(List<T> list, T newElement, Comparator<T> comparator) {
        if (list.isEmpty()) {
            list.add(newElement);
            return;
        }

        int i = 0;

        while (i < list.size() && comparator.compare(newElement, list.get(i)) > 0) {
            i++;
        }

        if (i == list.size()) {
            list.add(newElement);
        } else {
            list.add(i, newElement);
        }
    }

    public static boolean removeElement(List<Integer> list, Integer element) {
        Optional<Integer> elementIndex = binarySearch(list, element);
        if (elementIndex.isEmpty()) {
            throw new IllegalStateException("Didn't find an element");
        }

        return list.remove(element);
    }

    public static Optional<Integer> binarySearch(List<Integer> list, Integer element) {
        return binarySearch(list, element, Integer::compareTo);
    }

    public static <T> Optional<Integer> binarySearch(List<T> list, T element, Comparator<T> comparator) {
        if (list.isEmpty()) {
            return Optional.empty();
        }

        int l = 0;
        int r = list.size() - 1;
        // 0 [{[1] | {2 3}] 4 => m = (1 + 4) / 2 - 1 = 1 => l = 1, r = m = 1 (end) or l = 3 and r = 4
        // 0 [{1 [2]} | {3 4}] => m = (1 + 5) / 2 - 1 = 2 => l = 1, r = m = 2 or l = 3 and r = 5
        // 0 [{1} | {2}] 3 4] => m = (1 + 2) / 2 - 1 = 0 (incorrect)

        // 0 [{1 [2]} {3}] 4 => m = (1 + 3) / 2 = 2 => l = 1, r = m - 1 = 1 or l = 3 and r = 3
        // 0 [{1 [2]} | {3 4}] => m = (1 + 4) / 2 = 2 => l = 1, r = m - 1 = 1 or l = 3 and r = 4
        // 0 [{[1]} | {2}] 3 4] => m = (1 + 2) / 2 = 1 => l = 1, r = 0 (end) or l = 2, r = 2
        // 0 [{1}] 2 3 4] => m = (1 + 1) / 2 = 1.
        //      if {1} is less than x then l = 1, r = 0 (end);
        //      if {1} is greater than x then l = 2, r = 1 (end)
        while (l <= r) {
            int m = (l + r) / 2;
            if (comparator.compare(list.get(m), element) == 0) {
                return Optional.of(m);
            } else if (comparator.compare(list.get(m), element) > 0) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }

        return Optional.empty();
    }

    public static <T> void fill(List<T> list, T value, int size) {
        for (int i = 0; i < size; i++) {
            list.add(value);
        }
    }

    public static <T> String printList(List<T> list) {
        List<String> stringifiedList = list.stream().map(Object::toString).toList();
        return "[" + String.join(", ", stringifiedList) + "]";
    }

    public static <T> boolean isBeyoundEdge(List<List<T>> field, Point point) {
        if (point.getLeft() < 0 || point.getRight() < 0) {
            return true;
        }

        if (point.getLeft() >= field.size()) {
            return true;
        }

        return point.getRight() >= field.get(point.getLeft()).size();
    }

    public static <T> boolean isAtEdge(List<List<T>> field, Point point) {
        if (point.getLeft() == 0 || point.getRight() == 0) {
            return true;
        }

        if (point.getLeft() == field.size() - 1) {
            return true;
        }

        return point.getRight() == field.get(point.getLeft()).size() - 1;
    }

    public static <T> T get(List<List<T>> field, Point point) {
        return field.get(point.getLeft()).get(point.getRight());
    }
}
