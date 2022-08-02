package ru.tinkoff.dts.conference.ant.app.model;

public class City implements Element {
    private int id;
    private int x;
    private int y;

    public City(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    private static int sqr(int n) {
        return n * n;
    }

    public double distanceTo(City city) {
        return Math.sqrt((double) sqr(city.x - x) + sqr(city.y - y));
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
