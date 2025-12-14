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

class Day10Test {

    private static final Logger log = LogManager.getLogger(Day10Test.class);

    @Test
    public void testPart1Short() {
        String input = readFile("inputs/aoc2025/day10-short");
        long result = Day10.part1(input);
        assertThat(result).isEqualTo(7);
    }

    @Test
    public void testPart1Full() {
        String input = readFile("inputs/aoc2025/day10-full");
        long result = Day10.part1(input);
        assertThat(result).isEqualTo(415);
    }

    @Test
    public void testPart2Short() {
        String input = readFile("inputs/aoc2025/day10-short");
        Long result = Day10.part2(input);
        assertThat(result).isEqualTo(24L);
    }

    @Test
    public void testPart2Full() {
        String input = readFile("inputs/aoc2025/day10-full");
        Long result = Day10.part2(input);
        assertThat(result).isEqualTo(1476550548L);
    }

    @Test
    public void measureSpeed() {
        List<Pair<Integer, List<Integer>>> input = Day10.parse(readFile("inputs/aoc2025/day10-full"));
        StopWatchGauge.run(() -> Day10.part1(input), 1000, Task.FIRST);
//        StopWatchGauge.run(() -> Day10.part2(input), 10, Task.SECOND);
    }
}