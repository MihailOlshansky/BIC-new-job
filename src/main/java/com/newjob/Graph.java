package com.newjob;

import java.util.*;

public class Graph {
    private final Map<Node, Set<Node>> _graph = new HashMap<>();

    public void putAdjacentNodes(Node node, Collection<Node> adjacentNodes) {
        if (!_graph.containsKey(node)) {
            _graph.put(node, new HashSet<>());
        }

        _graph.get(node).addAll(adjacentNodes);
    }

    public Set<Node> getAdjacentNodes(Node node) { return _graph.get(node); }

    public int size() { return _graph.size(); }

    public Set<Node> nodes() { return _graph.keySet(); }

    public record Node(Integer position, String data, String original){
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass())
                return false;

            Node that = (Node) o;
            return Objects.equals(position, that.position) &&
                    Objects.equals(data, that.data);

        }
        @Override
        public int hashCode() {
            return Objects.hash(position, data);
        }
    }
}
