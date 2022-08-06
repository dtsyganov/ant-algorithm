package ru.tinkoff.dts.conference.ant.app.console;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.model.City;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CityDrawer implements Drawer<City> {

    public void draw(Graphics g, City city) {
        Graphics2D g2d = (Graphics2D) g;
        Ellipse2D.Double circle1 = new Ellipse2D.Double(
                city.x(), city.y(),
                Config.CITY_SIZE, Config.CITY_SIZE
        );
        Ellipse2D.Double circle2 = new Ellipse2D.Double(
                city.x() + (Config.CITY_BORDER_SIZE / 4),
                city.y() + (Config.CITY_BORDER_SIZE / 4),
                Config.CITY_SIZE - (Config.CITY_BORDER_SIZE / 2),
                Config.CITY_SIZE - (Config.CITY_BORDER_SIZE / 2));
        Ellipse2D.Double circle3 = new Ellipse2D.Double(
                city.x() + (Config.CITY_BORDER_SIZE / 2),
                city.y() + (Config.CITY_BORDER_SIZE / 2),
                Config.CITY_SIZE - Config.CITY_BORDER_SIZE,
                Config.CITY_SIZE - Config.CITY_BORDER_SIZE);

        g2d.setColor(Config.CITY_BORDER_COLOR);
        g2d.fill(circle1);
        g2d.setColor(Config.BACKGROUND_COLOR);
        g2d.fill(circle2);
        g2d.setColor(Config.CITY_COLOR);
        g2d.fill(circle3);
    }
}
