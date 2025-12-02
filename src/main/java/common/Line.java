package common;

import java.util.ArrayList;
import java.util.List;

public class Line {
    private List<Pair<Integer, Integer>> points;

    public Line(Pair<Integer, Integer> startingPoint) {
        points = new ArrayList<>();
        points.add(startingPoint);
    }

    public void addPoint(Pair<Integer, Integer> point) {
        Pair<Integer, Integer> lastPoint = points.get(points.size() - 1);
        if (!isEitherHorizontalOrVertical(lastPoint, point)) {
            throw new IllegalStateException("Line must be either vertical or horizontal");
        }
        points.add(point);
    }

    private boolean isEitherHorizontalOrVertical(Pair<Integer, Integer> pointA, Pair<Integer, Integer> pointB) {
        return pointA.getLeft().equals(pointB.getLeft()) ||
                pointA.getRight().equals(pointB.getRight());
    }

    public List<Pair<Integer, Integer>> getPoints() {
        return points;
    }

    public int getLength() {
        int result = 0;
        if (points.size() <= 1) {
            return result;
        }

        for (int i = 0; i < points.size() - 1; i++) {
            Pair<Integer, Integer> curPoint = points.get(i);
            Pair<Integer, Integer> nextPoint = points.get(i + 1);

            // We check invariant that all lines vertical or horizontal in add method
            int horizontalDistance = Math.abs(nextPoint.getRight() - curPoint.getRight());
            int verticalDistance = Math.abs(nextPoint.getLeft() - curPoint.getLeft());
            result += horizontalDistance + verticalDistance;
        }

        result++; // including starting point

        return result;
    }

    public List<Pair<Integer, Integer>> getIntersections(Pair<Integer, Integer> point) {
        Pair<Integer, Integer> lastPoint = points.get(points.size() - 1);
        if (!isEitherHorizontalOrVertical(lastPoint, point)) {
            throw new IllegalStateException("Line must be either vertical or horizontal");
        }

        Direction direction = getDirection(lastPoint, point);
        List<Pair<Integer, Integer>> result = new ArrayList<>();
        if (isVertical(lastPoint, point)) {
            Integer lineY = point.getRight();
            int fromX = lastPoint.getLeft();
            int untilX = point.getLeft();

            if (direction == Direction.UP) {
                fromX--; // excluding itself
            } else if (direction == Direction.DOWN) {
                fromX++;
            }

            result.addAll(getAllHorizontalIntersectionsForY(lineY, fromX, untilX));
        } else {
            Integer lineX = point.getLeft();
            int fromY = lastPoint.getRight();
            int untilY = point.getRight();

            if (direction == Direction.RIGHT) {
                fromY++;
            } else if (direction == Direction.LEFT) {
                fromY--;
            }
            result.addAll(getAllVerticalIntersectionsForX(lineX, fromY, untilY));
        }


        return result;
    }

    public List<Pair<Integer, Integer>> getAllHorizontalIntersectionsForY(int y, int fromX, int untilX) {
        int fromXtoCheck = fromX;
        int untilXtoCheck = untilX;

        if (fromX > untilX) {
            fromXtoCheck = untilX;
            untilXtoCheck = fromX;
        }

        List<Pair<Integer, Integer>> result = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            Pair<Integer, Integer> curPoint = points.get(i);
            Pair<Integer, Integer> nextPoint = points.get(i + 1);

            // bringing line to the same direction
            if (points.get(i).getRight() > points.get(i + 1).getRight()) {
                curPoint = points.get(i + 1);
                nextPoint = points.get(i);
            }

            if (isHorizontal(curPoint, nextPoint) && intersectsVerticalLine(curPoint, nextPoint, y, fromXtoCheck, untilXtoCheck)) {
                result.add(new Pair<>(curPoint.getLeft(), y));
            }
        }

