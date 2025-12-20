package common;

import java.util.*;

public class Graph<T extends AbstractNode> {

    private final Map<String, T> nodes;

    public Graph() {
        nodes = new HashMap<>();
    }

    public void addNode(T node) {
        nodes.put(node.getId(), node);
    }

    public Optional<T> findNode(String id) {
        if (nodes.containsKey(id)) {
            return Optional.of(nodes.get(id));
        } else {
            return Optional.empty();
        }
    }
}
