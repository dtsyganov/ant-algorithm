package ru.tinkoff.dts.conference.ant.app.console;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.model.City;
import ru.tinkoff.dts.conference.ant.app.model.Road;
import ru.tinkoff.dts.conference.ant.app.model.Solution;

import java.awt.*;
import java.util.List;

public class SolutionDrawer implements Drawer<Solution> {

    public void draw(Graphics g, Solution solution) {
        if (solution.getList() == null || solution.getList().isEmpty()) return;
        List<City> cities = solution.getList();
        for (int i = 0; i < cities.size() - 1; i++) {
            drawRoad(g, new Road(cities.get(i), cities.get(i + 1)));
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString(String.format("%7.2f", solution.getPathLength()), 10, 10);
    }

    private void drawRoad(Graphics g, Road road) {
        DrawHelper.drawRoadWithColor(g, road, Config.SOLUTION_COLOR, 3f);
    }

}
