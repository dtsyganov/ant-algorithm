package ru.tinkoff.dts.conference.ant.app.algorithm;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.model.Ant;
import ru.tinkoff.dts.conference.ant.app.model.City;
import ru.tinkoff.dts.conference.ant.app.model.Road;
import ru.tinkoff.dts.conference.ant.app.model.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import static ru.tinkoff.dts.conference.ant.app.algorithm.ProbabilityHelper.calcProbabilities;
import static ru.tinkoff.dts.conference.ant.app.algorithm.ProbabilityHelper.chooseWithProbability;

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
            updatePheromoneMap();
            chooseBestSolution(iter);

            clearSolutions();
        }
        return bestSolution;
    }

    private Solution chooseBestSolution(int iter) {
        Ant bestAnt = ants.stream()
                .min(Comparator.comparingDouble(Ant::getPathLength))
                .orElseThrow();
        Solution solution = new Solution(bestAnt.getPath(), roadMap.getWays());

        if (AlgConfig.APPLY_SWAP_MIDDLE_PAIR_FOR_EACH_FOUR) {
            solution = applySwapMiddlePairForEachFour(solution);
        }

        bestSolution = solution.min(bestSolution);

        notificationQueue.add(bestSolution);

        Logger.iteration(iter, solution);

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

    private void updatePheromoneMap() {
        roadMap.evaporate();
        ants.parallelStream()
                .forEach(roadMap::updateWithPheromone);
    }

    private void chooseStepForEveryAnt() {
        ants.parallelStream()
                .forEach(ant -> ant.addToPath(chooseNextCity(ant)));
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
        int index = chooseWithProbability(probabilities);
        return roads.get(index);
    }

    private void createAnts() {
        for (int i = 0; i < AlgConfig.NUMBER_OF_ANTS; i++) {
            ants.add(new Ant(cities.get(i % AlgConfig.NUMBER_OF_CITIES)));
        }
    }

    private void clearSolutions() {
        for (Ant ant : ants) {
            ant.clearPath();
        }
        if (Config.PHEROMONE_TO_CONSOLE) {
            Logger.pheromoneToConsole(roadMap.getWays());
        }
    }


}
