package aoc2024;

import common.PairWithoutOrder;
import common.Point;

import common.StopWatchGauge;
import common.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day8 {
    private int width;
    private int height;
    private Map<Character, List<Point>> antennas = new HashMap<>();

    public Day8(String text) {
        List<List<Character>> lines = text.lines()
                .map(it -> it.chars().mapToObj(charc -> (char) charc).toList()).toList();
        height = lines.size();
        width = lines.get(0).size();

        for (int i = 0; i < lines.size(); i++) {
            List<Character> line = lines.get(i);
            for (int j = 0; j < line.size(); j++) {
                computeAntenna(line, i, j);
            }
        }
    }

    public Set<Point> getTask1Antinodes() {
        Set<Point> result = new HashSet<>();
        antennas.keySet().forEach(it -> {
            getAntennasPairs(it).forEach(pointsPair -> {
                result.addAll(getAntinodes(pointsPair));
            });

        });

        return result;
    }

    public Set<Point> getTask2Antinodes() {
        Set<Point> result = new HashSet<>();
        antennas.keySet().forEach(it -> {
            getAntennasPairs(it).forEach(pointsPair -> {
                result.addAll(getAntinodesTask2(pointsPair));
            });

        });

        return result;
    }

    public int getTask1AntinodesNumber() {
        return StopWatchGauge.run(() -> getTask1Antinodes().size(), 1000, Task.FIRST);
    }

    public int getTask2AntinodesNumber() {
        return StopWatchGauge.run(() -> getTask2Antinodes().size(), 1000, Task.SECOND);
    }

    private Set<Point> getAntinodes(PairWithoutOrder<Point, Point> pointsPair) {
        Set<Point> result = new HashSet<>();

        Point firstPoint = pointsPair.getLeft();
        Point secondPoint = pointsPair.getRight();

        // 6,5 and 8,9 = 2,4 -> 8 - 2 = 4
        // 8,9 and 6,5 = -2,-4 -> 8 - (-2) = 10
        // 6,5 and 8,1 =2,-4 -> y = 5 - (-4) = 9
        int differenceOnX = secondPoint.getLeft() - firstPoint.getLeft();
        int differenceOnY = secondPoint.getRight() - firstPoint.getRight();

        Point firstAntenode = new Point(firstPoint.getLeft() - differenceOnX, firstPoint.getRight() - differenceOnY);
        Point secondeAntenode = new Point(secondPoint.getLeft() + differenceOnX, secondPoint.getRight() + differenceOnY);

        if (isPointValid(firstAntenode)) {
            result.add(firstAntenode);
        }

        if (isPointValid(secondeAntenode)) {
            result.add(secondeAntenode);
        }

        return result;
    }

    private Set<Point> getAntinodesTask2(PairWithoutOrder<Point, Point> pointsPair) {
        Set<Point> result = new HashSet<>();

        Point firstPoint = pointsPair.getLeft();
        Point secondPoint = pointsPair.getRight();

        // 6,5 and 8,9 = 2,4 -> 8 - 2 = 4
        // 8,9 and 6,5 = -2,-4 -> 8 - (-2) = 10
        // 6,5 and 8,1 =2,-4 -> y = 5 - (-4) = 9
        int differenceOnX = secondPoint.getLeft() - firstPoint.getLeft();
        int differenceOnY = secondPoint.getRight() - firstPoint.getRight();

        Point currentAntenode = new Point(firstPoint.getLeft() - differenceOnX, firstPoint.getRight() - differenceOnY);
        while(isPointValid(currentAntenode)) {
            result.add(currentAntenode);
            currentAntenode = new Point(currentAntenode.getLeft() - differenceOnX, currentAntenode.getRight() - differenceOnY);
        }

        currentAntenode = new Point(secondPoint.getLeft() + differenceOnX, secondPoint.getRight() + differenceOnY);;
        while(isPointValid(currentAntenode)) {
            result.add(currentAntenode);
            currentAntenode = new Point(currentAntenode.getLeft() + differenceOnX, currentAntenode.getRight() + differenceOnY);;
        }

        result.add(firstPoint);
        result.add(secondPoint);

        return result;
    }

    private boolean isPointValid(Point point) {
        if (point.getLeft() < 0 || point.getRight() < 0) {
            return false;
        }

        if (point.getLeft() >= height || point.getRight() >= width) {
            return false;
        }

        return true;
    }

    public Set<PairWithoutOrder<Point, Point>> getAntennasPairs(Character character) {
        List<Point> antennas = this.antennas.get(character);
        Set<PairWithoutOrder<Point, Point>> result = new HashSet<>();

        antennas.forEach(outer ->
                antennas.forEach(inner -> {
                    if (!inner.equals(outer)) {
                        result.add(new PairWithoutOrder<>(outer, inner));
                    }
                }));

        return result;
    }

    private void computeAntenna(List<Character> line, int i, int j) {
        Character character = line.get(j);
        if (character != '.') {
            antennas.compute(character, (key, list) -> {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(new Point(i, j));
                return list;
            });
        }
    }
}
