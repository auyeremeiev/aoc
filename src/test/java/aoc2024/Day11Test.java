package aoc2024;

import common.StopWatchGauge;
import common.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day11Test {

    @Test
    public void testShort() {
        assertEquals(5L, new Day11("125 17", 3).getStonesNumberTask1());
        assertEquals(9L, new Day11("125 17", 4).getStonesNumberTask1());
        assertEquals(13L, new Day11("125 17", 5).getStonesNumberTask1());
        assertEquals(22L, new Day11("125 17", 6).getStonesNumberTask1());
        assertEquals(55312L, new Day11("125 17", 25).getStonesNumberTask1());
        assertEquals(55312L, new Day11("4022724 951333 0 21633 5857 97 702 6", 25).getStonesNumberTask1());
    }

    @Test
    public void testLong() {
        assertEquals(250783680217283L, new Day11("4022724 951333 0 21633 5857 97 702 6", 75).getStonesNumberTask1());
    }

    @Test
    public void measureSpeed() {
        StopWatchGauge.run(() -> new Day11("4022724 951333 0 21633 5857 97 702 6", 25).getStonesNumberTask1(), 10, Task.FIRST);
        StopWatchGauge.run(() -> new Day11("4022724 951333 0 21633 5857 97 702 6", 75).getStonesNumberTask1(), 10, Task.SECOND);
    }

}