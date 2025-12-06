package aoc2025;

import common.StopWatchGauge;
import common.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static aoc2025.Day3.part2LineProcessor;
import static common.FileReader.readFile;
import static org.assertj.core.api.Assertions.assertThat;

class Day4Test {

    private static final Logger log = LogManager.getLogger(Day4Test.class);

    @Test
    public void testPart1Short() {
        String input = readFile("inputs/aoc2025/day4-short");
        Long result = Day4.part1(input);
        assertThat(result).isEqualTo(13);
    }

    @Test
    public void testPart1Full() {
        String input = readFile("inputs/aoc2025/day4-full");
        Long result = Day4.part1(input);
        assertThat(result).isEqualTo(1553L);
    }

    @Test
    public void testPart2Short() {
        String input = readFile("inputs/aoc2025/day4-short");
        Long result = Day4.part2(input);
        assertThat(result).isEqualTo(43L);
    }

    @Test
    public void testPart2Full() {
        String input = readFile("inputs/aoc2025/day4-full");
        Long result = Day4.part2(input);
        assertThat(result).isEqualTo(8442L);
    }

    @Test
    public void measureSpeed() {
        List<List<Boolean>> input = Day4.parse(readFile("inputs/aoc2025/day4-full"));
        StopWatchGauge.run(() -> Day4.part1(input), 10, Task.FIRST);
        StopWatchGauge.run(() -> {
            List<List<Boolean>> grid = new ArrayList<>(input);
            return Day4.part2(grid);
        }, 1, Task.SECOND);
    }
}