package aoc2024;

import common.Pair;
import common.Point;
import common.StopWatchGauge;
import aoc2024.helpers.Day14RobotData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static common.Direction.DOWN;
import static common.Direction.UP;

public class Day14 {

    private static final Logger log = LogManager.getLogger(StopWatchGauge.class);

    private final static Pattern BUTTON_PATTERN = Pattern.compile("p=([\\+-]?\\d+),([\\+-]?\\d+) v=([\\+-]?\\d+),([\\+-]?\\d+)");
    private final static int DEFAULT_HEIGHT = 103;
    private final static int DEFAULT_WIDTH = 101;
    private final static int DEFAULT_SECONDS = 100;
    private final static int POINTS_IN_ROW_CHECK = 10;


    private final List<Day14RobotData> robots;
    private final int height;
    private final int width;
    private final int seconds;

    public Day14(List<Day14RobotData> robots) {
        this(robots, DEFAULT_HEIGHT, DEFAULT_WIDTH, DEFAULT_SECONDS);
    }

    public Day14(String input, int height, int width, int seconds) {
        this(parseInput(input), height, width, seconds);
    }

    public Day14(List<Day14RobotData> robots, int height, int width, int seconds) {
        this.robots = robots;
        this.height = height;
        this.width = width;
        this.seconds = seconds;
    }

    public int getTask1Result() {
        List<Point> finalPositions = robots.stream()
                .map(robot -> getFinalPosition(robot, seconds))
                .toList();

        return countFinalResult(finalPositions);
    }

    // Can be improved by using variance and then Chinese Remainder Theorem
    public int getTask2Result() {
        int currentSecond = 0;

        Set<Point> currentSecondPoints = getAllPositionsAfter(currentSecond);

        while (!containsAnyShape(currentSecondPoints)) {
            currentSecond++;
            currentSecondPoints = getAllPositionsAfter(currentSecond);
        }

//        log.info(PointsUtils.print(currentSecondPoints));

        return currentSecond;
    }

    private Set<Point> getAllPositionsAfter(int currentSecond) {
        return robots.stream()
                .map(robot -> getFinalPosition(robot, currentSecond))
                .collect(Collectors.toSet());
    }

    public Map<Point, Map<Point, Integer>> getAllPositionsWithin(int seconds) {
        Map<Point, Map<Point, Integer>> result = new HashMap<>();
        robots.stream().forEach(robot -> {
            Map<Point, Integer> pointsPositions = new HashMap<>();
            for (int i = 0; i < seconds; i++) {
                Point position = getFinalPosition(robot, i);
                pointsPositions.compute(position, (k, v) -> {
                    if (v == null) {
                        v = 0;
                    }
                    v++;
                    return v;
                });
            }
            result.put(robot.startingPoint(), pointsPositions);
        });
        return result;
    }

    private Point getFinalPosition(Day14RobotData robot, int seconds) {
        long startingRow = robot.startingPoint().getLeft();
        long startingColumn = robot.startingPoint().getRight();

        long rowVelocity = robot.velocity().getLeft();
        long columnVelocity = robot.velocity().getRight();

        int finalRow = Math.floorMod(startingRow + rowVelocity * seconds, height);
        int finalColumn = Math.floorMod(startingColumn + columnVelocity * seconds, width);

        return new Point(finalRow, finalColumn);
    }

    // hidden row: height / 2
    // hidden column: width / 2
    private int countFinalResult(List<Point> finalPositions) {
        List<Point> firstQuadrant = new ArrayList<>();
        List<Point> secondQuadrant = new ArrayList<>();
        List<Point> thirdQuadrant = new ArrayList<>();
        List<Point> forthQuadrant = new ArrayList<>();

        finalPositions.forEach(it -> {
            if (it.getLeft() >= 0 && it.getLeft() < height / 2 && it.getRight() >= 0 && it.getRight() < width / 2) {
                firstQuadrant.add(it);
            } else if (it.getLeft() >= 0 && it.getLeft() < height / 2 && it.getRight() >= width / 2 + 1 && it.getRight() < width) {
                secondQuadrant.add(it);
            } else if (it.getLeft() >= height / 2 + 1 && it.getLeft() < height  && it.getRight() >= 0 && it.getRight() < width / 2) {
                thirdQuadrant.add(it);
            } else if (it.getLeft() >= height / 2 + 1 && it.getLeft() < height && it.getRight() >= width / 2 + 1 && it.getRight() < width) {
                forthQuadrant.add(it);
            }
        });

        return firstQuadrant.size() * secondQuadrant.size() * thirdQuadrant.size() * forthQuadrant.size();
    }

    public static boolean containsAnyShape(Set<Point> points) {
        Set<Point> currentPoints = new HashSet<>(points);
        while (!currentPoints.isEmpty()) {
            Point currentPoint = currentPoints.stream().findAny().orElseThrow();
            List<Point> pointsInRow = pointsInVerticalLine(points, currentPoint, POINTS_IN_ROW_CHECK);
            if (pointsInRow.size() == POINTS_IN_ROW_CHECK) {
                return true;
            }

            pointsInRow.forEach(currentPoints::remove);
        }

        return false;
    }

    // We consider that having 10 points in a row is such an impossible coincidence that
    // most probably it's a shape
    public static List<Point> pointsInVerticalLine(Set<Point> allPoints, Point point, int limit) {
        List<Point> traversedPoints = new ArrayList<>();
        traversedPoints.add(point);
        int pointsInLine = 1;
        Point nextPoint = UP.nextPoint(point);
        while (allPoints.contains(nextPoint) && pointsInLine < limit) {
            traversedPoints.add(nextPoint);
            pointsInLine++;
            nextPoint = UP.nextPoint(nextPoint);
        }

        nextPoint = DOWN.nextPoint(point);
        while (allPoints.contains(nextPoint) && pointsInLine < limit) {
            traversedPoints.add(nextPoint);
            pointsInLine++;
            nextPoint = DOWN.nextPoint(nextPoint);
        }

        return traversedPoints;
    }

    public static List<Day14RobotData> parseInput(String input) {
        return Arrays.stream(input.split("\n")).map(it -> {
            Matcher matcher = BUTTON_PATTERN.matcher(it);
            matcher.find();

            Point startingPoint = new Point(
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(1))
            );

            Pair<Integer, Integer> velocity = new Pair<>(
                    Integer.parseInt(matcher.group(4)),
                    Integer.parseInt(matcher.group(3))
            );

            return new Day14RobotData(startingPoint, velocity);
        }).toList();
    }
}
