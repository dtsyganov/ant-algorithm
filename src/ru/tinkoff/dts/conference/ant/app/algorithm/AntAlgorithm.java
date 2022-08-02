package ru.tinkoff.dts.conference.ant.app.algorithm;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.Rnd;
import ru.tinkoff.dts.conference.ant.app.model.Ant;
import ru.tinkoff.dts.conference.ant.app.model.City;
import ru.tinkoff.dts.conference.ant.app.model.Road;
import ru.tinkoff.dts.conference.ant.app.model.Solution;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class AntAlgorithm {
    private final List<City> cities;
    private final Road[][] ways;
    private final BlockingQueue<Solution> notificationQueue;
    private final List<Ant> ants = new ArrayList<>(AlgConfig.NUMBER_OF_ANTS);
    private Solution bestSolution;

    public AntAlgorithm(List<City> cities, BlockingQueue<Solution> notificationQueue) {
        this.cities = cities;
        ways = new Road[cities.size()][cities.size()];
        for (int i = 0; i < ways.length; i++) {
            for (int j = 0; j < ways[0].length; j++) {
                if (i == j) ways[i][j] = null;
                else ways[i][j] = new Road(cities.get(i), cities.get(j), AlgConfig.INITIAL_PHEROMONE);
            }
        }
        this.notificationQueue = notificationQueue;
    }

    public Solution findSolution() {
        createAnts();
        for (int iter = 0; iter < AlgConfig.NUMBER_OF_ITERATIONS; iter++) {
            for (int i = 0; i < cities.size() - 1; i++) {
                chooseWayForEveryAnt();
                updatePheromone();
            }
            Solution currentSolution = chooseBestSolution();

            System.out.println("iter " + iter + " " + currentSolution.getPathLength());
            if (Config.PHEROMONE_TO_CONSOLE) {
                pheromoneToConsole();
            }
            clearSolutions();
        }
        return bestSolution;
    }

    private void clearSolutions() {
        for (Ant ant : ants) {
            ant.clearPath();
        }
        for (Road[] roads : ways) {
            for (Road road : roads) {
                if (road != null) road.setPheromone(AlgConfig.INITIAL_PHEROMONE);
            }
        }
    }

    private Solution chooseBestSolution() {
        Ant bestAnt = ants.stream()
                .min(Comparator.comparingDouble(Ant::getPathLength))
                .orElseThrow();
        Solution solution = new Solution(bestAnt.getPath());

        if (AlgConfig.APPLY_BRUTEFORCE_EACH_FOUR) {
            solution = applyPartialBruteForce(solution);
        }

        bestSolution = (bestSolution == null || bestSolution.getPathLength() > solution.getPathLength()) ?
                solution : bestSolution;

        notificationQueue.add(new Solution(bestSolution.getList()));
        return solution;
    }

    private Solution applyPartialBruteForce(Solution solution) {
        List<City> path = solution.getList().subList(0, solution.getList().size() - 1);

        for (int i = 0; i < path.size() - 4; i++) {
            List<City> optimalFour = optimal(path.subList(i, i + 4));
            path.set(i, optimalFour.get(0));
            path.set(i + 1, optimalFour.get(1));
            path.set(i + 2, optimalFour.get(2));
            path.set(i + 3, optimalFour.get(3));
        }
        return new Solution(path);
    }

    private List<City> optimal(List<City> best) {
        List<City> solution = swap(best);
        if (Solution.calcPathLength(best) > Solution.calcPathLength(solution)) {
            best = solution;
        }
        return best;
    }

    private List<City> swap(List<City> list) {
        return List.of(list.get(0), list.get(2), list.get(1), list.get(3));
    }

    private void updatePheromone() {
        Set<Ant> routes = new HashSet<>();
        Set<List<City>> paths = new HashSet<>();

        for (Ant ant : ants) {
            if (!paths.contains(ant.getPath())) {
                paths.add(ant.getPath());
                routes.add(ant);
            }
        }

        for (Ant ant : routes) {
            List<City> path = ant.getPath();
            float pheromone = calcPheromone(ant);
            for (int i = 0; i < path.size() - 1; i++) {
                ways[ant.getPath().get(i).getId()][ant.getPath().get(i + 1).getId()]
                        .updatePheromoneWith(pheromone / path.size());
            }
        }

    }

    private float calcPheromone(Ant ant) {
        return (float) (AlgConfig.DISTANCE_COEFFICIENT / ant.getPathLength());
    }

    private void chooseWayForEveryAnt() {
        for (Ant ant : ants) {
            Road road = chooseRoad(ant);
            ant.addToPath(road.getTo());
        }
    }

    private Road chooseRoad(Ant ant) {
        List<City> availableCities = new ArrayList<>(cities);
        availableCities.removeAll(ant.getPath());
        List<Road> roads = Arrays.stream(ways[cities.indexOf(ant.getCurrentCity())])
                .filter(Objects::nonNull)
                .filter(r -> availableCities.contains(r.getTo()))
                .toList();

        return chooseRoad(roads);
    }

    private Road chooseRoad(List<Road> roads) {
        if (roads.isEmpty()) throw new IllegalArgumentException("Тупик!!");
        if (roads.size() == 1) return roads.get(0);

        double rnd = Rnd.get();
        double sum = 0;
        double[] probabilities = new double[roads.size()];
        for (int i = 0; i < roads.size(); i++) {
            Road road = roads.get(i);
            probabilities[i] = calcWayChooseProbability(road);
            sum += probabilities[i];
        }

        double chooser = 0;
        for (int i = 0; i < roads.size(); i++) {
            chooser += probabilities[i] / sum;
            if (chooser > rnd) return roads.get(i);
        }
        return roads.get(roads.size() - 1);
    }

    private double calcWayChooseProbability(Road road) {
        return Math.pow(road.getPheromone(), AlgConfig.ALFA)
                * Math.pow(AlgConfig.DISTANCE_COEFFICIENT / road.distance(), AlgConfig.BETA);
    }

    private void createAnts() {
        for (int i = 0; i < AlgConfig.NUMBER_OF_ANTS; i++) {
            ants.add(new Ant(cities.get(i % AlgConfig.NUMBER_OF_CITIES)));
        }
    }

    public Road[][] getWays() {
        return ways;
    }

    private void pheromoneToConsole() {
        System.out.println(
                Arrays.stream(ways)
                        .flatMap(Arrays::stream)
                        .filter(Objects::nonNull)
                        .map(Road::getPheromone)
                        .filter(f -> f - AlgConfig.INITIAL_PHEROMONE > 0.001)
                        .map(String::valueOf)
                        .collect(Collectors.joining(","))
        );
    }


}
