package com;

import com.newjob.*;

import java.io.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        long start = System.nanoTime();

        PrintStream output = getOutputPrintStream();

        if (args.length == 0) {
            printException(output, "Insert the file to parse, please");
            return;
        }

        File file = new File(args[args.length - 1]);

        Groups groups;
        try {
            groups = FindGroups.parse(file);
        } catch (IOException e) {
            printException(output, String.format("Wrong filepath %s%n", file.getAbsolutePath()));
            return;
        }

        long end = System.nanoTime();
        printResult(output, groups.getGroups(), start, end);
    }

    private static PrintStream getOutputPrintStream() throws IOException {
        File outputFile = new File("output.txt");
        outputFile.createNewFile();
        return new PrintStream(outputFile);
    }

    private static void printException(PrintStream ps, String message) {
        ps.println(message);
    }

    private static void printResult(PrintStream ps, Collection<List<String>> groups, long start, long end){
        System.out.printf("Algorithm: %d seconds%n", toSec(end - start));
        printGroups(ps, groups, toSec(end - start));
        System.out.printf("Output: %d seconds%n", toSec(System.nanoTime() - end));
        System.out.printf("Whole program: %d seconds%n", toSec(System.nanoTime() - start));
    }

    private static void printGroups(PrintStream ps, Collection<List<String>> groups, int duration) {

        ps.printf("Время алгоритма группировки: %d секунд%n", duration);
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
            group.forEach(ps::println);
        });
    }

    private static int toSec(long nanoSec) { return (int) (nanoSec / 1e9); }
}
