package ru.tinkoff.dts.conference.ant.app.algorithm;

public class AlgConfig {
    public static final int NUMBER_OF_CITIES = 66;
    public static final CitiesPattern CITIES_PATTERN = CitiesPattern.RANDOM;
    public static final double NUMBER_OF_ITERATIONS = 200;
    public static final int NUMBER_OF_ANTS = NUMBER_OF_CITIES * 5;
    public static final float INITIAL_PHEROMONE = 0.1f;
    public static final float PHEROMONE_EVAPORATED = 0.125f;
    public static final float PHEROMONE_MAX = 500.f;
    public static final double ALFA = 1;
    public static final double BETA = 3;
    public static final double DISTANCE_COEFFICIENT = 100;
    public static final boolean APPLY_SWAP_MIDDLE_PAIR_FOR_EACH_FOUR = true;



    public enum CitiesPattern { RECTANGLE, CIRCLE, RANDOM }

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

//        public static final int NUMBER_OF_CITIES = 66;
//        public static final String CITIES_PATTERN = "RECTANGLE";
//        public static final double NUMBER_OF_ITERATIONS = 1000;
//        public static final int NUMBER_OF_ANTS = NUMBER_OF_CITIES * 100;
//        public static final float INITIAL_PHEROMONE = 5f;
//        public static final float PHEROMONE_EVAPORATED = 0.2f;
//        public static final float PHEROMONE_MAX = 500.f;
//        public static final double ALFA = 2;
//        public static final double BETA = 3;
//        public static final double DISTANCE_COEFFICIENT = 22000;
//        public static final boolean APPLY_BRUTEFORCE_EACH_FOUR = false;
}
