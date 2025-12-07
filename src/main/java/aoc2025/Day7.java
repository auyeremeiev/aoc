package aoc2025;

import common.Pair;
import common.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {

    private static final Logger log = LogManager.getLogger(Day7.class);

    public static long part1(String input) {
        Pair<List<Point>, Point> parsedInput = parse(input);

        return part1(parsedInput);
    }

    public static Long part1(Pair<List<Point>, Point> input) {
        List<Point> splitters = input.getLeft();

        Map<Integer, List<Integer>> pointsOnYs = splitters.stream().collect(
                Collectors.groupingBy(
                        Point::getRight,
                        Collectors.mapping(Pair::getLeft, Collectors.toList())
                )
        );

        Set<Point> encounteredSplitters = new HashSet<>();

        calculateSplitsPart1(pointsOnYs, input.getRight(), encounteredSplitters);
        return (long) encounteredSplitters.size();
    }

    public static void calculateSplitsPart1(
            Map<Integer, List<Integer>> splittersByColumns,
            Point startingPoint,
            Set<Point> encounteredSplitters) {
        Integer startingRow = startingPoint.getLeft();

        if (!splittersByColumns.containsKey(startingPoint.getRight())) {
            return;
        }
        List<Integer> splittersOnColumn = splittersByColumns.get(startingPoint.getRight());

        Optional<Integer> suitableSplitter = splittersOnColumn.stream()
                .filter(splitterRow -> splitterRow > startingRow).findFirst();

        if (suitableSplitter.isPresent()) {
            Point foundSplitter = Point.of(suitableSplitter.get(), startingPoint.getRight());
            if (encounteredSplitters.contains(foundSplitter)) {
                return;
            }

            encounteredSplitters.add(foundSplitter);
            calculateSplitsPart1(
                    splittersByColumns,
                    Point.of(suitableSplitter.get() + 1, startingPoint.getRight() + 1),
                    encounteredSplitters
            );
            calculateSplitsPart1(
                    splittersByColumns,
                    Point.of(suitableSplitter.get() + 1, startingPoint.getRight() - 1),
                    encounteredSplitters
            );
        }
    }


    public static Long part2(String input) {
        Pair<List<Point>, Point> parse = parse(input);
        return part2(parse);
    }

    public static Long part2(Pair<List<Point>, Point> input) {
        List<Point> splitters = input.getLeft();

        Map<Integer, List<Integer>> pointsOnYs = splitters.stream().collect(
                Collectors.groupingBy(
                        Point::getRight,
                        Collectors.mapping(Pair::getLeft, Collectors.toList())
                )
        );

        Map<Point, Long> encounteredSplitters = new HashMap<>();

        calculateSplitsPart2(pointsOnYs, List.of(input.getRight()), input.getRight(), encounteredSplitters);
        return Long.valueOf(encounteredSplitters.values().stream().max(Long::compareTo).get());
    }

    public static Long calculateSplitsPart2(
            Map<Integer, List<Integer>> splittersByColumns,
            List<Point> currentPath,
            Point startingPoint,
            Map<Point, Long> encounteredSplitters) {
        List<Point> path = new ArrayList<>(currentPath);
        Integer startingRow = startingPoint.getLeft();

        if (!splittersByColumns.containsKey(startingPoint.getRight())) {
            return 1L;
        }
        List<Integer> splittersOnColumn = splittersByColumns.get(startingPoint.getRight());

        Optional<Integer> suitableSplitter = splittersOnColumn.stream()
                .filter(splitterRow -> splitterRow > startingRow).findFirst();

        if (suitableSplitter.isPresent()) {
            Point foundSplitter = Point.of(suitableSplitter.get(), startingPoint.getRight());
            if (encounteredSplitters.containsKey(foundSplitter)) {
                return encounteredSplitters.get(foundSplitter);
            }

            Long pathsToLeft = calculateSplitsPart2(
                    splittersByColumns,
                    path,
                    Point.of(suitableSplitter.get() + 1, startingPoint.getRight() - 1),
                    encounteredSplitters
            );

            Long pathsToRight = calculateSplitsPart2(
                    splittersByColumns,
                    path,
                    Point.of(suitableSplitter.get() + 1, startingPoint.getRight() + 1),
                    encounteredSplitters
            );

            Long result = pathsToLeft + pathsToRight;
            encounteredSplitters.put(foundSplitter, result);

            return result;
        }
        return 1L;
    }

    private static List<Integer> clone(List<Integer> numberOfPaths) {
        return new ArrayList<>(numberOfPaths);
    }

    private static List<List<Point>> mutableDoubleList(Point startingPoint) {
        List<List<Point>> res = new ArrayList<>();
        List<Point> path = new ArrayList<>();
        path.add(startingPoint);
        res.add(path);

        return res;
    }

    public static Pair<List<Point>, Point> parse(String input) {
        List<Point> splitters = new ArrayList<>();
        Point start = null;
        String[] lines = input.split("\n");
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                if (lines[i].charAt(j) == '^') {
                    splitters.add(Point.of(i, j));
                } else if (lines[i].charAt(j) == 'S') {
                    start = Point.of(i, j);
                }
            }
        }

        return Pair.of(splitters, start);
    }

}
