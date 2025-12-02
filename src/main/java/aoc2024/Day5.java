package aoc2024;

import common.Pair;
import aoc2024.helpers.Day5Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day5 {

    public static int getCorrectlyOrderedLinesResult(String rawInput) {
        List<List<Integer>> lines = getCorrectlyOrderedLines(rawInput);
        return aggregateMiddleNumbers(lines);
    }

    public static int getFixedInCorrectlyOrderedLinesResultThroughSort(String rawInput) {
        Day5Data day5Data = readInput(rawInput);

        Map<Integer, Set<Integer>> disallowedNumbersMap = getDisallowedNumbersMap(day5Data);

        List<List<Integer>> lines = getIncorrectlyOrderedLines(day5Data, disallowedNumbersMap);
        fixIncorrectlyOrderedLinesThroughSort(lines, day5Data);
        return aggregateMiddleNumbers(lines);
    }

    private static void fixIncorrectlyOrderedLinesThroughSort(List<List<Integer>> lines, Day5Data day5Data) {
        for (List<Integer> line : lines) {
            line.sort(new Task5Comparator(day5Data));
        }
    }

    public static List<List<Integer>> getCorrectlyOrderedLines(String rawInput) {
        List<List<Integer>> result = new ArrayList<>();
        Day5Data day5Data = readInput(rawInput);

        Map<Integer, Set<Integer>> disallowedNumbersMap = getDisallowedNumbersMap(day5Data);

        for (List<Integer> line : day5Data.getUpdatesList()) {
            if (lineIsCorrect(line, disallowedNumbersMap)) {
                result.add(line);
            }
        }

        return result;
    }

    private static List<List<Integer>> getIncorrectlyOrderedLines(Day5Data day5Data, Map<Integer, Set<Integer>> disallowedNumbersMap) {
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> line : day5Data.getUpdatesList()) {
            if (!lineIsCorrect(line, disallowedNumbersMap)) {
                result.add(line);
            }
        }

        return result;
    }

    private static boolean lineIsCorrect(List<Integer> line, Map<Integer, Set<Integer>> disallowedNumbersMap) {
        Set<Integer> outOfOrderNumbers = new HashSet<>();
        for (int number : line) {
            if (outOfOrderNumbers.contains(number)) {
                return false;
            }

            if (disallowedNumbersMap.containsKey(number)) {
                outOfOrderNumbers.addAll(disallowedNumbersMap.get(number));
            }
        }

        return true;
    }

    // Get numbers which can't follow other numbers
    public static Map<Integer, Set<Integer>> getDisallowedNumbersMap(Day5Data day5Data) {
        Map<Integer, Set<Integer>> result = new HashMap<>();

        List<Pair<Integer, Integer>> orderRules = day5Data.getOrderRules();
        orderRules.forEach(it -> {
            Integer right = it.getRight();
            result.compute(
                    right, (number, numbersWhichCannotFollow) -> {
                        if (numbersWhichCannotFollow == null) {
                            numbersWhichCannotFollow = new HashSet<>();
                        }

                        numbersWhichCannotFollow.add(it.getLeft());

                        return numbersWhichCannotFollow;
                    }
            );
        });

        return result;
    }

    public static Day5Data readInput(String rawInput) {
        String[] sections = rawInput.split("\\n\\n");
        String rawRules = sections[0];
        List<Pair<Integer, Integer>> rules = Arrays.stream(rawRules.split("\\n"))
                .map(it -> it.split("\\|"))
                .map(it -> Pair.of(Integer.parseInt(it[0]), Integer.parseInt(it[1])))
                .collect(Collectors.toCollection(ArrayList::new));

        String rawUpdates = sections[1];
        List<List<Integer>> updates = Arrays.stream(rawUpdates.split("\\n"))
                .map(it -> it.split(","))
                .map(Arrays::asList)
                .map(it -> {
                    return (List<Integer>) it.stream().map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
                })
                .toList();

        return new Day5Data(rules, updates);
    }

    private static Integer aggregateMiddleNumbers(List<List<Integer>> lines) {
        return lines.stream().map(Day5::getMiddleNumber).reduce(0, Integer::sum);
    }

    private static int getMiddleNumber(List<Integer> line) {
        // size = 5
        // 0 1 [2] 3 4
        // floor(5 / 2) = 2
        return line.get(line.size() / 2);
    }

    public static class Task5Comparator implements Comparator<Integer> {
        private Map<Integer, Set<Integer>> order;

        public Task5Comparator(Day5Data day5Data) {
            this.order = getOrderMap(day5Data);
        }

        @Override
        public int compare(Integer numb1, Integer numb2) {
            if (order.containsKey(numb1)) {
                Set<Integer> numbersGreaterThanNumb1 = order.get(numb1);
                if (numbersGreaterThanNumb1.contains(numb2)) {
                    return -1;
                }
            }

            if (order.containsKey(numb2)) {
                Set<Integer> numbersGreaterThanNum2 = order.get(numb2);
                if (numbersGreaterThanNum2.contains(numb1)) {
                    return 1;
                }
            }

            return 0;
        }

        public static Map<Integer, Set<Integer>> getOrderMap(Day5Data day5Data) {
            Map<Integer, Set<Integer>> result = new HashMap<>();

            List<Pair<Integer, Integer>> orderRules = day5Data.getOrderRules();
            orderRules.forEach(it -> {
                Integer left = it.getLeft();
                Integer right = it.getRight();
                result.compute(
                        left, (number, greaterNumbers) -> {
                            if (greaterNumbers == null) {
                                greaterNumbers = new HashSet<>();
                            }

                            greaterNumbers.add(right);

                            return greaterNumbers;
                        }
                );
            });

            return result;
        }

        @Override
        public boolean equals(Object obj) {
            return order.equals(obj);
        }

        @Override
        public int hashCode() {
            return order != null ? order.hashCode() : 0;
        }
    }
}
