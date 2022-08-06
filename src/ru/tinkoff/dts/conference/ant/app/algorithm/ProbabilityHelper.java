package ru.tinkoff.dts.conference.ant.app.algorithm;

import ru.tinkoff.dts.conference.ant.app.Rnd;
import ru.tinkoff.dts.conference.ant.app.model.Road;

import java.util.Arrays;
import java.util.List;

public class ProbabilityHelper {
    private ProbabilityHelper() {
    }

    public static double[] calcProbabilities(List<Road> roads) {
        double[] probabilities = new double[roads.size()];
        for (int i = 0; i < roads.size(); i++) {
            Road road = roads.get(i);
            probabilities[i] = calcRoadChooseProbability(road);
        }
        return probabilities;
    }

    private static double calcRoadChooseProbability(Road road) {
        return Math.pow(road.getPheromone(), AlgConfig.ALFA)
                * Math.pow(AlgConfig.DISTANCE_COEFFICIENT / road.distance(), AlgConfig.BETA);
    }

    public static int chooseWithProbability(double[] probabilities) {
        double sum = Arrays.stream(probabilities).sum();

        double chooser = 0;
        double rnd = Rnd.get();
        for (int i = 0; i < probabilities.length; i++) {
            chooser += probabilities[i] / sum;
            if (chooser > rnd) return i;
        }
        return probabilities.length - 1;
    }

}
