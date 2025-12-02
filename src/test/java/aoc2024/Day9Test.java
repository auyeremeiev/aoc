package aoc2024;

import common.Pair;
import common.StopWatchGauge;
import common.Task;
import aoc2024.helpers.Day9EmptyBlock;
import aoc2024.helpers.Day9FileBlock;
import aoc2024.inputs.Day9Input;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

    @Test
    public void testTask1Short() {
        Day9 day9 = new Day9(Day9Input.TASK_1_SHORT);
        assertEquals(
                List.of(
                        Pair.of(0, 2),
                        Pair.of(9, 2),
                        Pair.of(8, 1),
                        Pair.of(1, 3),
                        Pair.of(8, 3),
                        Pair.of(2, 1),
                        Pair.of(7, 3),
                        Pair.of(3, 3),
                        Pair.of(6, 1),
                        Pair.of(4, 2),
                        Pair.of(6, 1),
                        Pair.of(5, 4),
                        Pair.of(6, 1),
                        Pair.of(6, 1)
                ), day9.getTask1ResultNumbers()
        );
        assertEquals(1928, new Day9("2333133121414131402").getTask1ResultSum());
    }

    @Test
    public void testTask1Full() {
        Day9 day9 = new Day9(Day9Input.TASK_1_FULL);
        assertEquals(1928, day9.getTask1ResultSum());
    }

    @Test
    public void testTask2VeryShort() {
        assertEquals(
                List.of(
                        new Day9FileBlock(1, 3),
                        new Day9EmptyBlock(3),
                        new Day9EmptyBlock(0)
                ), new Day9("0330").getTask2ResultNumbers()
        );
        assertEquals(3, new Day9("0330").getTask2ResultSum());
        assertEquals(31, new Day9("0303412").getTask2ResultSum());

        assertEquals(169, new Day9("1313165").getTask2ResultSum());
        assertEquals(1, new Day9("111").getTask2ResultSum());
        assertEquals(19, new Day9("22222").getTask2ResultSum());
        assertEquals(215, new Day9("1111111111111111111").getTask2ResultSum());
        assertEquals(4193, new Day9("90817263544536271819").getTask2ResultSum());
        assertEquals(132, new Day9("12345").getTask2ResultSum());
        assertEquals(1715, new Day9("80893804751608292").getTask2ResultSum());
        assertEquals(4, new Day9("01010101101").getTask2ResultSum());
        assertEquals(26, new Day9("00122002200").getTask2ResultSum());
        assertEquals(87, new Day9("1404345").getTask2ResultSum());

        assertEquals(12523, new Day9("28519851383385783991").getTask2ResultSum());
    }

    @Test
    public void testTask2Short() {
        Day9 day9 = new Day9(Day9Input.TASK_1_SHORT);
        assertEquals(
                List.of(
                        new Day9FileBlock(0, 2),
                        new Day9FileBlock(9, 2),
                        new Day9FileBlock(2, 1),
                        new Day9FileBlock(1, 3),
                        new Day9FileBlock(7, 3),
                        new Day9EmptyBlock(1),
                        new Day9FileBlock(4, 2),
                        new Day9EmptyBlock(1),
                        new Day9FileBlock(3, 3),
                        new Day9EmptyBlock(1),
                        new Day9EmptyBlock(2),
                        new Day9EmptyBlock(1),
                        new Day9FileBlock(5, 4),
                        new Day9EmptyBlock(1),
                        new Day9FileBlock(6, 4),
                        new Day9EmptyBlock(1),
                        new Day9EmptyBlock(3),
                        new Day9EmptyBlock(1),
                        new Day9FileBlock(8, 4),
                        new Day9EmptyBlock(0),
                        new Day9EmptyBlock(2)
                ), day9.getTask2ResultNumbers()
        );
        assertEquals(2858, new Day9("2333133121414131402").getTask2ResultSum());
    }

    @Test
    public void testTask2Full() {
        Day9 day9 = new Day9(Day9Input.TASK_2_FULL);
        assertEquals(6311837662089L, day9.getTask2ResultSum());
    }

    @Test
    public void testTask2Full_otherTests() {
        Day9 day9 = new Day9(Day9Input.TASK_2_FULL_2);
        assertEquals(97898222299196L, day9.getTask2ResultSum());
    }

    @Test
    public void measureSpeed() {
        StopWatchGauge.run(() -> new Day9(Day9Input.TASK_1_FULL).getTask1ResultSum(), 10, Task.FIRST);
        StopWatchGauge.run(() -> new Day9(Day9Input.TASK_2_FULL).getTask2ResultSum(), 10, Task.SECOND);
    }
}