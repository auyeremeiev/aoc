package aoc2025;

import common.Direction;
import common.DirectionWithDiagonal;
import common.ListUtils;
import common.Point;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static common.Direction.traverseAllDirectionsPointsInsideMatrix;
import static common.DirectionWithDiagonal.traverseAllDirectionsPointsInsideMatrix;
import static common.Number.concat;

public class Day4 {

    private static final Logger log = LogManager.getLogger(aoc2025.Day4.class);

    public static long part1(String input) {
        List<List<Boolean>> grid = parse(input);

        return part1(grid);
    }

    public static long part1(List<List<Boolean>> grid) {
        List<List<Integer>> adjacencyMatrix = getAdjacencyMatrix(grid);

        int result = 0;

        for (Integer i = 0; i < grid.size(); i++) {
            for (Integer j = 0; j < grid.size(); j++) {
                if (grid.get(i).get(j)) {
                    Integer adjacencyCount = adjacencyMatrix.get(i).get(j);
                    if (adjacencyCount < 4) {
                        result++;
                    }
                }
            }
        }
        return result;
    }

    private static List<List<Integer>> getAdjacencyMatrix(List<List<Boolean>> grid) {
        List<List<Integer>> adjacentRollsCounter = new ArrayList<>();

        ListUtils.fillMatrix(adjacentRollsCounter, 0, grid.size());

        for (Integer i = 0; i < grid.size(); i++) {
            for (Integer j = 0; j < grid.size(); j++) {
                if (grid.get(i).get(j)) {
                    traverseAllDirectionsPointsInsideMatrix(
                            Point.of(i, j), grid, (nextPoint) -> {
                                Integer currentValue = adjacentRollsCounter.get(nextPoint.getLeft()).get(nextPoint.getRight());
                                adjacentRollsCounter.get(nextPoint.getLeft()).set(nextPoint.getRight(), currentValue + 1);
                            });
                }
            }
        }

        return adjacentRollsCounter;
    }

    public static Long part2(String input) {
        List<List<Boolean>> numbers = parse(input);

        return part2(numbers);
    }

    public static Long part2(List<List<Boolean>> grid) {
        List<List<Integer>> adjacencyMatrix = getAdjacencyMatrix(grid);

        Long lastRemovedRolls = clearRolls(grid, adjacencyMatrix);
        long result = lastRemovedRolls;
        while(lastRemovedRolls > 0) {
            lastRemovedRolls = clearRolls(grid, adjacencyMatrix);
            result += lastRemovedRolls;
        }

        return result;
    }

    private static Long clearRolls(List<List<Boolean>> grid, List<List<Integer>> adjacencyMatrix) {
        long result = 0;
        for (Integer i = 0; i < grid.size(); i++) {
            for (Integer j = 0; j < grid.size(); j++) {
                if (grid.get(i).get(j)) {
                    Integer adjacencyCount = adjacencyMatrix.get(i).get(j);
                    if (adjacencyCount < 4) {
                        grid.get(i).set(j, false);
                        traverseAllDirectionsPointsInsideMatrix(
                                Point.of(i, j), adjacencyMatrix, (nextPoint) -> {
                                    Integer currentValue = adjacencyMatrix.get(nextPoint.getLeft()).get(nextPoint.getRight());
                                    adjacencyMatrix.get(nextPoint.getLeft()).set(nextPoint.getRight(), currentValue - 1);
                                });
                        result++;
                    }
                }
            }
        }
        return result;
    }

    public static List<List<Boolean>> parse(String input) {
        String[] lines = input.split("\n");
        return Arrays.stream(lines).map(Day4::lineToBooleanList).toList();
    }

    public static List<Boolean> lineToBooleanList(String line) {
        List<Boolean> result = new ArrayList<>(line.length());
        for (char c : line.toCharArray()) {
            result.add(c == '@'); // true if '.', false otherwise
        }
        return result;
    }
}
