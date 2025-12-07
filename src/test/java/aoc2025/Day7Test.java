package aoc2025;

import common.Pair;
import common.Point;
import common.StopWatchGauge;
import common.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static common.FileReader.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class Day7Test {

    private static final Logger log = LogManager.getLogger(Day7Test.class);

    @Test
    public void testPart1Short() {
        String input = readFile("inputs/aoc2025/day7-short");
        Long result = Day7.part1(input);
        assertThat(result).isEqualTo(21L);
    }

    @Test
    public void testPart1Full() {
        String input = readFile("inputs/aoc2025/day7-full");
        Long result = Day7.part1(input);
        assertThat(result).isEqualTo(1504L);
    }

    @Test
    public void testPart2Short() {
        String input = readFile("inputs/aoc2025/day7-short");
        Long result = Day7.part2(input);
        assertThat(result).isEqualTo(40L);
    }

    @Test
    public void testPart2Full() {
        String input = readFile("inputs/aoc2025/day7-full");
        Long result = Day7.part2(input);
        assertThat(result).isEqualTo(34284458938L);
    }

    @Test
    public void measureSpeed() {
        Pair<List<Point>, Point> input = Day7.parse(readFile("inputs/aoc2025/day7-full"));
        StopWatchGauge.run(() -> Day7.part1(input), 10, Task.FIRST);
        StopWatchGauge.run(() -> Day7.part2(input), 10, Task.SECOND);
    }
}