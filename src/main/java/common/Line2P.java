package common;

import java.util.ArrayList;
import java.util.List;

public class Line2P {
    private final Pair<Point, Point> points;

    public Line2P(Point beginning, Point end) {
        points = Pair.of(beginning, end);
    }

    private boolean isEitherHorizontalOrVertical() {
        return isEitherHorizontalOrVertical(getBeginning(), getDestination());
    }

    private boolean isEitherHorizontalOrVertical(Pair<Integer, Integer> pointA, Pair<Integer, Integer> pointB) {
        return pointA.getLeft().equals(pointB.getLeft()) ||
                pointA.getRight().equals(pointB.getRight());
    }

    public Pair<Point, Point> getPoints() {
        return points;
    }

    public Direction getDirection() {
        return getDirection(getBeginning(), getDestination());
    }

    public Point getDestination() {
        return points.getRight();
    }

    public Point getBeginning() {
        return points.getLeft();
    }

    public int getColumnForVertical() {
        if (!isVertical()) {
            throw new IllegalStateException("Line is not vertical!");
        }

        return getBeginning().getRight();
    }

    public int getRowForHorizontal() {
        if (!isHorizontal()) {
            throw new IllegalStateException("Line is not horizontal!");
        }

        return getDestination().getLeft();
    }

    public List<Line2P> getIntersections(List<Line2P> lines, boolean exclusive) {
        if (!isEitherHorizontalOrVertical()) {
            throw new IllegalStateException("Line must be either vertical or horizontal");
        }

        List<Line2P> result = new ArrayList<>();

        Line2P lineToCheck = getLineToCheckForIntersections(exclusive);

        for (Line2P line : lines) {
            if (linesIntersect(lineToCheck, line)) {
                result.add(line);
            }
        }

        return result;
    }

    private Line2P getLineToCheckForIntersections(boolean exclusive) {
        Line2P lineToCheck = this;
        if (exclusive) {
            lineToCheck = switch (getDirection()) {
                case UP -> Line2P.of(
                        Point.of(getBeginning().getLeft() - 1, getBeginning().getRight()),
                        Point.of(getDestination().getLeft() + 1, getDestination().getRight())
                );
                case DOWN -> Line2P.of(
                        Point.of(getBeginning().getLeft() + 1, getBeginning().getRight()),
                        Point.of(getDestination().getLeft() - 1, getDestination().getRight())
                );
                case RIGHT -> Line2P.of(
                        Point.of(getBeginning().getLeft(), getBeginning().getRight() + 1),
                        Point.of(getDestination().getLeft(), getDestination().getRight() - 1));
                case LEFT -> Line2P.of(
                        Point.of(getBeginning().getLeft(), getBeginning().getRight() - 1),
                        Point.of(getDestination().getLeft(), getDestination().getRight() + 1));
            };
        }
        return lineToCheck;
    }

    public static boolean linesIntersect(Line2P line1, Line2P line2) {
        if (line1.isHorizontal() && line2.isVertical()) {
            return lineIntersectsVerticalLine(line1, line2);
        } else if (line1.isVertical() && line2.isHorizontal()) {
            return lineIntersectsHorizontalLine(line1, line2);
        }

        return false;
    }

    private static boolean lineIntersectsHorizontalLine(Line2P line, Line2P horizontalLine) {
        if (line.isHorizontal()) {
            return false;
        }

        return isBetweenRows(
                horizontalLine,
                line.getBeginning().getLeft(),
                line.getDestination().getLeft()
        ) &&

                isBetweenColumns(
                        line,
                        horizontalLine.getBeginning().getRight(),
                        horizontalLine.getDestination().getRight());
    }

    private static boolean lineIntersectsVerticalLine(Line2P line, Line2P verticalLine) {
        if (line.isVertical()) {
            return false;
        }

        return isBetweenRows(
                line,
                verticalLine.getBeginning().getLeft(),
                verticalLine.getDestination().getLeft()
        ) &&
                isBetweenColumns(
                        verticalLine,
                        line.getBeginning().getRight(),
                        line.getDestination().getRight());
    }

