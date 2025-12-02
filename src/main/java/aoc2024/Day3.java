package aoc2024;

import common.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    private static final Pattern PATTERN = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
    private static final Pattern PATTERN_SUBTASK_2 = Pattern.compile("(mul\\((\\d+),(\\d+))\\)|(don't\\(\\))|(do\\(\\))");

    public static long getResultSubtask1(String input) {
        List<Pair<Long, Long>> multipliers = parseInputSubtask1(input);
        return calculate(multipliers);
    }

    public static List<Pair<Long, Long>> parseInputSubtask1(String input) {
        List<Pair<Long, Long>> multipliers = new ArrayList<>();
        Matcher matcher = PATTERN.matcher(input);
        while(matcher.find()) {
            multipliers.add(parsePair(matcher));
        }

        return multipliers;
    }

    public static long getResultSubtask2(String input) {
        List<Pair<Long, Long>> multipliers = parseInputSubtask2(input);
        return calculate(multipliers);
    }

    private static Long calculate(List<Pair<Long, Long>> multipliers) {
        return multipliers.stream()
                .map(it -> it.getLeft() * it.getRight())
                .reduce(0L, Long::sum);
    }

    public static List<Pair<Long, Long>> parseInputSubtask2(String input) {
        List<Pair<Long, Long>> multipliers = new ArrayList<>();
        Matcher matcher = PATTERN_SUBTASK_2.matcher(input);

        if (matcher.find()) {
            multipliers.add(parsePair(matcher));
        }

        boolean shouldDo = true;
        while(matcher.find()) {
            Boolean parsedShouldDo = shouldDo(matcher);
            if (parsedShouldDo != null) {
                shouldDo = parsedShouldDo;
            } else  {
                if (shouldDo) {
                    multipliers.add(parsePair(matcher));
                }
            }
        }

        return multipliers;
    }

    private static Pair<Long, Long> parsePair(Matcher matcher) {
        return Pair.of(Long.parseLong(matcher.group(2)), Long.parseLong(matcher.group(3)));
    }

    private static Boolean shouldDo(Matcher matcher) {
        if (matcher.group(5) != null) {
            return true;
        }

        if (matcher.group(4) != null) {
            return false;
        }

        return null;
    }
}
