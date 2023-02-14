package com.newjob;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class FindGroups {
    private static final String DELIMITER = ";";

    public static Groups parse(File file) throws IOException {
        Groups groups = new Groups();
        Files.lines(file.toPath())
                .forEach((line) -> {
                    long dQuotes = line.chars().filter(ch -> ch == '"').count();
                    String[] data = line.split(DELIMITER);

                    if (dQuotes > Arrays.stream(data).filter(el -> !el.isBlank()).count() * 2)
                        return;

                    groups.putElements(data, line);
                });

        return groups;
    }
}
