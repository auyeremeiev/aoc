package aoc2024;

import common.Direction;
import common.Line;
import common.Pair;

import common.StopWatchGauge;
import common.Task;
import aoc2024.helpers.Day6Data;
import aoc2024.helpers.Day6Element;
import aoc2024.helpers.Day6TripResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static aoc2024.helpers.Day6TripResult.Task6BumpType.END_OF_FIELD;
import static aoc2024.helpers.Day6TripResult.Task6BumpType.OBSTACLE;
import static common.ListUtils.createBooleanMatrix;

public class Day6 {

    public static int getTask1Result(String input) {
        Day6Data day6Data = parseInput(input);
        return StopWatchGauge.runReliably(() -> calculateLength(day6Data), Task.FIRST);
    }

    public static int getTask1ResultSimpleSolution(String input) {
        Day6Data data = parseInput(input);
        return StopWatchGauge.runReliably(() -> calculateLengthSimpleSolution(data), Task.FIRST);
    }

    public static int getTask2ResultJumpingSolution(String input) {
        Day6Data data = parseInput(input);
        return StopWatchGauge.run(() -> calculateTask2JumpingSolution(data), 3, Task.SECOND);
    }

    private static int calculateTask2JumpingSolution(Day6Data data) {
        Pair<Integer, Integer> currentPoint = data.getStartingPoint();
        Direction currentDirection = Direction.UP;
        Set<Pair<Integer, Integer>> visitedPoints = new HashSet<>();
        Set<Pair<Integer, Integer>> loopMakingObstacles = new HashSet<>();
        Day6TripResult tripResult = getNextTripPoint(currentPoint, data, currentDirection);
        while (tripResult.getBumpType() == OBSTACLE) {
            Pair<Integer, Integer> nextPoint = tripResult.getNextPoint();
            if (!currentPoint.equals(nextPoint)) {
                loopMakingObstacles.addAll(getLoopingObstacles(currentPoint, nextPoint, data, visitedPoints));
            }
            currentDirection = switchDirection(currentDirection);
            currentPoint = nextPoint;
            tripResult = getNextTripPoint(currentPoint, data, currentDirection);
        }

        Pair<Integer, Integer> nextPoint = tripResult.getNextPoint();
        if (!currentPoint.equals(nextPoint)) {
            loopMakingObstacles.addAll(getLoopingObstacles(currentPoint, nextPoint, data, visitedPoints));
        }

        return loopMakingObstacles.size();
    }

    private static int calculateLengthSimpleSolution(Day6Data data) {
        List<List<Boolean>> visitedCells = createBooleanMatrix(data.getHeight(), data.getWidth());

        Pair<Integer, Integer> currentPoint = data.getStartingPoint();
        Line line = new Line(currentPoint);
        Direction currentDirection = Direction.UP;
        Day6TripResult tripResult = getNextTripPoint(currentPoint, data, currentDirection);
        List<Pair<Integer, Integer>> intersections = new ArrayList<>();
        ;
        while (tripResult.getBumpType() == OBSTACLE) {
            Pair<Integer, Integer> nextPoint = tripResult.getNextPoint();
            intersections.addAll(fillWithVisited(visitedCells, currentPoint, nextPoint));
            line.addPoint(nextPoint);
            currentDirection = switchDirection(currentDirection);
            currentPoint = nextPoint;
            tripResult = getNextTripPoint(currentPoint, data, currentDirection);
        }

        intersections.addAll(fillWithVisited(visitedCells, currentPoint, tripResult.getNextPoint()));
        line.addPoint(tripResult.getNextPoint()); // Getting last end of field point

        // intersections = 467
        return countFilledCells(visitedCells);
    }

    private static int calculateLength(Day6Data data) {
        Pair<Integer, Integer> currentPoint = data.getStartingPoint();
        Line line = new Line(currentPoint);
        Direction currentDirection = Direction.UP;
        Day6TripResult tripResult = getNextTripPoint(currentPoint, data, currentDirection);
        Set<Pair<Integer, Integer>> intersections = new HashSet<>();
        while (tripResult.getBumpType() == OBSTACLE) {
            Pair<Integer, Integer> nextPoint = tripResult.getNextPoint();
            intersections.addAll(line.getIntersections(nextPoint));
            line.addPoint(nextPoint);
            currentDirection = switchDirection(currentDirection);
            currentPoint = nextPoint;
            tripResult = getNextTripPoint(currentPoint, data, currentDirection);
        }

        intersections.addAll(line.getIntersections(tripResult.getNextPoint()));
        line.addPoint(tripResult.getNextPoint()); // Getting last end of field point

        // I could have improved performance if I counted the intersections only once in the end
        // intersections = 465
        return line.getLength() - intersections.size();
    }

