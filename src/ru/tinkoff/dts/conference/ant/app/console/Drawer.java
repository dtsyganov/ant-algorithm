package ru.tinkoff.dts.conference.ant.app.console;

import ru.tinkoff.dts.conference.ant.app.model.Element;

import java.awt.*;

public interface Drawer<E extends Element> {
    void draw(Graphics g, E e);
}
