package ru.tinkoff.dts.conference.ant.app.model;

import java.util.List;

public class World {
    private List<City> cities;
    private List<Road> roads;
    private Solution solution;

    public World(List<City> cities, List<Road> roads) {
        this.cities = cities;
        this.roads = roads;
    }

    public List<City> getCities() {
        return cities;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }
}
