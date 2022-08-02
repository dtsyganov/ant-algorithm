package ru.tinkoff.dts.conference.ant.app;

import java.util.Random;

public class Rnd {
    private Rnd() {
    }

    private static final Random RANDOM = new Random();

    public static int getX() {
        return Config.BORDER_SIZE + RANDOM.nextInt(Config.MAX_X - Config.BORDER_SIZE * 2);
    }

    public static int getY() {
        return Config.BORDER_SIZE + RANDOM.nextInt(Config.MAX_Y - Config.BORDER_SIZE * 2);
    }

    public static double get() {
        return RANDOM.nextDouble();
    }

    public static int variation(int mod) {
        return RANDOM.nextInt(mod * 2) - mod;
    }

    public static int getInDiameter(int diameter) {
        return RANDOM.nextInt(diameter) - diameter / 2;
    }

    public static int getOnCircle(int diameter, int x) {
        int y = (int) Math.sqrt(diameter * diameter / 4.0 - x * x);
        boolean sign = Rnd.get() > 0.5;
        return (sign ? 1 : -1) * y;
    }
}
