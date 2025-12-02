package aoc2025;

import org.junit.jupiter.api.Test;

import static common.FileReader.readFile;
import static org.assertj.core.api.Assertions.assertThat;

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
}