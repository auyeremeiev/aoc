package aoc2024;

import common.Number;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static common.Number.split;
import static common.Pair.toList;

public class Day11 {
    private final int numberOfIterations;

    private final List<Long> stones;

    public Day11(String input, int numberOfIterations) {
        stones = Arrays.stream(input.split("\\s{1,}")).map(Long::parseLong).toList();
        this.numberOfIterations = numberOfIterations;
    }

    public Long getStonesNumberTask1() {
        Map<Long, Map<Integer, Long>> cache = new HashMap<>();

        return stones.stream().map(initialStone -> processStoneFully(
                initialStone, cache, 0, numberOfIterations))
                .reduce(0L, Long::sum);
    }

    private Long processStoneFully(
            Long currentStone,
            Map<Long, Map<Integer, Long>> cache,
            int currentIteration,
            int iterationLimit) {
        cache.compute(currentStone, (k, v) -> {
            if (v == null) {
                v = new HashMap<>();
            }

            return v;
        });

        if (cache.containsKey(currentStone) && cache.get(currentStone).containsKey(currentIteration)) {
            return cache.get(currentStone).get(currentIteration);
        }

        if (currentIteration == iterationLimit) {
            return 1L;
        }

        List<Long> currentStones = new ArrayList<>(List.of(currentStone));

        List<Long> newStones = processStones(currentStones);
        Long processedStones = newStones.stream()
                .map(newStone -> processStoneFully(newStone, cache, currentIteration + 1, iterationLimit))
                .reduce(0L, Long::sum);

        if (cache.containsKey(currentStone)) {
            Map<Integer, Long> iterationToNumberMap = cache.get(currentStone);
            iterationToNumberMap.put(currentIteration, processedStones);
        }

        return processedStones;
    }

    private List<Long> processStones(List<Long> stones) {
        return stones.stream().flatMap(this::processStone).toList();
    }

    private Stream<Long> processStone(Long stone) {
        if (stone == 0) {
             return new ArrayList<>(List.of(1L)).stream();
        }

        int numberOfDigits = Number.digits(stone);
        if (numberOfDigits % 2 == 0) {
            return toList(split(stone, numberOfDigits)).stream();
        }

        return new ArrayList<>(List.of(stone * 2024)).stream();
    }
}
