package aoc2024.helpers;

import common.Point;
import java.util.HashSet;
import java.util.Set;

public class Day12Region {
    private final int regionId;
    private final Character character;
    private final Set<Point> points;
    private Set<Point> edgePoints;

    public Day12Region(int regionId, Character character) {
        this.regionId = regionId;
        this.character = character;
        this.points = new HashSet<>();
        this.edgePoints = new HashSet<>();
    }

    public int getRegionId() {
        return regionId;
    }

    public Character getCharacter() {
        return character;
    }

    public Set<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public Set<Point> getEdgePoints() {
        return edgePoints;
    }

    public void addEdgePoint(Point edgePoint) {
        this.edgePoints.add(edgePoint);
    }
}
