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

class Day12Test {

    private static final Logger log = LogManager.getLogger(Day12Test.class);

    @Test
    public void testPart1Short() {
        String input = readFile("inputs/aoc2025/day12-short");
        long result = Day12.part1(input);
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void testPart1Full() {
        String input = readFile("inputs/aoc2025/day12-full");
        long result = Day12.part1(input);
        assertThat(result).isEqualTo(495);
    }

    @Test
    public void measureSpeed() {
        Day12.Input input = Day12.parse(readFile("inputs/aoc2025/day12-full"));
        StopWatchGauge.run(() -> Day12.part1(input), 1000, Task.FIRST);
    }
}