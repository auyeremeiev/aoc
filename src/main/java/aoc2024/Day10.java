package aoc2024;

import common.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static common.ListUtils.createMatrix;

public class Day10 {

    private final List<List<Integer>> map;

    public Day10(String input) {
        map = Arrays.stream(input.split("\\n"))
                .map(String::chars)
                .map(chars -> chars.mapToObj(c -> (char) c).map(Character::getNumericValue).toList())
                .toList();
    }

    public int getNumberOfTrails() {
        if (map.isEmpty()) {
            return 0;
        }

        if (map.get(0).isEmpty()) {
            return 0;
        }

        List<List<Set<Point>>> numberOfTrails = createMatrix(map.size(), map.get(0).size(), () -> null);

        int numberOfTrailHeads = 0;
        int totalNumberOfTrails = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).size(); j++) {
                if (numberOfTrails.get(i).get(j) == null && map.get(i).get(j) == 0) {
                    Set<Point> headTails = findAllTrailTailsTask1(map, numberOfTrails, i, j);

                    numberOfTrails.get(i).set(j, headTails);
                    if (!headTails.isEmpty()) {
                        numberOfTrailHeads++;
                        totalNumberOfTrails += headTails.size();
                    }
                }
            }
        }

        return totalNumberOfTrails;
    }

    public int getNumberOfTrailsTask2() {
        if (map.isEmpty()) {
            return 0;
        }

        if (map.get(0).isEmpty()) {
            return 0;
        }

        List<List<Integer>> numberOfTrails = createMatrix(map.size(), map.get(0).size(), () -> -1);

        int numberOfTrailHeads = 0;
        int totalNumberOfTrails = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(0).size(); j++) {
                if (numberOfTrails.get(i).get(j) == -1 && map.get(i).get(j) == 0) {
                    int trails = findAllTrailsTask2(map, numberOfTrails, i, j);

                    numberOfTrails.get(i).set(j, trails);
                    numberOfTrailHeads++;
                    totalNumberOfTrails += trails;
                }
            }
        }

        return totalNumberOfTrails;
    }

    private Set<Point> findAllTrailTailsTask1(
            List<List<Integer>> map,
            List<List<Set<Point>>> numberOfTrails,
            int currentRow,
            int currentColumn) {
        Integer currentHeight = map.get(currentRow).get(currentColumn);
        if (currentHeight == 9) {
            Set<Point> headTail = new HashSet<>(Set.of(new Point(currentRow, currentColumn)));
            numberOfTrails.get(currentRow).set(currentColumn, headTail);
            return headTail;
        }

        Set<Point> newTrails = new HashSet<>();
        if (isValidPoint(map, currentRow - 1, currentColumn) &&
                map.get(currentRow - 1).get(currentColumn) == currentHeight + 1) { // UP
            Set<Point> cachedTrails = numberOfTrails.get(currentRow - 1).get(currentColumn);
            if (cachedTrails == null) {
                Set<Point> trails = findAllTrailTailsTask1(map, numberOfTrails, currentRow - 1, currentColumn);
                newTrails.addAll(trails);
            } else {
                newTrails.addAll(cachedTrails);
            }
        }

        if (isValidPoint(map, currentRow + 1, currentColumn) &&
                map.get(currentRow + 1).get(currentColumn) == currentHeight + 1) {
            Set<Point> cachedTrails = numberOfTrails.get(currentRow + 1).get(currentColumn);
            if (cachedTrails == null) {
                Set<Point> trails = findAllTrailTailsTask1(map, numberOfTrails, currentRow + 1, currentColumn);
                newTrails.addAll(trails);
            } else {
                newTrails.addAll(cachedTrails);
            }
        }

        if (isValidPoint(map, currentRow, currentColumn - 1) &&
                map.get(currentRow).get(currentColumn - 1) == currentHeight + 1) {
            Set<Point> cachedTrails = numberOfTrails.get(currentRow).get(currentColumn - 1);
            if (cachedTrails == null) {
                Set<Point> trails = findAllTrailTailsTask1(map, numberOfTrails, currentRow, currentColumn - 1);
                newTrails.addAll(trails);
            } else {
                newTrails.addAll(cachedTrails);
            }
        }

        if (isValidPoint(map, currentRow, currentColumn + 1) &&
                map.get(currentRow).get(currentColumn + 1) == currentHeight + 1) {
            Set<Point> cachedTrails = numberOfTrails.get(currentRow).get(currentColumn + 1);
            if (cachedTrails == null) {
                Set<Point> trails = findAllTrailTailsTask1(map, numberOfTrails, currentRow, currentColumn + 1);
                newTrails.addAll(trails);
            } else {
                newTrails.addAll(cachedTrails);
            }
        }

        numberOfTrails.get(currentRow).set(currentColumn, newTrails);
        return newTrails;
    }

    private int findAllTrailsTask2(
            List<List<Integer>> map,
            List<List<Integer>> numberOfTrails,
            int currentRow,
            int currentColumn
    ) {
        Integer currentHeight = map.get(currentRow).get(currentColumn);

        if (currentHeight == 9) {
            numberOfTrails.get(currentRow).set(currentColumn, 1);
            return 1;
        }

        // I don't neet to check previous position because it will be always currentHeight - 1. Or if we start from 0 then we can go to all
        // directions
        int allTrails = 0;
        if (isValidPoint(map, currentRow - 1, currentColumn) &&
                map.get(currentRow - 1).get(currentColumn) == currentHeight + 1) { // UP
            Integer alreadyCalculatedAllTrails = numberOfTrails.get(currentRow - 1).get(currentColumn);
            if (alreadyCalculatedAllTrails == -1) {
                alreadyCalculatedAllTrails = findAllTrailsTask2(map, numberOfTrails, currentRow - 1, currentColumn);
            }

            allTrails += alreadyCalculatedAllTrails;
        }

        if (isValidPoint(map, currentRow + 1, currentColumn) &&
                map.get(currentRow + 1).get(currentColumn) == currentHeight + 1) {
            Integer alreadyCalculatedAllTrails = numberOfTrails.get(currentRow + 1).get(currentColumn);
            if (alreadyCalculatedAllTrails == -1) {
                alreadyCalculatedAllTrails = findAllTrailsTask2(map, numberOfTrails, currentRow  + 1, currentColumn);
            }

            allTrails += alreadyCalculatedAllTrails;
        }

        if (isValidPoint(map, currentRow, currentColumn - 1) &&
                map.get(currentRow).get(currentColumn - 1) == currentHeight + 1) {
            Integer alreadyCalculatedAllTrails = numberOfTrails.get(currentRow).get(currentColumn - 1);
            if (alreadyCalculatedAllTrails == -1) {
                alreadyCalculatedAllTrails = findAllTrailsTask2(map, numberOfTrails, currentRow, currentColumn - 1);
            }

            allTrails += alreadyCalculatedAllTrails;
        }

        if (isValidPoint(map, currentRow, currentColumn + 1) &&
                map.get(currentRow).get(currentColumn + 1) == currentHeight + 1) {
            Integer alreadyCalculatedAllTrails = numberOfTrails.get(currentRow).get(currentColumn + 1);
            if (alreadyCalculatedAllTrails == -1) {
                alreadyCalculatedAllTrails = findAllTrailsTask2(map, numberOfTrails, currentRow, currentColumn + 1);
            }

            allTrails += alreadyCalculatedAllTrails;
        }

        numberOfTrails.get(currentRow).set(currentColumn, allTrails);
        return allTrails;
    }


    private boolean isValidPoint(List<List<Integer>> map, int row, int column) {
        if (row < 0 || column < 0) {
            return false;
        }

        if (row >= map.size()) {
            return false;
        }

        if (column >= map.get(row).size()) {
            return false;
        }

        return true;
    }
}
