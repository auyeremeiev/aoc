package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ImmediateAccessList<T> {
    private List<T> list;
    private Map<T, Integer> elementToIndexMap;

    public ImmediateAccessList() {
        list = new ArrayList<>();
        elementToIndexMap = new HashMap<>();
    }

    public void add(T element) {
        list.add(element);
        elementToIndexMap.put(element, list.size() - 1);
    }

    public Optional<Integer> getElementIndex(T element) {
        if (!elementToIndexMap.containsKey(element)) {
            return Optional.empty();
        }
        return Optional.of(elementToIndexMap.get(element));
    }

    public T getElementAt(int index) {
        return list.get(index);
    }
}
