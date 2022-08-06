package ru.tinkoff.dts.conference.ant.app;

import java.awt.*;
import java.util.EnumSet;
import java.util.function.Function;

public class Config {
    //    CANVAS
    public static final int MAX_X = 1024;
    public static final int MAX_Y = 768;
    public static final int BORDER_SIZE = 50;
    public static final Color BACKGROUND_COLOR = new Color(0xf0f0f0);
    public static final int DRAW_EVERY_ITERATION = 1;

    //    CITY
    public static final double CITY_CLOSEST_DISTANCE_THRESHOLD = 50;
    public static final Color CITY_COLOR = new Color(0x00008B);
    public static final Color CITY_BORDER_COLOR = new Color(0x222222);
    public static final double CITY_BORDER_SIZE = 8;
    public static final float CITY_SIZE = 30;

    //    ROAD
    public static final float ROAD_MAX_SIZE = CITY_SIZE * 1.0001f;
    public static final boolean SHOW_DISTANCE = false;
    public static final boolean PHEROMONE_TO_CONSOLE = false;

    public static final Color SOLUTION_COLOR = new Color(0x000000);
    public static final Color BEST_SOLUTION_COLOR = Color.YELLOW;
    public static final float ROAD_SHOW_THRESHOLD = 0.02f;


    public static Color roadColorForWidth(double width) {
        return ColorParts.getColor(width);
    }

    private enum ColorParts {
        RED(Color::getRed), GREEN(Color::getGreen), BLUE(Color::getBlue);
        private static final EnumSet<ColorParts> ROAD_COLOR_PARTS = EnumSet.of(RED, GREEN, BLUE);
        private final Function<Color, Integer> part;
        private static final Color ROAD_BASE_COLOR_FROM = new Color(0xDFDFDF);
        private static final Color ROAD_BASE_COLOR_TO = new Color(0xDFDFDF);
        ColorParts(Function<Color, Integer> part) {
            this.part = part;
        }

        public static Color getColor(double width) {
            return new Color(
                    RED.finalColorPart(width),
                    GREEN.finalColorPart(width),
                    BLUE.finalColorPart(width)
            );
        }

        private static int range(int from, int to, double width) {
            return from + (to - from) * (int)Math.min(width / ROAD_MAX_SIZE, ROAD_MAX_SIZE);
        }

        int finalColorPart(double width) {
            return ROAD_COLOR_PARTS.contains(this)
                    ? range(part.apply(ROAD_BASE_COLOR_FROM), part.apply(ROAD_BASE_COLOR_TO), width)
                    : part.apply(ROAD_BASE_COLOR_FROM);
        }
    }
}
