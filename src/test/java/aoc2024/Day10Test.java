package aoc2024;

import common.StopWatchGauge;
import common.Task;
import aoc2024.inputs.Day10Input;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day10Test {

    @Test
    public void testShort() {
        assertEquals(2, new Day10(Day10Input.SHORT_1).getNumberOfTrails());
        assertEquals(4, new Day10(Day10Input.SHORT_2).getNumberOfTrails());
        assertEquals(3, new Day10(Day10Input.SHORT_3).getNumberOfTrails());
        assertEquals(36, new Day10(Day10Input.SHORT).getNumberOfTrails());
    }

    @Test
    public void testFull() {
        assertEquals(2, new Day10(Day10Input.FULL).getNumberOfTrails());
    }

    @Test
    public void testShortTask2() {
        assertEquals(81, new Day10(Day10Input.SHORT).getNumberOfTrailsTask2());
    }

    @Test
    public void testFullTask2() {
        assertEquals(1062, new Day10(Day10Input.FULL).getNumberOfTrailsTask2());
    }

    @Test
    public void measureSpeed() {
        StopWatchGauge.run(() -> new Day10(Day10Input.FULL).getNumberOfTrails(), 10, Task.FIRST);
        StopWatchGauge.run(() -> new Day10(Day10Input.FULL).getNumberOfTrailsTask2(), 10, Task.SECOND);
    }

}