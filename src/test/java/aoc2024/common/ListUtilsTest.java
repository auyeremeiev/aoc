package aoc2024.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import common.ListUtils;
import org.junit.jupiter.api.Test;

import static common.ListUtils.binarySearch;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ListUtilsTest {

    @Test
    public void testAddToSortedList() {
        List<Integer> list = new ArrayList<>(List.of(4, 8, 9, 13, 24, 24, 29));

        ListUtils.addToSortedList(list, 3);
        assertThat(list).containsAll(List.of(3, 4, 8, 9, 13, 24, 24, 29));

        ListUtils.addToSortedList(list, 3);
        assertThat(list).containsAll(List.of(3, 3, 4, 8, 9, 13, 24, 24, 29));

        ListUtils.addToSortedList(list, 35);
        assertThat(list).containsAll(List.of(3, 3, 4, 8, 9, 13, 24, 24, 29, 35));

        ListUtils.addToSortedList(list, 35);
        assertThat(list).containsAll(List.of(3, 3, 4, 8, 9, 13, 24, 24, 29, 35, 35));

        ListUtils.addToSortedList(list, 11);
        assertThat(list).containsAll(List.of(3, 3, 4, 8, 9, 11, 13, 24, 24, 29, 35, 35));
    }

    @Test
    public void testBinarySearch() {
        assertEquals(Optional.empty(), binarySearch(List.of(), 5));
        assertEquals(Optional.of(0), binarySearch(List.of(5), 5));
        assertEquals(Optional.empty(), binarySearch(List.of(5), 10));
        assertEquals(Optional.of(2), binarySearch(List.of(1, 3, 5, 7, 9), 5));
        assertEquals(Optional.of(3), binarySearch(List.of(1, 3, 5, 6, 7, 9), 6));
        assertEquals(Optional.of(2), binarySearch(List.of(1, 3, 5, 6, 7, 9), 5));
        assertEquals(Optional.of(1), binarySearch(List.of(1, 3, 5, 6, 7, 9), 3));
        assertEquals(Optional.of(4), binarySearch(List.of(1, 3, 5, 6, 7, 9), 7));
        assertEquals(Optional.of(5), binarySearch(List.of(1, 3, 5, 6, 7, 9), 9));
        assertEquals(Optional.of(0), binarySearch(List.of(1, 3, 5, 6, 7, 9), 1));
        assertEquals(Optional.empty(), binarySearch(List.of(1, 3, 5, 7, 8, 9), 6));
        assertEquals(Optional.empty(), binarySearch(List.of(1, 3, 5, 7, 9), 6));
        assertEquals(Optional.of(0), binarySearch(List.of(1, 3, 5, 7, 9), 1));
        assertEquals(Optional.of(4), binarySearch(List.of(1, 3, 5, 7, 9), 9));
        assertEquals(Optional.of(2), binarySearch(List.of(1, 3, 5, 5, 7, 9), 5));
        assertEquals(Optional.empty(), binarySearch(List.of(1, 3, 5, 5, 7, 9), 6));
        assertEquals(Optional.of(999999), binarySearch(generateLargeList(1000000), 999999));
        assertEquals(Optional.of(1), binarySearch(List.of(-10, -5, 0, 5, 10), -5));
        assertEquals(Optional.empty(), binarySearch(List.of(-50, -40, -30, -20, -10), 0));
        assertEquals(Optional.of(2), binarySearch(List.of(0, 0, 0, 0, 0), 0));
        assertEquals(Optional.of(2), binarySearch(List.of(3, 1, 4, 5, 2), 4));
    }

    private static List<Integer> generateLargeList(int size) {
        return IntStream.range(0, size).boxed().toList();
    }
}