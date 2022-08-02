package ru.tinkoff.dts.conference.ant.app.algorithm;

import ru.tinkoff.dts.conference.ant.app.model.Road;
import ru.tinkoff.dts.conference.ant.app.model.Solution;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Logger {
    static void iteration(int iter, Solution currentSolution) {
        System.out.println("iter " + iter + " " + currentSolution.getPathLength());
    }

    static void pheromoneToConsole(List<Road> roads) {
        List<Float> pheromoneSorted = roads.stream()
                .map(Road::getPheromone)
                .sorted()
                .toList();

        System.out.println(
                getLowest(pheromoneSorted) + " ... " + getHighest(pheromoneSorted)
        );
    }

    private static String getHighest(List<Float> sorted) {
        return sorted.stream()
                .sorted(Comparator.reverseOrder())
                .limit(5)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    private static String getLowest(List<Float> sorted) {
        return sorted.stream()
                .limit(5)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
