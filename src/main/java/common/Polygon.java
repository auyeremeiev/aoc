package common;

import java.awt.geom.Point2D;
import java.util.*;

public class Polygon {

    private Set<Point> points;
    private Point leftMostPoint;
    private Point topPoint;
    private Point rightMostPoint;
    private Point bottomPoint;
    private List<Line2P> lines;

    private Map<Point, Boolean> insidePolygonCheckCache = new HashMap<>();

    public Polygon(List<Point> points) {
        this.leftMostPoint = findLeftMostPoint(points);
        this.topPoint = findTopPoint(points);
        this.rightMostPoint = findRightMostPoint(points);
        this.bottomPoint = findBottomPoint(points);
        this.lines = getAllLines(points);
        this.points = new HashSet<>(points);
    }

    private Point findLeftMostPoint(List<Point> points) {
        Point leftMostPoint = points.get(0);

        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).getRight() < leftMostPoint.getRight()) {
                leftMostPoint = points.get(i);
            }
        }

        return leftMostPoint;
    }

    private Point findTopPoint(List<Point> points) {
        Point result = points.get(0);

        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).getLeft() < result.getLeft()) {
                result = points.get(i);
            }
        }

        return result;
    }

    private Point findRightMostPoint(List<Point> points) {
        Point result = points.get(0);

        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).getRight() > result.getRight()) {
                result = points.get(i);
            }
        }

        return result;
    }

    private Point findBottomPoint(List<Point> points) {
        Point result = points.get(0);

        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).getLeft() > result.getLeft()) {
                result = points.get(i);
            }
        }

        return result;
    }

    public boolean isPointInsidePolygon(Point point) {
        return insidePolygonCheckCache.computeIfAbsent(point, key -> {
            if (points.contains(point)) {
                return true;
            }

            Line2P beamUp = makeBeamUp(point);
            Line2P beamToRight = makeBeamToRight(point);
            Line2P beamToBottom = makeBeamToBottom(point);
            Line2P beamToLeft = makeBeamToLeft(point);

            List<Line2P> beams = List.of(beamUp, beamToRight, beamToBottom, beamToLeft);

            for (Line2P beam : beams) {
                List<Line2P> intersections = beam.getIntersections(lines, false);
                if (intersections.isEmpty()) {
                    return false;
                }
            }

            return true;
        });
    }

    private static Line2P makeBeamToLeft(Point point) {
        return Line2P.of(Point.of(point.getLeft(), point.getRight()), Point.of(point.getLeft(), Integer.MIN_VALUE));
    }

    private static Line2P makeBeamToBottom(Point point) {
        return Line2P.of(Point.of(point.getLeft(), point.getRight()), Point.of(Integer.MAX_VALUE, point.getRight()));
    }

    private static Line2P makeBeamToRight(Point point) {
        return Line2P.of(Point.of(point.getLeft(), point.getRight()), Point.of(point.getLeft(), Integer.MAX_VALUE));
    }

    private static Line2P makeBeamUp(Point point) {
        return Line2P.of(Point.of(point.getLeft(), point.getRight()), Point.of(Integer.MIN_VALUE, point.getRight()));
    }

    public boolean isRectangleInsidePolygon(Rectangle rectangle) {
        if (rectangle.isFlat()) {
            return true;
        }

        if (intersectsHorizontalStickingOuts(rectangle, lines) ||
                intersectsVerticalStickingOuts(rectangle, lines)) {
            return false;
        }

        return true;
    }

    private boolean intersectsHorizontalStickingOuts(Rectangle rectangle, List<Line2P> allLines) {
        Line2P lowerLine = rectangle.bottomLine();
        List<Line2P> intersections = lowerLine.getIntersections(allLines, true);

        for (Line2P intersection : intersections) {
            Pair<Line2P, Line2P> split = split(intersection, lowerLine);
            if (split.getRight().length() > 0) {
                return true;
            }
        }

        for (Line2P intersection : intersections) {
            Pair<Line2P, Line2P> split = split(intersection, lowerLine);
            if (split.getLeft().length() > 0) {
                return true;
            }
        }

        return false;
    }

    private Pair<Line2P, Line2P> split(Line2P initialLine, Line2P splittingLine) {
        Point splittingPoint;
        if (splittingLine.isHorizontal()) {
            splittingPoint = Point.of(splittingLine.getRowForHorizontal(), initialLine.getColumnForVertical());
        } else {
            splittingPoint = Point.of(initialLine.getRowForHorizontal(), splittingLine.getColumnForVertical());
        }

        return split(initialLine, splittingPoint);
    }

    private Pair<Line2P, Line2P> split(Line2P line2P, Point point) {
        if (line2P.isHorizontal()) {
            if (line2P.getRowForHorizontal() != point.getLeft()) {
                throw new IllegalStateException("Point is not on the line");
            }

            Integer startingColumn = Math.min(
                    line2P.getBeginning().getRight(), line2P.getDestination().getRight());
            Integer endingColumn = Math.max(
                    line2P.getBeginning().getRight(), line2P.getDestination().getRight());

            if (point.getRight() < startingColumn || point.getRight() > endingColumn) {
                throw  new IllegalStateException("Point is not on the line");
            }

            Line2P line1 = Line2P.of(
                    Point.of(line2P.getRowForHorizontal(), startingColumn),
                    Point.of(line2P.getRowForHorizontal(), point.getRight()));

            Line2P line2 = Line2P.of(
                    Point.of(line2P.getRowForHorizontal(), point.getRight()),
                    Point.of(line2P.getRowForHorizontal(), endingColumn)
            );

            return Pair.of(line1, line2);
        } else {
            if (line2P.getColumnForVertical() != point.getRight()) {
                throw new IllegalStateException("Point is not on the line");
            }

            Integer startingRow = Math.min(line2P.getBeginning().getLeft(), line2P.getDestination().getLeft());
            Integer endingRow = Math.max(line2P.getBeginning().getLeft(), line2P.getDestination().getLeft());

            if (point.getLeft() < startingRow || point.getLeft() > endingRow) {
                throw  new IllegalStateException("Point is not on the line");
            }

            Line2P line1 = Line2P.of(
                    Point.of(startingRow, line2P.getColumnForVertical()),
                    Point.of(point.getLeft(), line2P.getColumnForVertical())
            );

            Line2P line2 = Line2P.of(
                    Point.of(point.getLeft(), line2P.getColumnForVertical()),
                    Point.of(endingRow, line2P.getColumnForVertical())
            );

            return Pair.of(line1, line2);
        }
    }

    private boolean intersectsVerticalStickingOuts(Rectangle rectangle, List<Line2P> allLines) {
        Line2P leftLine = rectangle.leftLine();
        List<Line2P> intersections = leftLine.getIntersections(allLines, true);

        for (Line2P intersection : intersections) {
            Pair<Line2P, Line2P> split = split(intersection, leftLine);
            if (split.getRight().length() > 0) {
                return true;
            }
        }

        Line2P rightLine = rectangle.rightLine();
        intersections = rightLine.getIntersections(allLines, true);
        for (Line2P intersection : intersections) {
            Pair<Line2P, Line2P> split = split(intersection, rightLine);
            if (split.getLeft().length() > 0) {
                return true;
            }
        }

        return false;
    }

    public List<Line2P> getAllLines(List<Point> points) {
        if (points.isEmpty()) {
            return Collections.emptyList();
        }

        List<Line2P> result = new ArrayList<>();

        Point currentPoint = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            Point nextPoint = points.get(i);
            result.add(Line2P.of(currentPoint, nextPoint));
            currentPoint = nextPoint;
        }

        result.add(Line2P.of(points.get(points.size() - 1), points.get(0)));

        return result;
    }

    public Line upperBoundary() {
        return Line.of(
                Point.of(topPoint.getLeft(), Integer.MIN_VALUE),
                Point.of(topPoint.getLeft(), Integer.MAX_VALUE)
        );
    }

    public Line bottomBoundary() {
        return Line.of(
                Point.of(bottomPoint.getLeft(), Integer.MIN_VALUE),
                Point.of(bottomPoint.getLeft(), Integer.MAX_VALUE));
    }

    public Line rightBoundary() {
        return Line.of(
                Point.of(Integer.MIN_VALUE, rightMostPoint.getRight()),
                Point.of(Integer.MAX_VALUE, rightMostPoint.getRight())
        );
    }

    public Line leftBoundary() {
        return Line.of(
                Point.of(Integer.MIN_VALUE, leftMostPoint.getRight()),
                Point.of(Integer.MAX_VALUE, leftMostPoint.getRight())
        );
    }
}
