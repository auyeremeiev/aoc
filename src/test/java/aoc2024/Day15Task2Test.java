package aoc2024;

import common.StopWatchGauge;
import common.Task;
import aoc2024.inputs.Day15Task1Input;
import aoc2024.inputs.Day15Task2Input;
import org.junit.jupiter.api.Test;

import static aoc2024.Day15Task2.transformRaw;
import static org.junit.jupiter.api.Assertions.*;

class Day15Task2Test {

    @Test
    public void testTask2Short() {
        assertEquals(2028L, new Day15Task2(Day15Task2Input.SHORT).getTaskResult());
    }

    @Test
    public void testTask2Full() {
        String input = transformRaw(Day15Task2Input.FULL_RAW);
        assertEquals(1456590L, new Day15Task2(input).getTaskResult());
    }

    @Test
    public void measureSpeed() {
        StopWatchGauge.run(() -> new Day15Task1(Day15Task1Input.FULL).getTaskResult(), 100, Task.FIRST);
        StopWatchGauge.run(() -> new Day15Task2(transformRaw(Day15Task2Input.FULL_RAW)).getTaskResult(), 100, Task.SECOND);
    }
}