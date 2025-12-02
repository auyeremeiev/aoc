package aoc2024;

import common.Pair;
import aoc2024.inputs.Day1Input;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1 {

    public static long getDistance() {
        Pair<List<Integer>, List<Integer>> input = readInput();
        input.getLeft().sort(Integer::compareTo);
        input.getRight().sort(Integer::compareTo);

        long result = 0;

        for (int i = 0; i < input.getLeft().size(); i++) {
            Integer leftValue = input.getLeft().get(i);
            Integer rightValue = input.getRight().get(i);
            result += Math.abs(leftValue - rightValue);
        }

        return result;
    }

    public static long getSimilarity(Pair<List<Integer>, List<Integer>> input) {
        Map<Integer, Long> numberCounts = new HashMap<>();

        List<Integer> rightList = input.getRight();

        rightList.forEach(it -> {
            if (numberCounts.containsKey(it)) {
                numberCounts.put(it, numberCounts.get(it) + 1);
            } else {
                numberCounts.put(it, 1L);
            }
        });

        long result = 0;
        List<Integer> left = input.getLeft();

        for (Integer it : left) {
            if (numberCounts.containsKey(it)) {
                result += it * numberCounts.get(it);
            }
        }

        return result;
    }

    public static Pair<List<Integer>, List<Integer>> readInput() {
        String input = Day1Input.INPUT_1;
        String[] lines = input.split("\\n");

        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();
        Arrays.stream(lines).map(it -> {
            String[] lineValues = it.trim().split("\\s{2,}");
            return new Pair<>(lineValues[0], lineValues[1]);
        }).forEach(it -> {
            firstList.add(Integer.valueOf(it.getLeft()));
            secondList.add(Integer.valueOf(it.getRight()));
        });

        return Pair.of(firstList, secondList);
    }

}
