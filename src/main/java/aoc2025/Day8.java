package aoc2025;

import common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Day8 {

    private static final Logger log = LogManager.getLogger(Day8.class);

    public static int part1(String input, int boxesLimit) {
        List<Node3D> parsedInput = parse(input);

        return part1(parsedInput, boxesLimit);
    }

    public static int part1(List<Node3D> input, int boxesLimit) {
        List<WeightedEdge3D> edges = calculateDistances(input);

        List<Set<Node3D>> circuits = buildCircuits(edges, boxesLimit);
        circuits.sort(Comparator.comparing(Set::size));

        return circuits.get(circuits.size() - 1).size()
                * circuits.get(circuits.size() - 2).size()
                * circuits.get(circuits.size() - 3).size();
    }


    public static List<Set<Node3D>> buildCircuits(List<WeightedEdge3D> edges, int boxesLimit) {
        edges.sort(Comparator.comparing(WeightedEdge3D::getWeight));
        edges = edges.stream().limit(boxesLimit).toList();
        Set<Node3D> allNodes = new HashSet<>();
        edges.forEach(it -> {
            Node3D firstNode = it.getFirstNode();
            Node3D secondNode = it.getSecondNode();

            firstNode.addAdjacentNode(secondNode);
            allNodes.add(firstNode);
            allNodes.add(secondNode);
        });


        return GraphUtils.findAllGraphs(allNodes);
    }

    public static Long part2(String input) {
        List<Node3D> parse = parse(input);
        return part2(parse);
    }

    public static Long part2(List<Node3D> input) {
        List<WeightedEdge3D> distances = calculateDistances(input);
        return buildCircuitPart2(distances, input.size());
    }

    public static long buildCircuitPart2(List<WeightedEdge3D> edges, long totalNodesNumber) {
        edges.sort(Comparator.comparing(WeightedEdge3D::getWeight));
        List<Set<Node3D>> graphs = new ArrayList<>();

        for (WeightedEdge3D it : edges) {
            Node3D firstNode = it.getFirstNode();
            Node3D secondNode = it.getSecondNode();

            Optional<Set<Node3D>> firstNodeGraph = containedInGraph(graphs, firstNode, Optional.empty());
            Optional<Set<Node3D>> secondNodeGraph = containedInGraph(graphs, secondNode, firstNodeGraph);

            if (firstNodeGraph.isEmpty() && secondNodeGraph.isEmpty()) {
                HashSet<Node3D> newGraph = new HashSet<>();
                newGraph.add(firstNode);
                newGraph.add(secondNode);
                graphs.add(newGraph);
            } else if (firstNodeGraph.isPresent() && secondNodeGraph.isEmpty()) {
                firstNodeGraph.get().add(secondNode);
            } else if (firstNodeGraph.isEmpty() && secondNodeGraph.isPresent()) {
                secondNodeGraph.get().add(firstNode);
            } else if (firstNodeGraph.isPresent() && secondNodeGraph.isPresent() && firstNodeGraph.get() != secondNodeGraph.get()) {
                // merge
                Set<Node3D> firstGraph = firstNodeGraph.get();
                Set<Node3D> secondGraph = secondNodeGraph.get();
                firstGraph.addAll(secondGraph);

                graphs.remove(secondGraph);
            }

            if (graphs.size() == 1 && graphs.get(0).size() == totalNodesNumber) {
                return ((long) firstNode.getX()) * ((long) secondNode.getX());
            }
        }

        return -1;
    }

    public static Optional<Set<Node3D>> containedInGraph(
            List<Set<Node3D>> graphs,
            Node3D node,
            Optional<Set<Node3D>> exceptGraph) {
        return graphs.stream().filter(graph ->
                        exceptGraph.map(except -> graph != except).orElse(true))
                .filter(it -> it.contains(node)).findFirst();
    }

    public static List<WeightedEdge3D> calculateDistances(List<Node3D> nodes) {
        Set<WeightedEdge3D> processedEdges = new HashSet<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node3D currentNode = nodes.get(i);

            for (int j = i + 1; j < nodes.size(); j++) {
                Node3D secondNode = nodes.get(j);

                processedEdges.add(
                        WeightedEdge3D.of(
                                currentNode,
                                secondNode,
                                Node3D.distance(currentNode, secondNode)
                        )
                );
            }
        }
        return new ArrayList<>(processedEdges);
    }

    public static List<Node3D> parse(String input) {
        String[] lines = input.split("\n");
        return Arrays.stream(lines).map(it -> {
            String[] coordinates = it.split(",");
            return Node3D.of(
                    Double.parseDouble(coordinates[0]),
                    Double.parseDouble(coordinates[1]),
                    Double.parseDouble(coordinates[2]));
        }).toList();
    }

}
