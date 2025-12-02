package common;

import java.util.function.Consumer;

public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    public boolean isHorizontal() {
        return this == RIGHT || this == LEFT;
    }

    public boolean isVertical() {
        return this == UP || this == DOWN;
    }

    public Direction switchClockWise() {
        return switch (this) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }

    public Direction switchCounterClockWise() {
        return switch (this) {
            case UP -> Direction.LEFT;
            case LEFT -> Direction.DOWN;
            case DOWN -> Direction.RIGHT;
            case RIGHT -> Direction.UP;
        };
    }

    public Point nextPoint(Point point) {
        return switch (this) {
            case UP -> new Point(point.getLeft() - 1, point.getRight());
            case LEFT -> new Point(point.getLeft(), point.getRight() - 1);
            case DOWN -> new Point(point.getLeft() + 1, point.getRight());
            case RIGHT -> new Point(point.getLeft(), point.getRight() + 1);
        };
    }

    public static Direction getDirection(Point point1, Point point2) {
        if (point1.equals(point2)) {
            throw new IllegalArgumentException("Points are equal");
        }

        if (point1.getRight().equals(point2.getRight())) {
            if (point1.getLeft() == point2.getLeft() + 1) {
                return Direction.DOWN;
            } else if (point1.getLeft() == point2.getLeft() - 1) {
                return Direction.UP;
            }
        } else if (point1.getLeft().equals(point2.getLeft())) {
            if (point1.getRight().equals(point2.getRight() + 1)) {
                return Direction.RIGHT;
            } else if (point1.getRight().equals(point2.getRight() - 1)) {
                return Direction.LEFT;
            }
        }

        throw new IllegalArgumentException("Don't support diagonal directions yet");
    }

    public static void traverseAllDirectionsPoints(Point point, Direction startingDirection, Consumer<Point> func) {
        Direction currentDirection = startingDirection;
        func.accept(currentDirection.nextPoint(point));
        currentDirection = currentDirection.switchClockWise();

        while (currentDirection != startingDirection) {
            func.accept(currentDirection.nextPoint(point));
            currentDirection = currentDirection.switchClockWise();
        }
    }

    public static void traverseAllDirections(Direction startingDirection, Consumer<Direction> func) {
        Direction currentDirection = startingDirection;
        func.accept(currentDirection);
        currentDirection = currentDirection.switchClockWise();

        while (currentDirection != startingDirection) {
            func.accept(currentDirection);
            currentDirection = currentDirection.switchClockWise();
        }
    }
}
