package ru.tinkoff.dts.conference.ant.app.console;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.algorithm.AlgConfig;
import ru.tinkoff.dts.conference.ant.app.model.Road;

import java.awt.*;

public class RoadDrawer implements Drawer<Road> {

    public void draw(Graphics g, Road road) {
        DrawHelper.drawRoadWithColor(g, road,
                Config.roadColorForWidth(road.getPheromone()),
                normalize(road.getPheromone())
        );
    }

    private static float normalize(float pheromone) {
        return pheromone / AlgConfig.PHEROMONE_MAX * Config.ROAD_MAX_SIZE;
    }
}
