package ru.tinkoff.dts.conference.ant.app.console;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.model.Road;

import java.awt.*;
import java.awt.geom.Line2D;

public class DrawHelper {
    private DrawHelper() {
    }

    static void drawRoadWithColor(Graphics g, Road road, Color color, float width) {
        drawRoadWithColor(g, road, color, width, false);
    }

    static void drawRoadWithColor(Graphics g, Road road, Color color, float width, boolean dashed) {
        Graphics2D g2d = (Graphics2D) g;
        double x1 = road.getFrom().getX() + Config.CITY_SIZE / 2;
        double y1 = road.getFrom().getY() + Config.CITY_SIZE / 2;
        double x2 = road.getTo().getX() + Config.CITY_SIZE / 2;
        double y2 = road.getTo().getY() + Config.CITY_SIZE / 2;
        Line2D.Double line = new Line2D.Double(x1, y1, x2, y2);

        g2d.setColor(color);
        if (dashed)
            g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1, new float[]{30, 30}, 0));
        else
            g2d.setStroke(new BasicStroke(width, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(line);
        if (Config.SHOW_DISTANCE) {
            g2d.drawString(String.format("%5.2f", road.distance()), average(x1, x2), average(y1, y2) + 20);
        }
    }

    private static float average(double n1, double n2) {
        return (float) ((n1 + n2) / 2.0);
    }

}
