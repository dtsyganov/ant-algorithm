package ru.tinkoff.dts.conference.ant.app;

import ru.tinkoff.dts.conference.ant.app.model.City;
import ru.tinkoff.dts.conference.ant.app.model.WorldHelper;

import java.util.List;

public class App {
    public static void main(String[] args) {
        List<City> cities = WorldHelper.createRandomCities(66);

        System.out.println("List<City> result = new ArrayList<>();");
        for (City city : cities) {
            System.out.printf("" +
                    "result.add(new City(%d, %4.0f, %4.0f));\n", city.id(), city.x(), city.y());
        }
    }
}
