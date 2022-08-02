package ru.tinkoff.dts.conference.ant.app.model;

import ru.tinkoff.dts.conference.ant.app.algorithm.AlgConfig;

import static java.lang.Math.*;

public class Road implements Element {
    private final City from;
    private final City to;
    private volatile float pheromone;

    public Road(City from, City to) {
        this.from = from;
        this.to = to;
    }

    public Road(City from, City to, float pheromone) {
        this.from = from;
        this.to = to;
        this.pheromone = pheromone;
    }

    public double distance() {
        return from.distanceTo(to);
    }
    public City getFrom() {
        return from;
    }

    public City getTo() {
        return to;
    }

    public float getPheromone() {
        return pheromone;
    }

    public void updatePheromoneWith(float pheromoneAdded) {
        pheromone = min(AlgConfig.PHEROMONE_MAX, max(0, pheromone + pheromoneAdded));
    }

    public void evaporate() {
        pheromone = pheromone * (1 - AlgConfig.PHEROMONE_EVAPORATED);
    }
}
