package ru.tinkoff.dts.conference.ant.app.model;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.Rnd;
import ru.tinkoff.dts.conference.ant.app.algorithm.AlgConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

public class WorldHelper {
    private WorldHelper() {
    }

    public static List<City> init(int amount) {
        return switch (AlgConfig.CITIES_PATTERN) {
            case RECTANGLE -> createCitiesRectangle(amount);
            case RECTANGLE_PLUS -> createCitiesRectanglePlus(amount);
            case CIRCLE -> createCircleCities(amount);
            case FIXED -> FixedCitiesList.get();
            default -> createRandomCities(amount);
        };
    }

    private static List<City> createCitiesRectanglePlus(int amount) {
        List<City> result = createCitiesRectangle(amount - 5);
        result.addAll(createRandomCities(5, amount - 5));
        return result;
    }

    public static List<City> createRandomCities(int amount) {
        return createRandomCities(amount, 0);
    }

    public static List<City> createRandomCities(int amount, int start) {
        List<City> result = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            City city = createCity(start + i, idx -> new City(idx, Rnd.getX(), Rnd.getY()), result);
            result.add(city);
        }
        return result;
    }

    public static List<City> createCircleCities(int amount) {
        final int VARIATION = 20;
        List<City> result = new ArrayList<>();
        int diameter = Config.MAX_Y - Config.BORDER_SIZE * 2;

        for (int i = 0; i < amount; i++) {
            result.add(createCity(i, n -> {
                int x = Rnd.getInDiameter(diameter);
                int y = Rnd.getOnCircle(diameter, x);
                return new City(n, x + Config.MAX_X / 2d + Rnd.variation(VARIATION), y + Config.MAX_Y / 2d + Rnd.variation(VARIATION));
            }, result));
        }
        return result;
    }

    public static List<City> createCitiesRectangle(int amount) {
        final double BORDER = 50;
        final int VARIATION = 20;
        List<City> result = new ArrayList<>();
        int id = 0;
        for (int i = 0; i < amount / 4; i++) {
            result.add(createCity(id++, num -> new City(num, BORDER + Rnd.variation(VARIATION), Rnd.getY()), result));
        }
        for (int i = 0; i < amount / 4; i++) {
            result.add(createCity(id++, num -> new City(num, Config.MAX_X - BORDER + Rnd.variation(VARIATION), Rnd.getY()), result));
        }
        for (int i = 0; i < amount / 4; i++) {
            result.add(createCity(id++, num -> new City(num, Rnd.getX(), BORDER + Rnd.variation(VARIATION)), result));
        }
        for (int i = 0; i < amount - amount / 4 * 3; i++) {
            result.add(createCity(id++, num -> new City(num, Rnd.getX(), Config.MAX_Y - BORDER + Rnd.variation(VARIATION)), result));
        }
        return result;
    }

    private static City createCity(Integer id, IntFunction<City> creator, List<City> result) {
        City city = creator.apply(id);
        while (isClose(city, result))
            city = creator.apply(id);
        return city;
    }
    public static boolean isClose(City city, List<City> result) {
        for (City nextCity : result) {
            if (city.distanceTo(nextCity) < Config.CITY_CLOSEST_DISTANCE_THRESHOLD) return true;
        }
        return false;
    }

}
