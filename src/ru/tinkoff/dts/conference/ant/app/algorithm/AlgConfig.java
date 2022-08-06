package ru.tinkoff.dts.conference.ant.app.algorithm;

public class AlgConfig {
    public static final int NUMBER_OF_CITIES = 66;
    public static final CitiesPattern CITIES_PATTERN = CitiesPattern.RANDOM;
    public static final double NUMBER_OF_ITERATIONS = 200;
    public static final int NUMBER_OF_ANTS = NUMBER_OF_CITIES * 10;
    public static final float INITIAL_PHEROMONE = 0.1f;
    public static final float PHEROMONE_EVAPORATED = 0.125f;
    public static final float PHEROMONE_MAX = 500.f;
    public static final double ALFA = 2;
    public static final double BETA = 3;
    public static final double DISTANCE_COEFFICIENT = 100;
    public static final boolean APPLY_SWAP_MIDDLE_PAIR_FOR_EACH_FOUR = true;


    public enum CitiesPattern { RECTANGLE, CIRCLE, RANDOM }
}
