package ru.tinkoff.dts.conference.ant.app.model;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.Rnd;
import ru.tinkoff.dts.conference.ant.app.algorithm.AlgConfig;

import java.util.ArrayList;
import java.util.List;

public class WorldHelper {
    private WorldHelper() {
    }

    public static List<City> init(int amount) {
        return switch (AlgConfig.CITIES_PATTERN) {
            case RECTANGLE -> createCitiesRectangle(amount);
            case CIRCLE -> createCircleCities(amount);
            default -> createRandomCities(amount);
        };
    }

    public static List<City> createRandomCities(int amount) {
        List<City> result = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            result.add(new City(i, Rnd.getX(), Rnd.getY()));
        }
        return result;
    }

    public static List<City> createCircleCities(int amount) {
        final int VARIATION = 20;
        List<City> result = new ArrayList<>();
        int diameter = Config.MAX_Y - Config.BORDER_SIZE * 2;

        for (int i = 0; i < amount; i++) {
            int x = Rnd.getInDiameter(diameter);
            int y = Rnd.getOnCircle(diameter, x);
            result.add(new City(i, x + Config.MAX_X / 2 + Rnd.variation(VARIATION),
                    y + Config.MAX_Y / 2 + Rnd.variation(VARIATION)));
        }
        return result;
    }

    public static List<City> createCitiesRectangle(int amount) {
        final int BORDER = 50;
        final int VARIATION = 20;
        List<City> result = new ArrayList<>();
        int id = 0;
        for (int i = 0; i < amount / 4; i++) {
            result.add(new City(id++, BORDER + Rnd.variation(VARIATION), Rnd.getY()));
        }
        for (int i = 0; i < amount / 4; i++) {
            result.add(new City(id++, Config.MAX_X - BORDER + Rnd.variation(VARIATION), Rnd.getY()));
        }
        for (int i = 0; i < amount / 4; i++) {
            result.add(new City(id++, Rnd.getX(), BORDER + Rnd.variation(VARIATION)));
        }
        for (int i = 0; i < amount - amount / 4 * 3; i++) {
            result.add(new City(id++, Rnd.getX(), Config.MAX_Y - BORDER + Rnd.variation(VARIATION)));
        }
        return result;
    }
}