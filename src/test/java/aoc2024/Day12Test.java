package aoc2024;

import common.StopWatchGauge;
import common.Task;
import java.util.List;
import org.junit.jupiter.api.Test;

import static aoc2024.inputs.Day12Input.FULL;
import static aoc2024.inputs.Day12Input.SHORT_1;
import static aoc2024.inputs.Day12Input.SHORT_2;
import static aoc2024.inputs.Day12Input.SHORT_3;
import static aoc2024.inputs.Day12Input.SHORT_4;
import static aoc2024.inputs.Day12Input.SHORT_5;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

    @Test
    public void testTask1Short() {
        assertEquals(140, new Day12(SHORT_1).getTask1Result());
        assertEquals(772, new Day12(SHORT_2).getTask1Result());
        assertEquals(1930, new Day12(SHORT_3).getTask1Result());
    }

    @Test
    public void testTask1Full() {
        assertEquals(1375476, new Day12(FULL).getTask1Result());
    }

    @Test
    public void testTask2Short() {
        assertEquals(80, new Day12(SHORT_1).getTask2Result());
        assertEquals(436, new Day12(SHORT_2).getTask2Result());
        assertEquals(236, new Day12(SHORT_4).getTask2Result());
        assertEquals(368, new Day12(SHORT_5).getTask2Result());
    }

    @Test
    public void testTask2Full() {
        assertEquals(1375476, new Day12(FULL).getTask2Result());
    }

    @Test
    public void measureSpeed() {
        List<List<Character>> input = Day12.parseInput(FULL);

        StopWatchGauge.run(() -> new Day12(input).getTask1Result(), 100, Task.FIRST);
        StopWatchGauge.run(() -> new Day12(input).getTask2Result(), 100, Task.FIRST);
//        StopWatchGauge.run(() -> new Day11("4022724 951333 0 21633 5857 97 702 6", 75).getStonesNumberTask1(), 10, Task.SECOND);
    }

}