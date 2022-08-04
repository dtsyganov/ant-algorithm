package ru.tinkoff.dts.conference.ant.app.console;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.model.City;
import ru.tinkoff.dts.conference.ant.app.model.Road;
import ru.tinkoff.dts.conference.ant.app.model.Solution;

import java.awt.*;
import java.util.List;

public class SolutionDrawer implements Drawer<Solution> {
    private final boolean best;
    private final Color solutionColor;

    public SolutionDrawer(boolean best) {
        this.best = best;
        this.solutionColor = best ? Config.BEST_SOLUTION_COLOR : Config.SOLUTION_COLOR;
    }

    public void draw(Graphics g, Solution solution) {
        if (solution.getList() == null || solution.getList().isEmpty()) return;
        List<City> cities = solution.getList();
        for (int i = 0; i < cities.size() - 1; i++) {
            drawRoad(g, new Road(cities.get(i), cities.get(i + 1)));
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2d.drawString(String.format("%7.2f", solution.getPathLength()), 10, 20 + delta());
    }

    private int delta() {
        return best ? 20 : 0;
    }

    private void drawRoad(Graphics g, Road road) {
        DrawHelper.drawRoadWithColor(g, road, solutionColor, best ? 1f : 3f, best);
    }

}
