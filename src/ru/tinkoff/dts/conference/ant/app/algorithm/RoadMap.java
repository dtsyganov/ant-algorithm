package ru.tinkoff.dts.conference.ant.app.algorithm;

import ru.tinkoff.dts.conference.ant.app.model.Ant;
import ru.tinkoff.dts.conference.ant.app.model.City;
import ru.tinkoff.dts.conference.ant.app.model.Road;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RoadMap {
    private final Road[][] ways;

    public RoadMap(List<City> cities) {
        ways = new Road[cities.size()][cities.size()];
        for (int i = 0; i < ways.length; i++) {
            for (int j = 0; j < ways[0].length; j++) {
                if (i == j) ways[i][j] = null;
                else ways[i][j] = new Road(cities.get(i), cities.get(j), AlgConfig.INITIAL_PHEROMONE);
            }
        }
    }

    public Road getRoad(City from, City to) {
        return ways[from.getId()][to.getId()];
    }

    public List<Road> getRoadsFrom(City city) {
        return Arrays.stream(ways[city.getId()])
                .filter(Objects::nonNull)
                .toList();
    }

    public List<Road> getAvailableRoadsForAnt(Ant ant) {
        return getRoadsFrom(ant.getCurrentCity())
                .stream()
                .filter(r -> !ant.getPath().contains(r.getTo()))
                .toList();
    }

    public void evaporate() {
        for (Road[] way : ways) {
            for (Road road : way) {
                if (road != null)
                    road.evaporate();
            }
        }
    }

    public void updateWithPheromone(Ant ant, float pheromone) {
        List<City> path = ant.getPath();
        for (int i = 0; i < path.size() - 1; i++) {
            updateWithPheromone(path.get(i), path.get(i + 1), pheromone);
        }
    }

    private void updateWithPheromone(City from, City to, float pheromone) {
        getRoad(from, to)
                .updatePheromoneWith((float) (pheromone / getRoad(from, to).distance()));
    }

    public List<Road> getWays() {
        return Arrays.stream(ways)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .toList();
    }
}
