package common;

import java.util.*;

public class DirectionalNode implements AbstractNode<DirectionalNode> {

    private final String id;

    private final Set<DirectionalNode> adjacentNodes;

    public DirectionalNode(String id) {
        this.id = id;
        this.adjacentNodes = new HashSet<>();
    }

    public DirectionalNode() {
        this.id = UUID.randomUUID().toString();
        this.adjacentNodes = new HashSet<>();
    }

    @Override
    public void addAdjacentNode(DirectionalNode node) {
        adjacentNodes.add(node);
    }

    @Override
    public boolean containsAdjacentNode(DirectionalNode node) {
        return adjacentNodes.contains(node);
    }

    @Override
    public void removeAdjacentNode(DirectionalNode node) {
        adjacentNodes.remove(node);
    }

    @Override
    public Set<DirectionalNode> getAdjacentNodes() {
        return adjacentNodes;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DirectionalNode that = (DirectionalNode) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DirectionalNode{" +
                "id='" + id + '\'' +
                ", adjacentNodes=" + adjacentNodes +
                '}';
    }
}
