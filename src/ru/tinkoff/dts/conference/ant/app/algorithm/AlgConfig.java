package ru.tinkoff.dts.conference.ant.app.algorithm;

public class AlgConfig {
    public static final int NUMBER_OF_CITIES = 66;
    public static final double NUMBER_OF_ITERATIONS = 500;
    public static final int NUMBER_OF_ANTS = NUMBER_OF_CITIES * 10;
    public static final float PHEROMONE_EVAPORATED = 0.425f;
    public static final double ALFA = 1;
    public static final double BETA = 3;
    public static final double DISTANCE_COEFFICIENT = 1000;

    public static final float INITIAL_PHEROMONE = 1.1f;
    public static final float PHEROMONE_MIN = 0.0000001f;
    public static final float PHEROMONE_MAX = 5.f;





    public static final CitiesPattern CITIES_PATTERN = CitiesPattern.FIXED;
    public static final boolean APPLY_SWAP_MIDDLE_PAIR_FOR_EACH_FOUR = false;

    public enum CitiesPattern { RECTANGLE, RECTANGLE_PLUS, CIRCLE, RANDOM, FIXED }
}
