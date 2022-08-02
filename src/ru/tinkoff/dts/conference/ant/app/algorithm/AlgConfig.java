package ru.tinkoff.dts.conference.ant.app.algorithm;

import ru.tinkoff.dts.conference.ant.app.Config;

public class AlgConfig {
    public static final int NUMBER_OF_CITIES = 100;
    public static final CitiesPattern CITIES_PATTERN = CitiesPattern.RANDOM;
    public static final double NUMBER_OF_ITERATIONS = 1000;
    public static final int NUMBER_OF_ANTS = NUMBER_OF_CITIES * 50;
    public static final float INITIAL_PHEROMONE = 0.4f;
    public static final float PHEROMONE_EVAPORATED = 0.25f;
    public static final float PHEROMONE_MAX = 500.f;
    public static final double ALFA = 1.2;
    public static final double BETA = 5;
    public static final double DISTANCE_COEFFICIENT = 10_000_000;
    public static final boolean APPLY_BRUTEFORCE_EACH_FOUR = true;


//    public static final int NUMBER_OF_CITIES = 100;
//    public static final String CITIES_PATTERN = "RANDOM";
//    public static final double NUMBER_OF_ITERATIONS = 1000;
//    public static final int NUMBER_OF_ANTS = NUMBER_OF_CITIES * 5;
//    public static final float INITIAL_PHEROMONE = 0.3f;
//    public static final float PHEROMONE_EVAPORATED = 0.2f;
//    public static final float PHEROMONE_MAX = 500.f;
//    public static final double ALFA = 1.5;
//    public static final double BETA = 3;
//    public static final double DISTANCE_COEFFICIENT = 5_000 * Math.pow(NUMBER_OF_CITIES, 1);
//    public static final boolean APPLY_BRUTEFORCE_EACH_FOUR = false;


//GOOD EXAMPLE
//    public static final int NUMBER_OF_CITIES = 66;
//    public static final String CITIES_PATTERN = "RECTANGLE";
//    public static final double NUMBER_OF_ITERATIONS = 1000;
//    public static final int NUMBER_OF_ANTS = NUMBER_OF_CITIES * 100;
//    public static final float INITIAL_PHEROMONE = 5f;
//    public static final float PHEROMONE_EVAPORATED = 0.2f;
//    public static final float PHEROMONE_MAX = 500.f;
//    public static final double ALFA = 2;
//    public static final double BETA = 3;
//    public static final double DISTANCE_COEFFICIENT = 22000;
//    public static final boolean APPLY_BRUTEFORCE_EACH_FOUR = false;

//ANOTHER EXAMPLE
//    public static final int NUMBER_OF_CITIES = 100;
//    public static final String CITIES_PATTERN = "CIRCLE";
//    public static final double NUMBER_OF_ITERATIONS = 1000;
//    public static final int NUMBER_OF_ANTS = NUMBER_OF_CITIES * 5;
//    public static final float INITIAL_PHEROMONE = 0.3f;
//    public static final float PHEROMONE_EVAPORATED = 0.35f;
//    public static final float PHEROMONE_MAX = 50.f;
//    public static final double ALFA = 1;
//    public static final double BETA = 4;
//    public static final double DISTANCE_COEFFICIENT = Config.MAX_Y * Math.PI * 50;
//    public static final boolean APPLY_BRUTEFORCE_EACH_FOUR = false;
    public enum CitiesPattern { RECTANGLE, CIRCLE, RANDOM }
}
