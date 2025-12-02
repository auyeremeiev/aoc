package aoc2024;

import common.Point;
import common.StopWatchGauge;
import common.Task;
import aoc2024.inputs.Day14Input;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Day14Test {

    @Test
    public void testShort() {
        assertEquals(12, new Day14(Day14.parseInput(Day14Input.SHORT), 7, 11, 100).getTask1Result());
    }

    @Test
    public void testFull() {
        assertEquals(12, new Day14(Day14.parseInput(Day14Input.FULL)).getTask1Result());
    }

    @Test
    public void testFullTask2() {
        assertEquals(7709, new Day14(Day14.parseInput(Day14Input.FULL)).getTask2Result());
    }

    @Test
    public void testPointsInVerticalLine() {
        Set<Point> points = Set.of(
                new Point(3, 1),
                new Point(4, 1),
                new Point(2, 1),
                new Point(5, 1),
                new Point(1, 1),
                new Point(6, 1),
                new Point(8, 1),
                new Point(9, 1),
                new Point(10, 1),
                new Point(11, 1),
                new Point(12, 1),
                new Point(13, 1),
                new Point(7, 1),
                new Point(1, 3)
        );

        assertEquals(10, Day14.pointsInVerticalLine(points, new Point(3, 1), 10).size());
        assertTrue(Day14.containsAnyShape(points));
    }

    @Test
    public void testAllPositions() {
        Map<Point, Map<Point, Integer>> allPositionsWithin = new Day14(Day14.parseInput(Day14Input.FULL)).getAllPositionsWithin(12000);
        assertEquals(489, allPositionsWithin.size());
    }


    @Test
    public void measureSpeed() {
        StopWatchGauge.run(() -> new Day14(Day14.parseInput(Day14Input.FULL)).getTask1Result(), 100, Task.FIRST);
        StopWatchGauge.run(() -> new Day14(Day14.parseInput(Day14Input.FULL)).getTask2Result(), 3, Task.SECOND);
    }

}