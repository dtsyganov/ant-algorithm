package ru.tinkoff.dts.conference.ant.app.model;

import ru.tinkoff.dts.conference.ant.app.algorithm.AlgConfig;

import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Math.*;

public class Road implements Element {
    private final City from;
    private final City to;
    private final AtomicReference<Float> pheromone = new AtomicReference<>();

    public Road(City from, City to) {
        this.from = from;
        this.to = to;
    }

    public Road(City from, City to, float pheromone) {
        this.from = from;
        this.to = to;
        this.pheromone.set(pheromone);
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
        return pheromone.get();
    }

    public void updatePheromoneWith(float pheromoneAdded) {
        pheromone.getAndAccumulate(pheromoneAdded, (cur, add) -> min(AlgConfig.PHEROMONE_MAX, max(0, cur + add)));
    }

    public void evaporate() {
        pheromone.getAndAccumulate(AlgConfig.PHEROMONE_EVAPORATED, (cur, koeff)-> cur * (1 - koeff));
    }
}
