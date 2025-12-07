package aoc2025;

import common.Pair;
import common.StopWatchGauge;
import common.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static aoc2025.Day3.part2LineProcessor;
import static common.FileReader.readFile;
import static common.Number.number;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

class Day3Test {

    private static final Logger log = LogManager.getLogger(Day3Test.class);

    @Test
    public void part1Minis() {
//        assertThat(Day3.part1LineProcessor(9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 1, 1, 1, 1, 1)).isEqualTo(98);
        assertThat(Day3.part1LineProcessor("234234234234278")).isEqualTo(78);
    }

    @Test
    public void testPart1Short() {
        String input = readFile("inputs/aoc2025/day3-short");
        Long result = Day3.part1(input);
        assertThat(result).isEqualTo(357);
    }

    @Test
    public void testPart1Full() {
        String input = readFile("inputs/aoc2025/day3-full");
        Long result = Day3.part1(input);
        assertThat(result).isEqualTo(23701357374L);
    }

    @Test
    public void testPart2Minis() {
        assertThat(part2LineProcessor("987654321111111")).isEqualTo(987654321111L);
        assertThat(part2LineProcessor("811111111111119")).isEqualTo(811111111119L);
        assertThat(part2LineProcessor("234234234234278")).isEqualTo(434234234278L);
        assertThat(part2LineProcessor("818181911112111")).isEqualTo(888911112111L);
    }

    @Test
    public void testPart2Short() {
        String input = readFile("inputs/aoc2025/day3-short");
        Long result = Day3.part2(input);
        assertThat(result).isEqualTo(3121910778619L);
    }

    @Test
    public void testPart2Full() {
        String input = readFile("inputs/aoc2025/day3-full");
        Long result = Day3.part2(input);
        assertThat(result).isEqualTo(34284458938L);
    }

    @Test
    public void measureSpeed() {
        List<int[]> input = Day3.parse(readFile("inputs/aoc2025/day3-full"));
        StopWatchGauge.run(() -> Day3.part1(input), 1000, Task.FIRST);
        StopWatchGauge.run(() -> Day3.part2(input), 1000, Task.SECOND);
    }
}