package ru.tinkoff.dts.conference.ant.app.model;

import java.util.ArrayList;
import java.util.List;

public class BestSolution extends Solution {
    public BestSolution(List<City> list) {
        super(list, new ArrayList<>());
    }
}
