package aoc2025;

import common.Pair;
import common.StopWatchGauge;
import common.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static common.FileReader.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    private static final Logger log = LogManager.getLogger(Day11Test.class);

    @Test
    public void testPart1Short() {
        String input = readFile("inputs/aoc2025/day11-short");
        long result = Day11.part1(input);
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void testPart1Full() {
        String input = readFile("inputs/aoc2025/day11-full");
        long result = Day11.part1(input);
        assertThat(result).isEqualTo(690);
    }

    @Test
    public void testPart2Short() {
        String input = readFile("inputs/aoc2025/day11-2-short");
        long result = Day11.part2(input);
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testPart2Full() {
        String input = readFile("inputs/aoc2025/day11-full");
        long result = Day11.part2(input);
        assertThat(result).isEqualTo(557332758684000L);
    }

    @Test
    public void measureSpeed() {
        List<Pair<String, List<String>>> input = Day11.parse(readFile("inputs/aoc2025/day11-full"));
        StopWatchGauge.run(() -> Day11.part1(input), 1000, Task.FIRST);
        StopWatchGauge.run(() -> Day11.part2(input), 100, Task.SECOND);
    }
}