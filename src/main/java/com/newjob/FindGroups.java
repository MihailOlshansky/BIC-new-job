package com.newjob;

import java.util.*;

public class FindGroups {
    public static Collection<Group> findGroups(Graph graph) {
        List<Group> groups = new LinkedList<>();
        Set<Graph.Node> visited = new HashSet<>();
        graph.nodes().forEach(node -> {
            if (!visited.contains(node)) {
                visited.add(node);
                groups.add(findGroup(node, graph, visited));
            }
        });

        return groups;
    }

    private static Group findGroup(Graph.Node start, Graph graph, Set<Graph.Node> visited) {
        Set<String> members = new HashSet<>();
        Queue<Graph.Node> q = new LinkedList<>();
        q.add(start);
        while (!q.isEmpty()) {
            Graph.Node cur = q.poll();
            members.add(cur.original());

            graph.getAdjacentNodes(cur)
                    .forEach(node -> {
                        if (!visited.contains(node)){
                            visited.add(node);
                            q.add(node);
                        }
                    });
        }

        return new Group(members);
    }
}
