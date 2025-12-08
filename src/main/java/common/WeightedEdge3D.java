package common;

import java.util.Objects;

public class WeightedEdge3D {

    private Node3D firstNode;
    private Node3D secondNode;
    private double weight;

    public WeightedEdge3D(Node3D firstNode, Node3D secondNode, double weight) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.weight = weight;
    }

    public Node3D getFirstNode() {
        return firstNode;
    }

    public Node3D getSecondNode() {
        return secondNode;
    }

    public double getWeight() {
        return weight;
    }

    public static WeightedEdge3D of(Node3D firstNode, Node3D secondNode, double weight) {
        return new WeightedEdge3D(firstNode, secondNode, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) return false;
        WeightedEdge3D that = (WeightedEdge3D) o;
        return (Objects.equals(firstNode, that.firstNode) && Objects.equals(secondNode, that.secondNode))
                ||  (Objects.equals(that.secondNode, firstNode) && Objects.equals(that.firstNode, secondNode)) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstNode) + Objects.hash(secondNode);
    }
}