    /**
     * The line will end up with the direction either RIGHT or BOTTOM. Convenient for comparison
     */
    private Line2P normaliseDirection(Line2P line, boolean exclusive) {
        Direction direction = line.getDirection();
        if (direction.isVertical()) {
            int lineY = line.getDestination().getRight();

            Integer fromX = line.getBeginning().getLeft();
            Integer toX = line.getDestination().getLeft();

            // bringing everything in the same direction from down to up
            if (direction == Direction.UP) {
                fromX = line.getDestination().getLeft();
                toX = line.getBeginning().getLeft();
            }

            return Line2P.of(
                    Point.of(fromX + (exclusive ? +1 : 0), lineY),
                    Point.of(toX + (exclusive ? -1 : 0), lineY)
            );
        } else {
            Integer lineX = line.getDestination().getLeft();

            int fromY = line.getBeginning().getRight();
            int untilY = line.getDestination().getRight();

            // bringing everything in the same direction from left to right excluding the Point
            if (direction == Direction.LEFT) {
                fromY = line.getDestination().getRight();
                untilY = line.getBeginning().getRight();
            }

            return Line2P.of(
                    Point.of(lineX, fromY + (exclusive ? +1 : 0)),
                    Point.of(lineX, untilY + (exclusive ? -1 : 0))
            );
        }
    }

    public long length() {
        return Point.distance(getBeginning(), getDestination());
    }

    public boolean isVertical() {
        return isVertical(this);
    }

    public static boolean isVertical(Line2P line) {
        return isVertical(line.getBeginning(), line.getDestination());
    }

    public boolean isHorizontal() {
        return isHorizontal(this);
    }

    public static boolean isHorizontal(Line2P line) {
        return isHorizontal(line.getBeginning(), line.getDestination());
    }

    private static boolean isBetweenRows(Line2P line, int fromXInput, int toXInput) {
        int fromX = Math.min(fromXInput, toXInput);
        int toX = Math.max(fromXInput, toXInput);

        if (line.isHorizontal()) {
            Integer lineX = line.getBeginning().getLeft();
            return fromX <= lineX && toX >= lineX;
        } else {
            int lineUpmostX = Math.min(line.getBeginning().getLeft(), line.getDestination().getLeft());
            int lineDownmostX = Math.max(line.getDestination().getLeft(), line.getDestination().getLeft());

            return (fromX <= lineUpmostX && fromX <= lineDownmostX)
                    && (lineUpmostX <= toX && lineDownmostX <= toX);
        }
    }

    private static boolean isBetweenColumns(Line2P line, int fromYInput, int toYInput) {
        int fromY = Math.min(fromYInput, toYInput);
        int toY = Math.max(fromYInput, toYInput);

        if (line.isVertical()) {
            int lineY = line.getBeginning().getRight();
            return fromY <= lineY && toY >= lineY;
        } else {
            int lineLeftMostColumn = Math.min(line.getBeginning().getRight(), line.getBeginning().getRight());
            int lineRightMostColumn = Math.max(line.getBeginning().getRight(), line.getDestination().getRight());

            return (fromY <= lineLeftMostColumn && fromY <= lineRightMostColumn)
                    && (lineLeftMostColumn <= toY && lineRightMostColumn <= toY);
        }
    }

    public static boolean isVertical(Point pointA, Point pointB) {
        return pointA.getRight().equals(pointB.getRight());
    }

    public static boolean isHorizontal(Point pointA, Point pointB) {
        return pointA.getLeft().equals(pointB.getLeft());
    }

    public static Line2P of(Point pointA, Point pointB) {
        return new Line2P(pointA, pointB);
    }

    public static Direction getDirection(Point pointA, Point pointB) {
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
