package common;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Node3D extends Point3D implements AbstractNode<Node3D> {

    private final Set<Node3D> adjacentNodes;
    private final String id;

    public Node3D(double x, double y, double z) {
        super(x, y, z);
        adjacentNodes = new HashSet<>();
        id = UUID.randomUUID().toString();
    }

    @Override
    public String getId() {
        return id;
    }

    public void addAdjacentNode(Node3D node3D) {
        if (!adjacentNodes.contains(node3D)) {
            adjacentNodes.add(node3D);
            node3D.addAdjacentNode(this);
        }
    }

    public boolean containsAdjacentNode(Node3D node3D) {
        return adjacentNodes.contains(node3D);
    }

    public void removeAdjacentNode(Node3D node3D) {
        adjacentNodes.remove(node3D);
        node3D.removeAdjacentNode(this);
    }

    public Set<Node3D> getAdjacentNodes() {
        return adjacentNodes;
    }

    public static Node3D of(double x, double y, double z) {
        return new Node3D(x, y, z);
    }
}
