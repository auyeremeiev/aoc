package common;

import java.util.List;

public class Rectangle {

    private final Point upperLeft;
    private final Point upperRight;
    private final Point bottomRight;
    private final Point bottomLeft;

    public Rectangle(Point point1, Point point2) {
        if (point1.getLeft().equals(point2.getLeft()) &&
            point1.getRight() < point2.getRight()) {
            upperLeft = point1;
            bottomLeft = point1;
            upperRight = point2;
            bottomRight = point2;
        } else if (point1.getLeft().equals(point2.getLeft()) &&
                point1.getRight() > point2.getRight()) {
            upperLeft = point2;
            bottomLeft = point2;
            upperRight = point1;
            bottomRight = point1;
        } else if (point1.getRight().equals(point2.getRight()) &&
                point1.getLeft() < point2.getLeft()) {
            upperLeft = point1;
            upperRight = point1;
            bottomLeft = point2;
            bottomRight = point2;
        } else if (point1.getRight().equals(point2.getRight()) &&
            point1.getLeft() > point2.getLeft()) {
            upperLeft = point2;
            upperRight = point2;
            bottomLeft = point1;
            bottomRight = point1;
        } else if (point1.getRight() < point2.getRight()) {
            this.upperLeft = point1;
            this.upperRight = Point.of(upperLeft.getLeft(), point2.getRight());
            this.bottomRight = point2;
            this.bottomLeft = Point.of(point2.getLeft(), upperLeft.getRight());
        } else {
            this.upperLeft = point2;
            this.upperRight = Point.of(upperLeft.getLeft(), point1.getRight());
            this.bottomRight = point1;
            this.bottomLeft = Point.of(point1.getLeft(), upperLeft.getRight());
        }
    }

    public boolean isFlat() {
        return upperLeft.getLeft().equals(bottomRight.getLeft())
                || bottomRight.getRight().equals(upperLeft.getRight());
    }

    public long area() {
        return ((long) Math.abs(upperRight.getRight() - upperLeft.getRight()) + 1)
                * (long) (Math.abs(bottomRight.getLeft() - upperRight.getLeft()) + 1);
    }

    public List<Line2P> lines() {
        return List.of(upperLine(), rightLine(), bottomLine(), leftLine());
    }

    public Line2P upperLine() {
        return Line2P.of(upperLeft, upperRight);
    }

    public Line2P rightLine() {
        return Line2P.of(upperRight, bottomRight);
    }

    public Line2P bottomLine() {
        return Line2P.of(bottomRight, bottomLeft);
    }

    public Line2P leftLine() {
        return Line2P.of(bottomLeft, upperLeft);
    }

    public Point getUpperLeft() {
        return upperLeft;
    }

    public Point getUpperRight() {
        return upperRight;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public Point getBottomLeft() {
        return bottomLeft;
    }

    public List<Point> getPoints() {
        return List.of(upperLeft, upperRight, bottomRight, bottomLeft);
    }

    public static long area(Point upperLeft, Point bottomRight) {
        return ((long) (Math.abs(bottomRight.getRight() - upperLeft.getRight())) + 1)
                * (long) (Math.abs(bottomRight.getLeft() - upperLeft.getLeft()) + 1);
    }
}
