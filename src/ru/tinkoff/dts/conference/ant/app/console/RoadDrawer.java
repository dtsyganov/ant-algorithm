package ru.tinkoff.dts.conference.ant.app.console;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.algorithm.AlgConfig;
import ru.tinkoff.dts.conference.ant.app.model.Road;

import java.awt.*;

public class RoadDrawer implements Drawer<Road> {

    public void draw(Graphics g, Road road) {
        if (road.getPheromone() < AlgConfig.INITIAL_PHEROMONE / 20)
            return;
        float width = normalize(road.getPheromone());
        DrawHelper.drawRoadWithColor(g, road,
                Config.roadColorForWidth(width),
                width
        );
    }

    private static float normalize(float pheromone) {
        return Math.min(Config.ROAD_MAX_SIZE, Config.ROAD_MAX_SIZE * pheromone / 10);
    }
}
