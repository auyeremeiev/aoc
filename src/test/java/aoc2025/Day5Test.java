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

class Day5Test {

    private static final Logger log = LogManager.getLogger(Day5Test.class);

    @Test
    public void testPart1Short() {
        String input = readFile("inputs/aoc2025/day5-short");
        Long result = Day5.part1(input);
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void testPart1Full() {
        String input = readFile("inputs/aoc2025/day5-full");
        Long result = Day5.part1(input);
        assertThat(result).isEqualTo(1553L);
    }

    @Test
    public void testPart2Short() {
        String input = readFile("inputs/aoc2025/day5-short");
        Long result = Day5.part2(input);
        assertThat(result).isEqualTo(14L);
    }

    @Test
    public void testPart2Full() {
        String input = readFile("inputs/aoc2025/day5-full");
        Long result = Day5.part2(input);
        assertThat(result).isEqualTo(34284458938L);
    }

    @Test
    public void measureSpeed() {
        Pair<List<Pair<Long, Long>>, List<Long>> input = Day5.parse(readFile("inputs/aoc2025/day5-full"));
        StopWatchGauge.run(() -> Day5.part1(input), 1000, Task.FIRST);
        StopWatchGauge.run(() -> Day5.part2(input), 1000, Task.SECOND);
    }
}