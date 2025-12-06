package aoc2025;

import common.ListUtils;
import common.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static common.DirectionWithDiagonal.traverseAllDirectionsPointsInsideMatrix;

public class Day5 {

    private static final Logger log = LogManager.getLogger(Day5.class);

    public static long part1(String input) {
        Pair<List<Pair<Long, Long>>, List<Long>> parsedInput = parse(input);

        return part1(parsedInput);
    }

    public static Long part1(Pair<List<Pair<Long, Long>>, List<Long>> input) {
        List<Pair<Long, Long>> ranges = input.getLeft();
        ranges = mergeRanges(ranges);

        return processValues(input, ranges);
    }

    private static Long processValues(Pair<List<Pair<Long, Long>>, List<Long>> input, List<Pair<Long, Long>> ranges) {
        List<Long> values = input.getRight();
        return values.stream()
                .map(it -> inAnyRange(ranges, it))
                .map(it -> it ? 1L : 0L)
                .reduce(0L, Long::sum);
    }

    public static boolean inAnyRange(List<Pair<Long, Long>> ranges, Long value) {
        return ListUtils.binarySearch(ranges, Pair.of(value, value), (e1, e2) -> {
            if (e1.getLeft() > e2.getRight()) {
                return 1;
            } else if (e1.getRight() < e2.getLeft()) {
                return -1;
            } else {
                return 0;
            }
        }).isPresent();
    }

    public static ArrayList<Pair<Long, Long>> mergeRanges(List<Pair<Long, Long>> rangesInput) {
        ArrayList<Pair<Long, Long>> ranges = new ArrayList<>(rangesInput);
        ranges.sort(Comparator.comparing(Pair::getLeft));

        for (int i = 0; i < ranges.size() - 1; i++) {
            Pair<Long, Long> currentRange = ranges.get(i);
            Pair<Long, Long> nextRange = ranges.get(i + 1);

            while (i + 1 < ranges.size() && doOverlap((nextRange = ranges.get(i + 1)), currentRange)) {
                ranges.remove(i + 1);
                if (nextRange.getRight() > currentRange.getRight()) {
                    Pair<Long, Long> newRange = Pair.of(currentRange.getLeft(), nextRange.getRight());
                    currentRange = newRange;
                    ranges.set(i, newRange);
                };
            }
        }
        return ranges;
    }

    private static boolean doOverlap(Pair<Long, Long> nextRange, Pair<Long, Long> currentRange) {
        return nextRange.getLeft() <= currentRange.getRight();
    }

    public static Long part2(String input) {
        Pair<List<Pair<Long, Long>>, List<Long>> parsedInput = parse(input);

        return part2(parsedInput);
    }

    public static Long part2(Pair<List<Pair<Long, Long>>, List<Long>> input) {
        List<Pair<Long, Long>> ranges = input.getLeft();
        ranges = mergeRanges(ranges);

        return ranges.stream()
                .map(range -> range.getRight() - range.getLeft() + 1)
                .reduce(0L, Long::sum);
    }

    public static Pair<List<Pair<Long, Long>>, List<Long>> parse(String input) {
        String[] parts = input.split("\n\n");
        String ranges = parts[0];

        List<Pair<Long, Long>> parsedRanges = Arrays.stream(ranges.split("\n")).map(
                it -> {
                    String[] rangeParts = it.split("-");
                    return Pair.of(Long.parseLong(rangeParts[0]), Long.parseLong(rangeParts[1]));
                }
        ).toList();
        String values = parts[1];
        List<Long> parsedValues = Arrays.stream(values.split("\n")).map(
                        Long::parseLong)
                .toList();
        return Pair.of(parsedRanges, parsedValues);
    }
}
