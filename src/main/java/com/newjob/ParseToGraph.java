package com.newjob;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ParseToGraph {
    private static final String DELIMITER = ";";

    public static Graph parse(File file) throws IOException {
        Graph graph = new Graph();
        Files.lines(file.toPath())
                .forEach((line) -> {
                    long dQuotes = line.chars().filter(ch -> ch == '"').count();
                    String[] data = line.split(DELIMITER);
                    if (dQuotes > data.length * 2L)
                        return;

                    List<Graph.Node> nodes = new LinkedList<>(IntStream
                            .range(0, data.length)
                            .mapToObj(i -> new Graph.Node(i, data[i], line))
                            .filter(node -> !node.data().isBlank())
                            .toList());

                    for (int i = 0; i < nodes.size(); i++) {
                        Graph.Node cur = nodes.remove(0);
                        graph.putAdjacentNodes(cur, nodes);
                        nodes.add(cur);
                    }
                });

        return graph;
    }
}
