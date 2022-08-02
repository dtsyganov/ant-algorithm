package ru.tinkoff.dts.conference.ant.app.model;

import java.util.ArrayList;
import java.util.List;

public class Solution implements Element {
    private final List<City> list;
    private final List<Road> roads;
    private final double pathLength;

    public Solution(List<City> list, List<Road> roads) {
        this.list = new ArrayList<>(list);
        if (list.get(0) != list.get(list.size() - 1))
            this.list.add(list.get(0));

        pathLength = calcPathLength();
        this.roads = roads;
    }

    private double calcPathLength() {
        return calcPathLength(list);
    }

    public List<City> getList() {
        return list;
    }

    public double getPathLength() {
        return pathLength;
    }

    public static double calcPathLength(List<City> cities) {
        double path = 0;
        for (int i = 0; i < cities.size() - 1; i++) {
            path += cities.get(i).distanceTo(cities.get(i + 1));
        }
        return path;
    }

    public List<Road> getRoads() {
        return roads;
    }
}
