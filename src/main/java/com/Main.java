package com;

import com.newjob.FindGroups;
import com.newjob.Graph;
import com.newjob.Group;
import com.newjob.ParseToGraph;

import java.io.*;
import java.util.Collection;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {
        File outputFile = new File("output.txt");
        outputFile.createNewFile();
        PrintStream output = new PrintStream(outputFile);

        int index = 1;
        if (args.length < index + 1) {
            printException(output, "Insert the file to parse, please");
            return;
        }

        File file = new File(args[index]);

        long start = System.nanoTime();
        Graph graph;
        try {
            graph = ParseToGraph.parse(file);
        } catch (IOException e) {
            printException(output, String.format("Wrong filepath %s%n", file.getAbsolutePath()));
            return;
        }
        var parseTime = System.nanoTime();
        System.out.printf("Парсинг: %d секунд%n", toSec(parseTime - start));

        Collection<Group> groups = FindGroups.findGroups(graph);

        long end = System.nanoTime();
        System.out.printf("Группировка: %d секунд%n", toSec(end - parseTime));
        printResult(output, groups, toSec(end - start));
    }

    private static void printException(PrintStream ps, String message) {
        ps.println(message);
    }

    private static void printResult(PrintStream ps, Collection<Group> groups, int duration) {

        ps.printf("Время исполнения программы: %d секунд%n", duration);
        ps.printf(
                "Групп больше чем с 1 элементом %d%n",
                groups.stream()
                        .filter(group -> group.size() > 1)
                        .count());

        int[] index = {1};
        groups.stream()
                .sorted(Comparator.comparingInt(group -> -group.size()))
                .forEach(group -> {
            ps.printf("Группа %d (%d строк)%n", index[0]++, group.size());
            group.members().forEach(ps::println);
        });
    }

    private static int toSec(long nanoSec) { return (int) (nanoSec / 1e9); }
}
