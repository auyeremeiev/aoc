package aoc2024;

import common.Direction;
import common.ListUtils;
import common.Pair;
import common.Point;
import aoc2024.helpers.Day12Region;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static common.Direction.DOWN;
import static common.Direction.LEFT;
import static common.Direction.RIGHT;
import static common.Direction.UP;
import static common.ListUtils.isBeyoundEdge;

public class Day12 {

    private final List<List<Character>> garden;
    private final int height;
    private final int width;

    public Day12(String input) {
        garden = parseInput(input);
        height = garden.size();
        width = garden.get(0).size();
    }

    public Day12(List<List<Character>> garden) {
        this.garden = garden;
        height = garden.size();
        width = garden.get(0).size();
    }

    public static List<List<Character>> parseInput(String input) {
        return input.lines().map(it -> it.chars().mapToObj(charc -> (char) charc).toList()).toList();
    }

    public Long getTask1Result() {
        List<List<Integer>> regionsMap = ListUtils.createMatrix(height, width);

        List<Day12Region> regions = getAllRegions(regionsMap);
        return regions.stream().map(this::calculatePrice).reduce(0L, Long::sum);
    }

    public Long getTask2Result() {
        List<List<Integer>> regionsMap = ListUtils.createMatrix(height, width);

        List<Day12Region> regions = getAllRegions(regionsMap);
        return regions.stream().map(this::calculatePrice2).reduce(0L, Long::sum);
    }

    public List<Day12Region> getAllRegions(List<List<Integer>> regionsMap) {
        List<Day12Region> regions = new ArrayList<>();

        int currentRegionId = 1;
        for (int i = 0; i < garden.size(); i++) {
            for (int j = 0; j < garden.get(i).size(); j++) {
                if (regionsMap.get(i).get(j) == 0) {
                    Day12Region newRegion = new Day12Region(currentRegionId, garden.get(i).get(j));
                    exploreRegion(regionsMap, garden, newRegion, i, j);
                    regions.add(newRegion);
                    currentRegionId++;
                }
            }
        }

        return regions;
    }

    private void exploreRegion(
            List<List<Integer>> regionsMap,
            List<List<Character>> garden,
            Day12Region region,
            int i,
            int j) {
        regionsMap.get(i).set(j, region.getRegionId());
        Point currentPoint = new Point(i, j);
        region.addPoint(currentPoint);

        if (!isBeyoundEdge(garden, new Point(i + 1, j))
                && garden.get(i + 1).get(j) == region.getCharacter() && regionsMap.get(i + 1).get(j) == 0) {
            exploreRegion(regionsMap, garden, region, i + 1, j);
        }

        if (!isBeyoundEdge(garden, new Point(i - 1, j))
                && garden.get(i - 1).get(j) == region.getCharacter() && regionsMap.get(i - 1).get(j) == 0) {
            exploreRegion(regionsMap, garden, region, i - 1, j);
        }

        if (!isBeyoundEdge(garden, new Point(i, j + 1))
                && garden.get(i).get(j + 1) == region.getCharacter() && regionsMap.get(i).get(j + 1) == 0) {
            exploreRegion(regionsMap, garden, region, i, j + 1);
        }

        if (!isBeyoundEdge(garden, new Point(i, j - 1))
                && garden.get(i).get(j - 1) == region.getCharacter() && regionsMap.get(i).get(j - 1) == 0) {
            exploreRegion(regionsMap, garden, region, i, j - 1);
        }

        if (isRegionEdgePoint(garden, region, currentPoint)) {
            region.addEdgePoint(currentPoint);
        }
    }

    private boolean isRegionEdgePoint(List<List<Character>> garden, Day12Region region, Point currentPoint) {
        if (ListUtils.isAtEdge(garden, currentPoint)) {
            return true;
        }

        Integer i = currentPoint.getLeft();
        Integer j = currentPoint.getRight();

        return garden.get(i + 1).get(j) != region.getCharacter()
                || garden.get(i - 1).get(j) != region.getCharacter()
                || garden.get(i).get(j + 1) != region.getCharacter()
                || garden.get(i).get(j - 1) != region.getCharacter();
    }

    private long calculatePrice(Day12Region region) {
        return calculatePerimeter(region) * region.getPoints().size();
    }

