package aoc2025;

import common.Pair;
import common.StopWatchGauge;
import common.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static aoc2025.Day1.parse;
import static common.FileReader.readFile;
import static common.Number.number;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class Day2Test {

    private static final Logger log = LogManager.getLogger(Day2Test.class);

    @Test
    public void part1Minis() {
        List<Long> result3 = Day2.part1ProcessRange(Pair.of(22L, 33L));
        assertThat(result3).containsExactlyElementsOf(List.of(22L, 33L));

        List<Long> result = Day2.part1ProcessRange(Pair.of(95L, 115L));
        assertThat(result).containsExactlyElementsOf(List.of(99L));
//
        List<Long> result2 = Day2.part1ProcessRange(Pair.of(998L, 1012L));
        assertThat(result2).containsExactlyElementsOf(List.of(1010L));

        result = Day2.part1ProcessRange(Pair.of(1188511880L, 1188511890L));
        assertThat(result).containsExactlyElementsOf(List.of(1188511885L));

        result = Day2.part1ProcessRange(Pair.of(222220L, 222224L));
        assertThat(result).containsExactlyElementsOf(List.of(222222L));

        List<Long> result4 = Day2.part1ProcessRange(Pair.of(1698522L, 1698528L));
        assertThat(result4).isEmpty();

        result = Day2.part1ProcessRange(Pair.of(446443L, 446449L));
        assertThat(result).containsExactlyElementsOf(List.of(446446L));

        result = Day2.part1ProcessRange(Pair.of(38593856L, 38593862L));
        assertThat(result).containsExactlyElementsOf(List.of(38593859L));
    }

    @Test
    public void part1Minis2() {
        List<Long> result3 = Day2.part1ProcessRange(Pair.of(853L, 1994L));
        List<Long> expected = List.of(1010, 1111, 1212, 1313, 1414, 1515, 1616, 1717, 1818, 1919)
                .stream().map(Long::valueOf).toList();
        assertThat(result3).containsExactlyElementsOf(expected);

        List<Long> result4 = Day2.part1ProcessRange(Pair.of(62668L, 105646L));
        List<Long> expected2 = List.of(100100, 101101, 102102, 103103, 104104, 105105)
                .stream().map(Long::valueOf).toList();
        assertThat(result4).containsExactlyElementsOf(expected2);

        List<Long> result5 = Day2.part1ProcessRange(Pair.of(3L, 20L));
        List<Long> expected3 = List.of(11)
                .stream().map(Long::valueOf).toList();
        assertThat(result5).containsExactlyElementsOf(expected3);

        List<Long> result6 = Day2.part1ProcessRange(Pair.of(998L, 1012L));
        List<Long> expected4 = List.of(1010)
                .stream().map(Long::valueOf).toList();
        assertThat(result6).containsExactlyElementsOf(expected4);
    }

    @Test
    public void part1MinisBruteForce() {
        List<BigDecimal> result3 = Day2.part1ProcessRangeBruteForce(Pair.of(22L, 33L));
        assertThat(result3).containsExactlyElementsOf(List.of(number(22L), number(33L)));

        List<BigDecimal> result = Day2.part1ProcessRangeBruteForce(Pair.of(95L, 115L));
        assertThat(result).containsExactlyElementsOf(List.of(number(99)));
//
        List<BigDecimal> result2 = Day2.part1ProcessRangeBruteForce(Pair.of(998L, 1012L));
        assertThat(result2).containsExactlyElementsOf(List.of(number(1010)));

        result = Day2.part1ProcessRangeBruteForce(Pair.of(1188511880L, 1188511890L));
        assertThat(result).containsExactlyElementsOf(List.of(number(1188511885L)));

        result = Day2.part1ProcessRangeBruteForce(Pair.of(222220L, 222224L));
        assertThat(result).containsExactlyElementsOf(List.of(number(222222)));

        List<BigDecimal> result4 = Day2.part1ProcessRangeBruteForce(Pair.of(1698522L, 1698528L));
        assertThat(result4).isEmpty();

        result = Day2.part1ProcessRangeBruteForce(Pair.of(446443L, 446449L));
        assertThat(result).containsExactlyElementsOf(List.of(number(446446)));

        result = Day2.part1ProcessRangeBruteForce(Pair.of(38593856L, 38593862L));
        assertThat(result).containsExactlyElementsOf(List.of(number(38593859L)));
    }

    @Test
    public void testPart1Short() {
        String input = readFile("inputs/aoc2025/day2-short");
        Long result = Day2.part1(input);
        assertThat(result).isEqualTo(1227775554L);
    }

    @Test
    public void testPart1Full() {
        String input = readFile("inputs/aoc2025/day2-full");
        Long result = Day2.part1(input);
        assertThat(result).isEqualTo(23701357374L);
    }


    @Test
    public void testPart2Short() {
        String input = readFile("inputs/aoc2025/day2-short");
        BigDecimal result = Day2.part2(input);
        assertThat(result).isEqualTo(new BigDecimal(3));
    }

    @Test
    public void testPart2Full() {
        String input = readFile("inputs/aoc2025/day2-full");
        BigDecimal result = Day2.part2(input);
        assertThat(result).isEqualTo(new BigDecimal(3));
    }

    @Test
    public void measureSpeed() {
        List<Pair<Long, Long>> input = Day2.parse(readFile("inputs/aoc2025/day2-full"));
        StopWatchGauge.run(() -> Day2.part1(input), 10, Task.FIRST);
//        StopWatchGauge.run(() -> Day2.part2(input), 10, Task.SECOND);
    }
}