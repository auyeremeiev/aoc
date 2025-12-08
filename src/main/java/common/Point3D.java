package common;

import java.util.Objects;

import static java.lang.Math.pow;

public class Point3D {
    private final double x;
    private final double y;
    private final double z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public static Point3D of(double x, double y, double z) {
        return new Point3D(x, y, z);
    }

    public static double distance(Point3D point1, Point3D point2) {
        return Math.sqrt(
                (pow(point2.getX() - point1.getX(), 2) +
                        pow(point2.getY() - point1.getY(), 2) +
                        pow(point2.getZ() - point1.getZ(), 2)
                ));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return Double.compare(x, point3D.x) == 0 && Double.compare(y, point3D.y) == 0 && Double.compare(z, point3D.z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