        return result;
    }

    public List<Pair<Integer, Integer>> getAllVerticalIntersectionsForX(int x, int fromY, int untilY) {
        int fromYToCheck = fromY;
        int untilYtoCheck = untilY;

        if (fromY > untilY) { // is reversed
            fromYToCheck = untilY;
            untilYtoCheck = fromY;
        }

        List<Pair<Integer, Integer>> result = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            Pair<Integer, Integer> curPoint = points.get(i);
            Pair<Integer, Integer> nextPoint = points.get(i + 1);

            // bringing line to the same direction
            if (points.get(i).getLeft() > points.get(i + 1).getLeft()) {
                curPoint = points.get(i + 1);
                nextPoint = points.get(i);
            }

            if (isVertical(curPoint, nextPoint) && intersectsHorizontalLine(curPoint, nextPoint, x, fromYToCheck, untilYtoCheck)) {
                result.add(new Pair<>(x, curPoint.getRight()));
            }
        }

        return result;
    }

    // if from x = 3 and untilX = 8 and cur point is (2, 2) and next point is (2, 7) it cannot intersect
    // if cur point is (4, 2) and next point is (4, 7) it can intersect
    // if cur point is (4, 7) and next point is (4, 2) it can intersect
    // if from x = 8 and untilX = 3 (reversed) and cur point is (2, 2) and next point is (2, 7) it cannot intersect
    // if cur point is (4, 2) and next point is (4, 7) it can intersect
    // if cur point is (4, 7) and next point is (4, 2) it can intersect
    private boolean intersectsHorizontalLine(Pair<Integer, Integer> curPoint,
                                             Pair<Integer, Integer> nextPoint,
                                             int x,
                                             int fromY,
                                             int untilY) {
        if (!isFromY1toY2(curPoint, nextPoint, fromY, untilY)) {
            return false;
        }

        if (curPoint.getLeft() <= x && nextPoint.getLeft() >= x) { // goes down
            return true;
        } else if (curPoint.getLeft() >= x && nextPoint.getLeft() <= x) { // goes up
            return true;
        }

        return false;
    }

    private boolean intersectsVerticalLine(Pair<Integer, Integer> curPoint,
                                           Pair<Integer, Integer> nextPoint,
                                           int y,
                                           int fromX,
                                           int untilX) {
        if (!isFromX1toX2(curPoint, nextPoint, fromX, untilX)) {
            return false;
        }

        if (curPoint.getRight() <= y && nextPoint.getRight() >= y) {
            return true;
        } else if (curPoint.getRight() >= y && nextPoint.getRight() <= y) {
            return true;
        }

        return false;
    }

    private boolean isFromX1toX2(Pair<Integer, Integer> curPoint,
                                 Pair<Integer, Integer> nextPoint,
                                 int fromX,
                                 int untilX) {
        if (!curPoint.getLeft().equals(nextPoint.getLeft())) {
            throw new IllegalStateException("Points are not on the same horizontal line");
        }
        Integer lineX = curPoint.getLeft();

        return lineX >= fromX && lineX <= untilX;
    }

    private boolean isFromY1toY2(Pair<Integer, Integer> curPoint,
                                 Pair<Integer, Integer> nextPoint,
                                 int fromY,
                                 int untilY) {
        if (!curPoint.getRight().equals(nextPoint.getRight())) {
            throw new IllegalStateException("Points are not on the same vertical line");
        }

        Integer lineY = curPoint.getRight();

        return lineY >= fromY && lineY <= untilY;
    }

    public static boolean isVertical(Pair<Integer, Integer> pointA, Pair<Integer, Integer> pointB) {
        return pointA.getRight().equals(pointB.getRight());
    }

    public static boolean isHorizontal(Pair<Integer, Integer> pointA, Pair<Integer, Integer> pointB) {
        return pointA.getLeft().equals(pointB.getLeft());
    }

    public static Line of(Pair<Integer, Integer> pointA, Pair<Integer, Integer> pointB) {
        Line line = new Line(pointA);
        line.addPoint(pointB);
        return line;
    }

    public static Direction getDirection(Pair<Integer, Integer> pointA, Pair<Integer, Integer> pointB) {
        if (isHorizontal(pointA, pointB)) {
            if (pointA.getRight() < pointB.getRight()) {
                return Direction.RIGHT;
            } else if (pointA.getRight() > pointB.getRight()) {
                return Direction.LEFT;
            } else {
                throw new IllegalStateException("Line consists of one point");
            }
        } else if (isVertical(pointA, pointB)) {
            if (pointA.getLeft() < pointB.getLeft()) {
                return Direction.DOWN;
            } else if (pointA.getLeft() > pointB.getLeft()) {
                return Direction.UP;
            } else {
                throw new IllegalStateException("Line consists of one point");
            }
        } else {
            throw new IllegalStateException("Line is neither vertical nor horizontal");
        }
    }
}
