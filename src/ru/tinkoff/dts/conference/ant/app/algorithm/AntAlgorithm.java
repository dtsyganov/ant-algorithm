package ru.tinkoff.dts.conference.ant.app.algorithm;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.Rnd;
import ru.tinkoff.dts.conference.ant.app.model.Ant;
import ru.tinkoff.dts.conference.ant.app.model.City;
import ru.tinkoff.dts.conference.ant.app.model.Road;
import ru.tinkoff.dts.conference.ant.app.model.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class AntAlgorithm {
    private final List<City> cities;
    private final RoadMap roadMap;
    private final BlockingQueue<Solution> notificationQueue;
    private final List<Ant> ants = new ArrayList<>(AlgConfig.NUMBER_OF_ANTS);
    private Solution bestSolution;

    public AntAlgorithm(List<City> cities, BlockingQueue<Solution> notificationQueue) {
        this.cities = cities;
        this.roadMap = new RoadMap(cities);
        this.notificationQueue = notificationQueue;
    }

    public Solution findSolution() {
        createAnts();
        for (int iter = 0; iter < AlgConfig.NUMBER_OF_ITERATIONS; iter++) {
            for (int i = 0; i < cities.size(); i++) {
                chooseStepForEveryAnt();
            }
            updatePheromone();
            Solution currentSolution = chooseBestSolution();

            Logger.iteration(iter, currentSolution);
            clearSolutions();
        }
        return bestSolution;
    }

    private void clearSolutions() {
        for (Ant ant : ants) {
            ant.clearPath();
        }
        if (Config.PHEROMONE_TO_CONSOLE) {
            Logger.pheromoneToConsole(roadMap.getWays());
        }
    }

    private Solution chooseBestSolution() {
        Ant bestAnt = ants.stream()
                .min(Comparator.comparingDouble(Ant::getPathLength))
                .orElseThrow();
        Solution solution = new Solution(bestAnt.getPath(), roadMap.getWays());

        if (AlgConfig.APPLY_SWAP_MIDDLE_PAIR_FOR_EACH_FOUR) {
            solution = applySwapMiddlePairForEachFour(solution);
        }

        bestSolution = (bestSolution == null || bestSolution.getPathLength() > solution.getPathLength()) ?
                solution : bestSolution;

        notificationQueue.add(bestSolution);
        return solution;
    }

    private Solution applySwapMiddlePairForEachFour(Solution solution) {
        List<City> path = solution.getList().subList(0, solution.getList().size() - 1);

        for (int i = 0; i < path.size() - 4; i++) {
            List<City> optimalFour = optimal(path.subList(i, i + 4));
            mergeToPath(path, i, optimalFour);
        }
        return new Solution(path, roadMap.getWays());
    }

    private void mergeToPath(List<City> path, int pos, List<City> optimalFour) {
        for (int i = 0; i < 4; i++) {
            path.set(pos + i, optimalFour.get(i));
        }
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
        roadMap.evaporate();
        for (Ant ant : ants) {
            float pheromone = calcPheromone(ant);
            roadMap.updateWithPheromone(ant, pheromone);
        }
    }

    private float calcPheromone(Ant ant) {
        return (float) (AlgConfig.DISTANCE_COEFFICIENT / ant.getPathLength());
    }

    private void chooseStepForEveryAnt() {
        for (Ant ant : ants) {
            ant.addToPath(chooseNextCity(ant));
        }
    }

    private City chooseNextCity(Ant ant) {
        List<Road> roads = roadMap.getAvailableRoadsForAnt(ant);

        return roads.isEmpty() ?
                ant.getPath().get(0) :
                chooseRoad(roads).getTo();
    }

    private Road chooseRoad(List<Road> roads) {
        if (roads.size() == 1) return roads.get(0);

        double[] probabilities = calcProbabilities(roads);
        double sum = Arrays.stream(probabilities).sum();

        double chooser = 0;
        double rnd = Rnd.get();
        for (int i = 0; i < roads.size(); i++) {
            chooser += probabilities[i] / sum;
            if (chooser > rnd) return roads.get(i);
        }
        return roads.get(roads.size() - 1);
    }

    private double[] calcProbabilities(List<Road> roads) {
        double[] probabilities = new double[roads.size()];
        for (int i = 0; i < roads.size(); i++) {
            Road road = roads.get(i);
            probabilities[i] = calcWayChooseProbability(road);
        }
        return probabilities;
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

    public List<Road> getWays() {
        return roadMap.getWays();
    }


}
