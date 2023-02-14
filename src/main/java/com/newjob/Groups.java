package com.newjob;

import java.util.*;

public class Groups {
    private final Map<Integer, List<String>> groups = new HashMap<>();
    private final List<Map<String, Integer>> graph = new ArrayList<>();
    private final Map<Integer, Integer> merges = new HashMap<>();
    private int numberOfGroups = 0;

    public List<List<String>> getGroups() {
        List<List<String>> result = new LinkedList<>();
        groups.values().forEach(group -> {
            if (group != null) {
                result.add(group);
            }
        });

        return result;
    }

    public void putElements(String[] elements, String original) {
        List<Integer> foundGroupNumbers = new LinkedList<>();
        List<Integer> newElementIndexes = new LinkedList<>();

        for (int i = 0; i < elements.length; i++) {
            if (elements[i].isBlank()) continue;

            if (graph.size() <= i) {
                graph.add(new HashMap<>());
            }

            Map<String, Integer> elementToGroupMap = graph.get(i);
            if (!elementToGroupMap.containsKey(elements[i])) {
                newElementIndexes.add(i);
            } else {
                foundGroupNumbers.add(elementToGroupMap.get(elements[i]));
            }
        }

        int primeGroup = findPrimeGroup(foundGroupNumbers, newElementIndexes, elements);
        addNewElements(newElementIndexes, elements, primeGroup, original);
        mergeGroups(foundGroupNumbers, primeGroup);
    }

    private int findPrimeGroup(
        List<Integer> foundGroupNumbers,
        List<Integer> newElementIndexes,
        String[] elements
    ) {
        int primeGroup;
        if (foundGroupNumbers.isEmpty()) {
            primeGroup = numberOfGroups++;
            groups.put(primeGroup, new ArrayList<>());
        } else {
            primeGroup = foundGroupNumbers.get(0);
            while (merges.containsKey(primeGroup)) {
                primeGroup = merges.get(primeGroup);
            }
        }

        return primeGroup;
    }

    private void addNewElements(
        List<Integer> newElementIndexes,
        String[] elements,
        int primeGroup,
        String original
    ) {
        List<String> curGroup = groups.get(primeGroup);
        curGroup.add(original);
        newElementIndexes.forEach(i -> graph.get(i).put(elements[i], primeGroup));

    }

    private void mergeGroups(
        List<Integer> foundGroupNumbers,
        int primeGroup
    ) {
        List<String> curGroup = groups.get(primeGroup);
        foundGroupNumbers.forEach(groupNumber -> {
            if (groupNumber == primeGroup) return;

            merges.put(groupNumber, primeGroup);
            while (merges.containsKey(groupNumber)) {
                groupNumber = merges.get(groupNumber);
            }
            if (groupNumber == primeGroup) return;

            curGroup.addAll(groups.get(groupNumber));
            curGroup.set(groupNumber, null);
        });
    }
}
