package common;

import java.util.Objects;

public class PairWithoutOrder<L, R> {

    private L left;
    private R right;

    public PairWithoutOrder(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairWithoutOrder<?, ?> pair = (PairWithoutOrder<?, ?>) o;
        return (Objects.equals(left, pair.left) && Objects.equals(right, pair.right)) ||
                (Objects.equals(left, pair.right) && Objects.equals(right, pair.left));
    }

    @Override
    public int hashCode() {
        return 31 * left.hashCode() + 31 * right.hashCode();
    }

    @Override
    public String toString() {
        return "Pair{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }
}