    private static Set<Pair<Integer, Integer>> getLoopingObstacles(
            Pair<Integer, Integer> currentPoint,
            Pair<Integer, Integer> nextPoint,
            Day6Data data,
            Set<Pair<Integer, Integer>> excludedPoints) {
        excludedPoints.add(currentPoint);
        Direction direction = Line.getDirection(currentPoint, nextPoint);
        Set<Pair<Integer, Integer>> result = new HashSet<>();
        Pair<Integer, Integer> newObstacle;
        switch (direction) {
            case UP -> {
                for (int i = currentPoint.getLeft() - 1; i >= nextPoint.getLeft(); i--) {
                    newObstacle = new Pair<>(i, currentPoint.getRight());
                    if (!excludedPoints.contains(newObstacle) && willCreateLoop(currentPoint, newObstacle, direction, data)) {
                        result.add(newObstacle);
                    }
                    excludedPoints.add(newObstacle);
                }
            }
            case DOWN -> {
                for (int i = currentPoint.getLeft() + 1; i <= nextPoint.getLeft(); i++) {
                    newObstacle = new Pair<>(i, currentPoint.getRight());
                    if (!excludedPoints.contains(newObstacle) && willCreateLoop(currentPoint, newObstacle, direction, data)) {
                        result.add(newObstacle);
                    }
                    excludedPoints.add(newObstacle);
                }
            }
            case RIGHT -> {
                for (int i = currentPoint.getRight() + 1; i <= nextPoint.getRight(); i++) {
                    newObstacle = new Pair<>(currentPoint.getLeft(), i);
                    if (!excludedPoints.contains(newObstacle) && willCreateLoop(currentPoint, newObstacle, direction, data)) {
                        result.add(newObstacle);
                    }
                    excludedPoints.add(newObstacle);
                }
            }
            case LEFT -> {
                for (int i = currentPoint.getRight() - 1; i >= nextPoint.getRight(); i--) {
                    newObstacle = new Pair<>(currentPoint.getLeft(), i);
                    if (!excludedPoints.contains(newObstacle) && willCreateLoop(currentPoint, newObstacle, direction, data)) {
                        result.add(newObstacle);
                    }
                    excludedPoints.add(newObstacle);
                }
            }
        }

        return result;
    }

    public static boolean willCreateLoop(
            Pair<Integer, Integer> startingPoint,
            Pair<Integer, Integer> extraObstacle,
            Direction direction,
            Day6Data data) {
        Direction currentDirection = direction;
        Set<Pair<Integer, Integer>> visitedPoints = new HashSet<>();
        visitedPoints.add(startingPoint); // we start at the obstacle and turn immediately
        Pair<Integer, Integer> currentPoint = startingPoint;
        Day6TripResult tripResult = getNextTripPointWithExtraObstacle(startingPoint, extraObstacle, data, direction);
        while(tripResult.getBumpType() != END_OF_FIELD) {
            Pair<Integer, Integer> nextPoint = tripResult.getNextPoint();
            if (visitedPoints.contains(nextPoint)
                    && !currentPoint.equals(nextPoint)
            ) {
                return true;
            }
            visitedPoints.add(nextPoint);
            currentPoint = nextPoint;
            currentDirection = switchDirection(currentDirection);
            tripResult = getNextTripPointWithExtraObstacle(currentPoint, extraObstacle, data, currentDirection);
        }

        return false;
    }

    private static Day6TripResult getNextTripPoint(Pair<Integer, Integer> currentPoint,
                                                    Day6Data data,
                                                    Direction direction) {
        return getNextTripPointWithExtraObstacle(currentPoint, null, data, direction);
    }

    private static Day6TripResult getNextTripPointWithExtraObstacle(
            Pair<Integer, Integer> currentPoint,
            Pair<Integer, Integer> extraObstacle,
            Day6Data data,
            Direction direction) {
        Optional<Pair<Integer, Integer>> closestObstacle;
        if (direction.isHorizontal()) {
            closestObstacle = data.getClosestObstaclesOnXWithExtraObstacle(
                    currentPoint.getLeft(), currentPoint.getRight(), direction, extraObstacle);
        } else {
            closestObstacle = data.getClosestObstaclesOnYWithExtraObstacle(
                    currentPoint.getLeft(), currentPoint.getRight(), direction, extraObstacle);
        }

        if (closestObstacle.isEmpty()) {
            return new Day6TripResult(headToTheEnd(currentPoint, direction, data), END_OF_FIELD);
        }

        return closestObstacle.map(it -> {
                    int x = it.getLeft();
                    int y = it.getRight();

                    // We stop just at the obstacle one cell before it
                    switch (direction) {
                        case UP -> x++;
                        case DOWN -> x--;
                        case RIGHT -> y--;
                        case LEFT -> y++;
                    }

                    return new Pair<>(x, y);
                }).map(it -> new Day6TripResult(it, OBSTACLE))
                .get();
    }

