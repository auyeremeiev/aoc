package common;

import java.util.Set;

public interface AbstractNode<T> {

    String getId();

    void addAdjacentNode(T node);

    boolean containsAdjacentNode(T node3D);

    void removeAdjacentNode(T node3D);

    Set<T> getAdjacentNodes();
}
