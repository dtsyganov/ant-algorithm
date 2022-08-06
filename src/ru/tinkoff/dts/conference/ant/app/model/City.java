package ru.tinkoff.dts.conference.ant.app.model;

public record City(int id, double x, double y) implements Element {

    private static double sqr(double n) {
        return n * n;
    }

    public double distanceTo(City city) {
        return Math.sqrt(sqr(city.x - x) + sqr(city.y - y));
    }
}
