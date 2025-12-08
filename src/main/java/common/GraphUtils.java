package common;

import java.util.*;

public class GraphUtils {

    public static  <T extends AbstractNode> List<Set<T>> findAllGraphs(Set<T> nodes) {
        if (nodes.size() == 0) {
            return new ArrayList<>();
        }

        List<Set<T>> graphs = new ArrayList<>();
        Set<T> remainingNodes = new HashSet<>(nodes);


        while (!remainingNodes.isEmpty()) {
            T rootNode = remainingNodes.iterator().next();
            remainingNodes.remove(rootNode);

            graphs.add(buildGraph(rootNode, remainingNodes));
        }

        return graphs;
    }

    private static <T extends AbstractNode> Set<T> buildGraph(T rootNode, Set<T> remainingNodes) {
        Set<T> currentGraph = new HashSet<>();
        Queue<T> queue = new ArrayDeque<>();

        queue.add(rootNode);
        currentGraph.add(rootNode);

        while (!queue.isEmpty()) {
            T currentNode = queue.poll();
            currentGraph.add(currentNode);

            currentNode.getAdjacentNodes()
                    .stream()
                    .filter(it -> remainingNodes.contains(it))
                    .forEach(it -> {
                        queue.add((T) it);
                        remainingNodes.remove(it);
                        currentGraph.add((T) it);
                    });
        }

        return currentGraph;
    }
}
