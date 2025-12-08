package aoc2025;

import common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.List;

import static common.FileReader.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class Day8Test {

    private static final Logger log = LogManager.getLogger(Day8Test.class);

    @Test
    public void testPart1Short() {
        String input = readFile("inputs/aoc2025/day8-short");
        int result = Day8.part1(input, 10);
        assertThat(result).isEqualTo(40L);
    }

    @Test
    public void testPart1Full() {
        String input = readFile("inputs/aoc2025/day8-full");
        int result = Day8.part1(input, 1000);
        assertThat(result).isEqualTo(129564L);
    }

    @Test
    public void testPart2Short() {
        String input = readFile("inputs/aoc2025/day8-short");
        Long result = Day8.part2(input);
        assertThat(result).isEqualTo(25272L);
    }

    @Test
    public void testPart2Full() {
        String input = readFile("inputs/aoc2025/day8-full");
        Long result = Day8.part2(input);
        assertThat(result).isEqualTo(34284458938L);
    }

    @Test
    public void measureSpeed() {
        List<Node3D> input = Day8.parse(readFile("inputs/aoc2025/day8-full"));
        StopWatchGauge.run(() -> Day8.part1(input, 1000), 100, Task.FIRST);
        StopWatchGauge.run(() -> Day8.part2(input), 100, Task.SECOND);
    }
}