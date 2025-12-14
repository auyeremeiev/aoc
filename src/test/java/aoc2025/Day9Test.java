package aoc2025;

import common.Node3D;
import common.Point;
import common.StopWatchGauge;
import common.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static common.FileReader.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class Day9Test {

    private static final Logger log = LogManager.getLogger(Day9Test.class);

    @Test
    public void testPart1Short() {
        String input = readFile("inputs/aoc2025/day9-short");
        long result = Day9.part1(input);
        assertThat(result).isEqualTo(50L);
    }

    @Test
    public void testPart1Full() {
        String input = readFile("inputs/aoc2025/day9-full");
        long result = Day9.part1(input);
        assertThat(result).isEqualTo(4776100539L);
    }

    @Test
    public void testPart2Short() {
        String input = readFile("inputs/aoc2025/day9-short");
        Long result = Day9.part2(input);
        assertThat(result).isEqualTo(24L);
    }

    @Test
    public void testPart2Full() {
        String input = readFile("inputs/aoc2025/day9-full");
        Long result = Day9.part2(input);
        assertThat(result).isEqualTo(34284458938L);
    }

    @Test
    public void measureSpeed() {
        List<Point> input = Day9.parse(readFile("inputs/aoc2025/day9-full"));
        StopWatchGauge.run(() -> Day9.part1(input), 100, Task.FIRST);
        StopWatchGauge.run(() -> Day9.part2(input), 10, Task.SECOND);
    }
}