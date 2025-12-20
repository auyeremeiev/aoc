package aoc2025;

import common.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Day11 {

    private static final Logger log = LogManager.getLogger(Day11.class);

    public static long part1(String input) {
        List<Pair<String, List<String>>> parsedInput = parse(input);

        return part1(parsedInput);
    }

    public static long part1(List<Pair<String, List<String>>> input) {
        Pair<DirectionalNode, Graph<DirectionalNode>> task = buildGraph(input, "you");
        DirectionalNode startingNode = task.getLeft();
        Map<DirectionalNode, Long> cache = new HashMap<>();

        return findAllPaths(startingNode, cache);
    }

    public static long findAllPaths(
            DirectionalNode currentNode,
            Map<DirectionalNode, Long> cache) {
        if (cache.containsKey(currentNode)) {
            return cache.get(currentNode);
        }
        if (currentNode.getId().equals("out")) {
            return 1;
        }

        if (currentNode.getAdjacentNodes().isEmpty()) {
            return 0;
        }

        long allPaths = 0;
        for (DirectionalNode adjacentNode : currentNode.getAdjacentNodes()) {
            allPaths += findAllPaths(adjacentNode, cache);
        }

        cache.put(currentNode, allPaths);

        return allPaths;
    }

    private static Pair<DirectionalNode, Graph<DirectionalNode>> buildGraph(
            List<Pair<String, List<String>>> parsedInput, String startingId) {
        Graph<DirectionalNode> graph = new Graph<>();
        DirectionalNode startingNode = null;

        for (Pair<String, List<String>> nodesAdjacency : parsedInput) {
            DirectionalNode currentNode = findOrCreate(nodesAdjacency.getLeft(), graph);

            if (currentNode.getId().equals(startingId)) {
                startingNode = currentNode;
            }

            List<String> adjacentNodes = nodesAdjacency.getRight();
            for (String adjacentNode : adjacentNodes) {
                DirectionalNode nodeToAdd = findOrCreate(adjacentNode, graph);
                currentNode.addAdjacentNode(nodeToAdd);
            }
        }
        return Pair.of(startingNode, graph);
    }

    private static DirectionalNode findOrCreate(String nodeId, Graph<DirectionalNode> graph) {
        return graph.findNode(nodeId)
                .orElseGet(() -> {
                    DirectionalNode adjacentNewNode = new DirectionalNode(nodeId);
                    graph.addNode(adjacentNewNode);
                    return adjacentNewNode;
                });
    }

    public static long part2(String input) {
        List<Pair<String, List<String>>> parse = parse(input);
        return part2(parse);
    }

    public static long part2(List<Pair<String, List<String>>> input) {
        Pair<DirectionalNode, Graph<DirectionalNode>> task = buildGraph(input, "svr");
        DirectionalNode startingNode = task.getLeft();
        Map<DirectionalNode, SubgraphResult> cache = new HashMap<>();

        return findAllPathsPart2(startingNode, cache).pathsContainingBoth;
    }

    private static SubgraphResult findAllPathsPart2(DirectionalNode currentNode,
                                                    Map<DirectionalNode, SubgraphResult> cache) {
        if (cache.containsKey(currentNode)) {
            return cache.get(currentNode);
        }
        if (currentNode.getId().equals("out")) {
            return SubgraphResult.ofSingleNone();
        }

        if (currentNode.getAdjacentNodes().isEmpty()) {
            return SubgraphResult.ofSingleNone();
        }

        boolean currentNodeIsFft = currentNode.getId().equals("fft");
        boolean currentNodeIsDac = currentNode.getId().equals("dac");

        SubgraphResult currentResult = new SubgraphResult();

        for (DirectionalNode adjacentNode : currentNode.getAdjacentNodes()) {
            SubgraphResult resultsForAdjacent = findAllPathsPart2(adjacentNode, cache).copy();

            if (currentNodeIsFft) {
                resultsForAdjacent.addFft();
            } else if (currentNodeIsDac) {
                resultsForAdjacent.addDac();
            }

            currentResult.combine(resultsForAdjacent);
        }

        cache.put(currentNode, currentResult);
        return currentResult;
    }

    public static List<Pair<String, List<String>>> parse(String input) {
        String[] lines = input.split("\n");
        return Arrays.stream(lines).map(it -> {
            String[] split = it.split(":");
            String node = split[0].trim();
            List<String> connections = Arrays.stream(split[1].split(" "))
                    .map(String::trim)
                    .filter(id -> !id.isEmpty())
                    .toList();

            return Pair.of(
                    node,
                    connections
            );
        }).toList();
    }

    private static class SubgraphResult {
        private long pathsContainingNone;
        private long pathsContainingOnlyFft;
        private long pathsContainingOnlyDac;
        private long pathsContainingBoth;

        public SubgraphResult() {
        }

        public SubgraphResult(long pathsContainingNone,
                              long pathsContainingOnlyFft,
                              long pathsContainingOnlyDac,
                              long pathsContainingBoth) {
            this.pathsContainingNone = pathsContainingNone;
            this.pathsContainingOnlyFft = pathsContainingOnlyFft;
            this.pathsContainingOnlyDac = pathsContainingOnlyDac;
            this.pathsContainingBoth = pathsContainingBoth;
        }


        public void addDac() {
            if (this.pathsContainingOnlyFft > 0) {
                this.pathsContainingBoth += this.pathsContainingOnlyFft;
                this.pathsContainingOnlyFft = 0;
            }

            addDacToNone();
        }

        public void addDacToNone() {
            this.pathsContainingOnlyDac = this.pathsContainingNone;
            this.pathsContainingNone = 0;
        }

        public void addFft() {
            if (this.pathsContainingOnlyDac > 0) {
                this.pathsContainingBoth += pathsContainingOnlyDac;
                this.pathsContainingOnlyDac = 0;
            }

            addFftToNone();
        }

        private void addFftToNone() {
            this.pathsContainingOnlyFft += this.pathsContainingNone;
            this.pathsContainingNone = 0;
        }

        public void combine(SubgraphResult result) {
            this.pathsContainingNone += result.pathsContainingNone;
            this.pathsContainingOnlyDac += result.pathsContainingOnlyDac;
            this.pathsContainingOnlyFft += result.pathsContainingOnlyFft;
            this.pathsContainingBoth += result.pathsContainingBoth;
        }

        public SubgraphResult copy() {
            return new SubgraphResult(pathsContainingNone, pathsContainingOnlyFft, pathsContainingOnlyDac, pathsContainingBoth);
        }

        public static SubgraphResult ofSingleNone() {
            return new SubgraphResult(1L, 0, 0, 0);
        }
    }
}