    private static Pair<Integer, Integer> headToTheEnd(Pair<Integer, Integer> currentPoint, Direction direction, Day6Data data) {
        return switch (direction) {
            case UP -> new Pair<>(0, currentPoint.getRight());
            case DOWN -> new Pair<>(data.getHeight() - 1, currentPoint.getRight());
            case RIGHT -> new Pair<>(currentPoint.getLeft(), data.getWidth() - 1);
            case LEFT -> new Pair<>(currentPoint.getLeft(), 0);
        };
    }

    private static Direction switchDirection(Direction direction) {
        return switch (direction) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }

    public static Day6Data parseInput(String input) {
        List<List<Character>> map = Arrays.stream(input.split("\\n"))
                .map(String::chars)
                .map(chars -> chars.mapToObj(c -> (char) c).toList())
                .toList();

        Pair<Integer, Integer> startingPoint = null;
        List<Pair<Integer, Integer>> obstacles = new ArrayList<>();
        List<List<Day6Element>> resultMap = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            List<Day6Element> line = new ArrayList<>();
            List<Character> characters = map.get(i);
            for (int j = 0; j < characters.size(); j++) {
                if (characters.get(j) == '.') {
                    line.add(Day6Element.EMPTY);
                } else if (characters.get(j) == '#') {
                    line.add(Day6Element.OBSTACLE);
                    obstacles.add(new Pair<>(i, j));
                } else if (characters.get(j) == '^') {
                    startingPoint = new Pair<>(i, j);
                } else {
                    throw new IllegalStateException("Unknown element type");
                }
            }
            resultMap.add(line);
        }

        if (startingPoint == null) {
            throw new IllegalStateException("No starting point");
        }

        return new Day6Data(resultMap, obstacles, startingPoint);
    }


    private static List<Pair<Integer, Integer>> fillWithVisited(List<List<Boolean>> visitedCells, Pair<Integer, Integer> currentPoint, Pair<Integer, Integer> nextPoint) {
        Direction direction = Line.getDirection(currentPoint, nextPoint);
        List<Pair<Integer, Integer>> intersections = new ArrayList<>();
        switch (direction) {
            case UP -> {
                for (int i = currentPoint.getLeft(); i >= nextPoint.getLeft(); i--) {
                    if (visitedCells.get(i).get(currentPoint.getRight())) {
                        intersections.add(new Pair<>(i, currentPoint.getRight()));
                    }
                    visitedCells.get(i).set(currentPoint.getRight(), true);
                }
            }
            case DOWN -> {
                for (int i = currentPoint.getLeft(); i <= nextPoint.getLeft(); i++) {
                    if (visitedCells.get(i).get(currentPoint.getRight())) {
                        intersections.add(new Pair<>(i, currentPoint.getRight()));
                    }

                    visitedCells.get(i).set(currentPoint.getRight(), true);
                }
            }
            case RIGHT -> {
                for (int i = currentPoint.getRight(); i <= nextPoint.getRight(); i++) {
                    if (visitedCells.get(currentPoint.getLeft()).get(i)) {
                        intersections.add(new Pair<>(currentPoint.getLeft(), i));
                    }

                    visitedCells.get(currentPoint.getLeft()).set(i, true);
                }
            }
            case LEFT -> {
                for (int i = currentPoint.getRight(); i >= nextPoint.getRight(); i--) {
                    if (visitedCells.get(currentPoint.getLeft()).get(i)) {
                        intersections.add(new Pair<>(currentPoint.getLeft(), i));
                    }

                    visitedCells.get(currentPoint.getLeft()).set(i, true);
                }
            }
        }

        // The very first time it won't intersect with itself
        if (!intersections.isEmpty()) {
            intersections.remove(0); // exclude itself
        }
        return intersections;
    }

    private static int countFilledCells(List<List<Boolean>> visitedCells) {
        int result = 0;
        for (int i = 0; i < visitedCells.size(); i++) {
            for (int j = 0; j < visitedCells.size(); j++) {
                if (visitedCells.get(i).get(j)) {
                    result++;
                }
            }
        }
        return result;
    }
}
