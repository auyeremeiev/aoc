package aoc2024.helpers;

import common.Direction;
import common.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Day6Data {

    private List<List<Day6Element>> map;
    private List<Pair<Integer, Integer>> obstacles;
    private Map<Integer, List<Pair<Integer, Integer>>> obstaclesOnX;
    private Map<Integer, List<Pair<Integer, Integer>>> obstaclesOnY;
    private Pair<Integer, Integer> startingPoint;

    public Day6Data(List<List<Day6Element>> map, List<Pair<Integer, Integer>> obstacles, Pair<Integer, Integer> startingPoint) {
        this.map = map;
        this.obstacles = obstacles;
        this.startingPoint = startingPoint;
        preprocessObstacles(obstacles);
    }

    private void preprocessObstacles(List<Pair<Integer, Integer>> obstacles) {
        obstaclesOnX = new HashMap<>();
        obstaclesOnY = new HashMap<>();

        obstacles.forEach(it -> {
            obstaclesOnX.compute(it.getLeft(), (x, obstaclesOnX) -> {
                if (obstaclesOnX == null) {
                    obstaclesOnX = new ArrayList<>();
                }
                obstaclesOnX.add(it);

                return obstaclesOnX;
            });

            obstaclesOnY.compute(it.getRight(), (y, obstaclesOnY) -> {
                if (obstaclesOnY == null) {
                    obstaclesOnY = new ArrayList<>();
                }

                obstaclesOnY.add(it);
                return obstaclesOnY;
            });
        });
    }

    public List<List<Day6Element>> getMap() {
        return map;
    }

    public List<Pair<Integer, Integer>> getObstacles() {
        return obstacles;
    }

    public Pair<Integer, Integer> getStartingPoint() {
        return startingPoint;
    }

    public Optional<Pair<Integer, Integer>> getClosestObstaclesOnX(int x, int startingY, Direction direction) {
        return getClosestObstaclesOnXWithExtraObstacle(x, startingY, direction, null);
    }

    public Optional<Pair<Integer, Integer>> getClosestObstaclesOnXWithExtraObstacle(
            int x, int startingY, Direction direction, Pair<Integer, Integer> extraObstacle
    ) {
        Pair<Integer, Integer> startingPoint = new Pair<>(x, startingY);
        if (!List.of(Direction.RIGHT, Direction.LEFT).contains(direction)) {
            throw new IllegalStateException("Direction should be horizontal");
        }

        List<Pair<Integer, Integer>> obstacles = new ArrayList<>();
        if (obstaclesOnX.containsKey(x)) {
            obstacles.addAll(obstaclesOnX.get(x));
        }
        if (extraObstacle != null && extraObstacle.getLeft().equals(x)) {
            obstacles.add(extraObstacle);
        }

        if (obstacles.isEmpty()) {
            return Optional.empty();
        }

        Pair<Integer, Integer> closestObstacle = null;
        int curSmallestDistance = Integer.MAX_VALUE;
        for (Pair<Integer, Integer> obstacle : obstacles) {
            int curDistance = getDistance(startingPoint, obstacle);
            if ((isOnTheRightAndHeadingRight(startingY, direction, obstacle) ||
                    isOnTheLeftAndHeadingLeft(startingY, direction, obstacle))
                    && curDistance < curSmallestDistance) {
                curSmallestDistance = curDistance;
                closestObstacle = obstacle;
            }
        }

        return Optional.ofNullable(closestObstacle);
    }

    public Optional<Pair<Integer, Integer>> getClosestObstaclesOnY(int startingX, int y, Direction direction) {
        return getClosestObstaclesOnYWithExtraObstacle(startingX, y, direction, null);
    }

    public Optional<Pair<Integer, Integer>> getClosestObstaclesOnYWithExtraObstacle(
            int startingX, int y, Direction direction, Pair<Integer, Integer> extraObstacle
    ) {
        Pair<Integer, Integer> startingPoint = new Pair<>(startingX, y);
        if (!List.of(Direction.UP, Direction.DOWN).contains(direction)) {
            throw new IllegalStateException("Direction should be vertical");
        }

        List<Pair<Integer, Integer>> obstacles = new ArrayList<>();
        if (obstaclesOnY.containsKey(y)) {
            obstacles.addAll(obstaclesOnY.get(y));
        }

        if (extraObstacle != null && extraObstacle.getRight().equals(y)) {
            obstacles.add(extraObstacle);
        }

        if (obstacles.isEmpty()) {
            return Optional.empty();
        }

        Pair<Integer, Integer> closestObstacle = null;
        int curSmallestDistance = Integer.MAX_VALUE;
        for (Pair<Integer, Integer> obstacle : obstacles) {
            int curDistance = getDistance(startingPoint, obstacle);
            if ((isAboveAndHeadingUp(startingX, direction, obstacle) ||
                    isBelowAndHeadingDown(startingX, direction, obstacle))
                    && curDistance < curSmallestDistance) {
                curSmallestDistance = curDistance;
                closestObstacle = obstacle;
            }
        }

        return Optional.ofNullable(closestObstacle);
    }

    private boolean isAboveAndHeadingUp(int startingX, Direction direction, Pair<Integer, Integer> obstacle) {
        return obstacle.getLeft() < startingX && direction == Direction.UP;
    }

    private boolean isBelowAndHeadingDown(int startingX, Direction direction, Pair<Integer, Integer> obstacle) {
        return obstacle.getLeft() > startingX && direction == Direction.DOWN;
    }

    private static boolean isOnTheRightAndHeadingRight(int startingY, Direction direction, Pair<Integer, Integer> obstacle) {
        return obstacle.getRight() > startingY && direction == Direction.RIGHT;
    }

    private static boolean isOnTheLeftAndHeadingLeft(int startingY, Direction direction, Pair<Integer, Integer> obstacle) {
        return obstacle.getRight() < startingY && direction == Direction.LEFT;
    }

    public static int getDistance(Pair<Integer, Integer> pointA, Pair<Integer, Integer> pointB) {
        if (pointA.getLeft().equals(pointB.getLeft())) {
            return Math.abs(pointA.getRight() - pointB.getRight());
        } else if (pointA.getRight().equals(pointB.getRight())) {
            return Math.abs(pointA.getLeft() - pointB.getLeft());
        } else {
            throw new IllegalStateException("Points are not on the line");
        }
    }

    public int getWidth() {
        return map.get(0).size();
    }

    public int getHeight() {
        return map.size();
    }
}