    private long calculatePrice2(Day12Region region) {
        return calculateSides(region) * region.getPoints().size();
    }

    private long calculatePerimeter(Day12Region region) {
        Set<Point> allEdgePoints = new HashSet<>(region.getEdgePoints());
        return allEdgePoints.stream().flatMap(it -> getFences(region, it).stream()).collect(Collectors.toSet()).size();
    }

    private long calculateSides(Day12Region region) {
        Set<Point> allEdgePoints = new HashSet<>(region.getEdgePoints());
        Set<Pair<Point, Point>> fences = new HashSet<>();
        allEdgePoints.forEach(it -> fences.addAll(getFences(region, it)));
        Set<Set<Pair<Point, Point>>> squashedFences = squashFences(fences);

        return squashedFences.size();
    }

    private Set<Set<Pair<Point, Point>>> squashFences(Set<Pair<Point, Point>> fences) {
        Set<Set<Pair<Point, Point>>> sides = new HashSet<>();
        sides.addAll(getHorizontalSides(fences));
        sides.addAll(getVerticalSides(fences));
        return sides;
    }

    private Set<Set<Pair<Point, Point>>> getHorizontalSides(Set<Pair<Point, Point>> fences) {
        Set<Set<Pair<Point, Point>>> result = new HashSet<>();

        Set<Pair<Point, Point>> horizontalFences = new HashSet<>(fences.stream()
                .filter(it -> Direction.getDirection(it.getLeft(), it.getRight()).isVertical())
                .collect(Collectors.toSet()));

        while (!horizontalFences.isEmpty()) {
            Set<Pair<Point, Point>> side = new HashSet<>();
            Pair<Point, Point> initialFence = horizontalFences.stream().findFirst().orElseThrow();
            side.add(initialFence);
            horizontalFences.remove(initialFence);

            Pair<Point, Point> nextFence = shiftFence(initialFence, LEFT);

            while (horizontalFences.contains(nextFence)) {
                side.add(nextFence);
                horizontalFences.remove(nextFence);
                nextFence = shiftFence(nextFence, LEFT);
            }

            nextFence = shiftFence(initialFence, RIGHT);

            while (horizontalFences.contains(nextFence)) {
                side.add(nextFence);
                horizontalFences.remove(nextFence);
                nextFence = shiftFence(nextFence, RIGHT);
            }

            result.add(side);
        }

        return result;
    }

    private Set<Set<Pair<Point, Point>>> getVerticalSides(Set<Pair<Point, Point>> fences) {
        Set<Set<Pair<Point, Point>>> result = new HashSet<>();

        Set<Pair<Point, Point>> verticalFences = new HashSet<>(fences.stream()
                .filter(it -> Direction.getDirection(it.getLeft(), it.getRight()).isHorizontal())
                .collect(Collectors.toSet()));

        while (!verticalFences.isEmpty()) {
            Set<Pair<Point, Point>> side = new HashSet<>();
            Pair<Point, Point> initialFence = verticalFences.stream().findFirst().orElseThrow();
            side.add(initialFence);
            verticalFences.remove(initialFence);

            Pair<Point, Point> nextFence = shiftFence(initialFence, UP);

            while (verticalFences.contains(nextFence)) {
                side.add(nextFence);
                verticalFences.remove(nextFence);
                nextFence = shiftFence(nextFence, UP);
            }

            nextFence = shiftFence(initialFence, DOWN);

            while (verticalFences.contains(nextFence)) {
                side.add(nextFence);
                verticalFences.remove(nextFence);
                nextFence = shiftFence(nextFence, DOWN);
            }

            result.add(side);
        }

        return result;
    }

    private Pair<Point, Point> shiftFence(Pair<Point, Point> fence, Direction direction) {
        return new Pair<>(
                direction.nextPoint(fence.getLeft()),
                direction.nextPoint(fence.getRight())
        );
    }


    private Set<Pair<Point, Point>> getFences(
            Day12Region region,
            Point edgePoint) {
        Direction currentDirection = UP;
        Set<Pair<Point, Point>> fences = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            Point nextPoint = currentDirection.nextPoint(edgePoint);
            if (!region.getPoints().contains(nextPoint)) {
                fences.add(new Pair<>(edgePoint, nextPoint));
            }
            currentDirection = currentDirection.switchClockWise();
        }

        return fences;
    }
}
