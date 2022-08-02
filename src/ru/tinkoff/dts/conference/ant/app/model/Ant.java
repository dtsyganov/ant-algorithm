package ru.tinkoff.dts.conference.ant.app.model;

import java.util.ArrayList;
import java.util.List;

public class Ant {
    private final List<City> path = new ArrayList<>();
    private double pathLength = 0;

    public Ant(City start) {
        addToPath(start);
    }

    public List<City> getPath() {
        return path;
    }

    public City getCurrentCity() {
        return path.get(path.size() - 1);
    }

    public City getPreCity() {
        return path.get(path.size() - 2);
    }

    public void addToPath(City city) {
        path.add(city);
        if (path.size() > 1)
            pathLength += getPreCity().distanceTo(getCurrentCity());
    }

    public double getPathLength() {
        return pathLength;
    }

    public void clearPath() {
        City city = path.get(0);
        path.clear();
        path.add(city);
        pathLength = 0;
    }
}
