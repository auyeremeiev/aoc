package common;

import java.util.List;
import java.util.function.Consumer;

public enum DirectionWithDiagonal {
    UP,
    UP_RIGHT,
    RIGHT,
    DOWN_RIGHT,
    DOWN,
    DOWN_LEFT,
    LEFT,
    UP_LEFT;

    public boolean isHorizontal() {
        return this == RIGHT || this == LEFT;
    }

    public boolean isVertical() {
        return this == UP || this == DOWN;
    }

    public DirectionWithDiagonal switchClockWise() {
        return switch (this) {
            case UP -> DirectionWithDiagonal.UP_RIGHT;
            case UP_RIGHT -> DirectionWithDiagonal.RIGHT;
            case RIGHT -> DirectionWithDiagonal.DOWN_RIGHT;
            case DOWN_RIGHT -> DirectionWithDiagonal.DOWN;
            case DOWN -> DirectionWithDiagonal.DOWN_LEFT;
            case DOWN_LEFT -> DirectionWithDiagonal.LEFT;
            case LEFT -> DirectionWithDiagonal.UP_LEFT;
            case UP_LEFT -> DirectionWithDiagonal.UP;
        };
    }

    public Point nextPoint(Point point) {
        return switch (this) {
            case UP -> new Point(point.getLeft() - 1, point.getRight());
            case UP_RIGHT -> new Point(point.getLeft() - 1, point.getRight() + 1);
            case RIGHT -> new Point(point.getLeft(), point.getRight() + 1);
            case DOWN_RIGHT -> new Point(point.getLeft() + 1, point.getRight() + 1);
            case DOWN -> new Point(point.getLeft() + 1, point.getRight());
            case DOWN_LEFT -> new Point(point.getLeft() + 1, point.getRight() - 1);
            case LEFT -> new Point(point.getLeft(), point.getRight() - 1);
            case UP_LEFT -> new Point(point.getLeft() - 1, point.getRight() - 1);
        };
    }

    public static <T> void traverseAllDirectionsPointsInsideMatrix(
            Point point,
            List<List<T>> matrix,
            Consumer<Point> func) {
        traverseAllDirectionsPointsInsideMatrix(point, DirectionWithDiagonal.UP, matrix, func);
}

    public static <T> void traverseAllDirectionsPointsInsideMatrix(
            Point point,
            DirectionWithDiagonal startingDirection,
            List<List<T>> matrix,
            Consumer<Point> func) {
        DirectionWithDiagonal currentDirection = startingDirection;
        Point nextPoint = currentDirection.nextPoint(point);
        if (nextPoint.insideMatrix(matrix)) {
            func.accept(nextPoint);
        }
        currentDirection = currentDirection.switchClockWise();

        while (currentDirection != startingDirection) {
            nextPoint = currentDirection.nextPoint(point);
            if (nextPoint.insideMatrix(matrix)) {
                func.accept(nextPoint);
            }
            currentDirection = currentDirection.switchClockWise();
        }
    }
}
