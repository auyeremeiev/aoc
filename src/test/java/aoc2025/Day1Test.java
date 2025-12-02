package aoc2025;

import aoc2024.Day10;
import aoc2024.inputs.Day10Input;
import common.StopWatchGauge;
import common.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static aoc2025.Day1.parse;
import static common.FileReader.readFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

class Day1Test {

    @Test
    public void testPart1Short() {
        String input = readFile("inputs/aoc2025/day1-short");
        long result = Day1.part1(input);
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void testPart1Full() {
        String input = readFile("inputs/aoc2025/day1-full");
        long result = Day1.part1(input);
        assertThat(result).isEqualTo(1100);
    }


    @Test
    public void testPart2Short() {
        String input = readFile("inputs/aoc2025/day1-short");
        long result = Day1.part2(input);
        assertThat(result).isEqualTo(6);
    }

    @Test
    public void testPart2Full() {
        String input = readFile("inputs/aoc2025/day1-full");
        long result = Day1.part2(input);
        assertThat(result).isEqualTo(1100);
    }

    @Test
    public void measureSpeed() {
        List<Long> input = parse(readFile("inputs/aoc2025/day1-full"));
        StopWatchGauge.run(() -> Day1.part1(input), 10, Task.FIRST);
        StopWatchGauge.run(() -> Day1.part2(input), 10, Task.SECOND);
    }
}