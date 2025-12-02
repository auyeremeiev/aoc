package aoc2024.helpers;

import common.Pair;
import java.util.List;

public class Day5Data {
    private final List<Pair<Integer, Integer>> orderRules;
    private final List<List<Integer>> updatesList;

    public Day5Data(List<Pair<Integer, Integer>> orderRules, List<List<Integer>> updatesList) {
        this.orderRules = orderRules;
        this.updatesList = updatesList;
    }

    public List<Pair<Integer, Integer>> getOrderRules() {
        return orderRules;
    }

    public List<List<Integer>> getUpdatesList() {
        return updatesList;
    }
}
