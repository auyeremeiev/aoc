package aoc2025;

import common.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static common.ListUtils.*;
import static common.Number.*;
import static java.lang.Math.abs;

public class Day2 {

    private static final Logger log = LogManager.getLogger(Day2.class);
    // 2325 - 6546
    // 2424
    // 2525
    // 2626

    /// 23 - 65
    ///
    ///
    //
    // 2320 - 65345647
    // ...
    // 9999
    // 100100
    // 101101
    //
    public static long part1(String input) {
        List<Pair<Long, Long>> numbers = parse(input);

        return part1(numbers);
    }

    public static long part1(List<Pair<Long, Long>> ranges) {
        return ranges.stream()
                .map(it -> {
                    List<Long> numbers = part1ProcessRange(it);
                    return numbers.stream().reduce(0L, Long::sum);
                })
                .reduce(0L, Long::sum);
    }

    public static List<Long> part1ProcessRange(Pair<Long, Long> range) {
        Long left = range.getLeft();
        Long right = range.getRight();

        Pair<Long, Long> leftSplit = digits(left) < digits(right) ? split(left) : splitLeftBigger(left);

        List<Long> result = new ArrayList<>();

        long leftStart = leftSplit.getLeft() >= leftSplit.getRight() ? leftSplit.getLeft() : leftSplit.getLeft() + 1;

        long i = left > 10 && digits(left) % 2 != 0 ? pow10(digits(leftSplit.getLeft())) :  leftStart;
        Long currentNumber = concat(i, i);
        while(currentNumber.compareTo(right) <= 0) {
            result.add(currentNumber);
            i++;
            currentNumber = concat(i, i);
        }

        return result;
    }

    public static List<BigDecimal> part1ProcessRangeBruteForce(Pair<Long, Long> range) {
        List<BigDecimal> result = new ArrayList<>();
        Long left = range.getLeft();
        Long right = range.getRight();

        Long i = left;
        while(i <= right) {
            Pair<Long, Long> split = split(i);
            if (digits(i) % 2 == 0 && split.getLeft().equals(split.getRight())) {
                result.add(number(i));
            }
            i++;
        }

        return result;
    }

    public static Long part2(String input) {
        List<Pair<Long, Long>> numbers = parse(input);

        return part2(numbers);
    }

    public static Long part2(List<Pair<Long, Long>> ranges) {
        return ranges.stream()
                .map(it -> {
                    List<Long> numbers = part2ProcessRangeBruteForce(it);
                    return numbers.stream().reduce(0L, Long::sum);
                })
                .reduce(0L, Long::sum);
    }

    public static List<Long> part2ProcessRangeBruteForce(Pair<Long, Long> range) {
        List<Long> result = new ArrayList<>();
        Long left = range.getLeft();
        Long right = range.getRight();

        long i = left;
        while(i <= right) {
            if (isInvalidIdPart2(i)) {
                result.add(i);
            }
//
//            if (isInvalidIdPart2UsingString(i) != isInvalidIdPart2(i)) {
//                log.info("Found difference when smarter algorithm returned wrong result! : {}. " +
//                        "Correct result: {}, wrong result: {}", i, isInvalidIdPart2UsingString(i), isInvalidIdPart2(i));
//            }

            i++;
        }

        return result;
    }

    public static boolean isInvalidIdPart2(final long i) {
        if (i < 10) {
            return false;
        }

        int digits = digits(i);
        List<Long> allPartitions = findAllDivisors(digits);

        allPartitions.remove(0);
        for (Long partitionSize : allPartitions) {
            if (partitionSize == digits) {
                if (allDigitsAreSame(i)) {
                    return true;
                }
            } else {
                List<Long> parts = splitIntoEqualParts(i, partitionSize);
                if (allElementsAreEqual(parts)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isInvalidIdPart2UsingString(final long i) {
        if (i < 10) {
            return false;
        }

        int digits = digits(i);
        List<Long> allPartitions = findAllDivisors(digits);

        allPartitions.remove(0);
        for (Long partitionSize : allPartitions) {
            List<Long> parts = splitIntoEqualPartsUsingString(i, partitionSize);
            if (allElementsAreEqual(parts)) {
                return true;
            }
        }

        return false;
    }

    public static List<Pair<Long, Long>> parse(String input) {
        String[] ranges = input.split(",");
        return Arrays.stream(ranges).map(
                it -> {
                    String[] rangeParts = it.split("-");
                    return Pair.of(Long.parseLong(rangeParts[0]), Long.parseLong(rangeParts[1]));
                }
        ).toList();
    }
}
