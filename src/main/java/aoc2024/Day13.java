package aoc2024;

import common.Pair;
import aoc2024.helpers.Day13Task;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {
    private final long BIG_NUMBER = 10000000000000L;

    private final static Pattern BUTTON_PATTERN = Pattern.compile(": X(\\+\\d+), Y(\\+\\d+)");
    private final static Pattern RESULT_PATTERN = Pattern.compile(": X=(\\d+), Y=(\\d+)");

    private final List<Day13Task> tasks;

    public Day13(String input) {
        this(parseInput(input));
    }

    public Day13(List<Day13Task> tasks) {
        this.tasks = tasks;
    }

    public long getTask1Result() {
        return tasks.stream().map(this::calculateTokensTask1)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(0L, Long::sum);
    }

    public long getTask2Result() {
        return tasks.stream().map(it -> {
                    return new Day13Task(
                            it.a(),
                            it.b(),
                            new Day13Task.Result(it.r().x() + BIG_NUMBER, it.r().y() + BIG_NUMBER));
                })
                .map(this::calculateTokensTask1)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(0L, Long::sum);
    }

    private Optional<Long> calculateTokensTask1(Day13Task day13Task) {
        Optional<Pair<Long, Long>> bestButtonPressesOpt = bestButtonPresses(day13Task);

        if (bestButtonPressesOpt.isEmpty()) {
            return Optional.empty();
        }

        return bestButtonPressesOpt.map(it -> it.getLeft() * 3 + it.getRight());
    }

    private Optional<Pair<Long, Long>> bestButtonPresses(Day13Task d) {
        long determinant = d.a().x() * d.b().y() - d.b().x() * d.a().y();

        if (determinant == 0) {
            if (d.a().x() > d.b().x() * 3) {
                return Optional.of(new Pair<>(d.r().x() / d.a().x(),  0L));
            } else {
                return Optional.of(new Pair<>(0L,  d.r().x() / d.b().x()));
            }
        }

        long u = (d.b().y() * d.r().x() - d.b().x() * d.r().y()) / determinant;
        long v = (d.a().x() * d.r().y() - d.a().y() * d.r().x()) / determinant;

        // If u or v is real number, fail
        if (u * d.a().x() + v * d.b().x() == d.r().x() && u * d.a().y() + v * d.b().y() == d.r().y()) {
            return Optional.of(new Pair<>(u, v));
        } else {
            return Optional.empty();
        }
    }

    private Optional<Pair<Long, Long>> buildResultForButton(long baseButtonPresses, long anotherButtonValue, boolean isButtonABase) {
        return isButtonABase ? Optional.of(new Pair<>(baseButtonPresses, anotherButtonValue))
                : Optional.of(new Pair<>(anotherButtonValue, baseButtonPresses));
    }


    public static List<Day13Task> parseInput(String input) {
        List<String> rawTasks = Arrays.stream(input.split("\\n\\n")).toList();
        return rawTasks.stream().map(Day13::parseRawTask).toList();
    }

    private static Day13Task parseRawTask(String rawTask) {
        String[] taskLines = rawTask.split("\n");
        return new Day13Task(
                parseButton(taskLines[0]),
                parseButton(taskLines[1]),
                parseResult(taskLines[2]));
    }

    private static Day13Task.Button parseButton(String rawButton) {
        Matcher buttonMatcher = BUTTON_PATTERN.matcher(rawButton);
        buttonMatcher.find();
        long x = Long.parseLong(buttonMatcher.group(1));
        long y = Long.parseLong(buttonMatcher.group(2));
        return new Day13Task.Button(x, y);
    }

    private static Day13Task.Result parseResult(String rawButton) {
        Matcher buttonMatcher = RESULT_PATTERN.matcher(rawButton);
        buttonMatcher.find();
        long x = Long.parseLong(buttonMatcher.group(1));
        long y = Long.parseLong(buttonMatcher.group(2));
        return new Day13Task.Result(x, y);
    }
}
