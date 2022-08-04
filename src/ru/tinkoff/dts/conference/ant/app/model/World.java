package ru.tinkoff.dts.conference.ant.app.model;

import java.util.List;

public class World {
    private List<City> cities;
    private List<Road> roads;
    private Solution solution;

    private BestSolution bestSolution;

    public World(List<City> cities) {
        this.cities = cities;
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
        this.roads = solution.getRoads();
    }

    public BestSolution getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(BestSolution bestSolution) {
        this.bestSolution = bestSolution;
    }
}
