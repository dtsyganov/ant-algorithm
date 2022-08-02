package ru.tinkoff.dts.conference.ant.app.console;

import ru.tinkoff.dts.conference.ant.app.Config;
import ru.tinkoff.dts.conference.ant.app.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Canvas extends JFrame {

    private JPanel panel;
    private transient DrawerRepository drawers = new DrawerRepository();

    public Canvas(World world) {
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                for (Road road : world.getRoads()) {
                    drawers.drawer(road).draw(g, road);
                }
                for (City city : world.getCities()) {
                    drawers.drawer(city).draw(g, city);
                }
                Solution solution = world.getSolution();
                if (solution != null)
                    drawers.drawer(solution).draw(g, solution);
            }
        };
        add(panel);
        panel.setBackground(Config.BACKGROUND_COLOR);
        panel.setPreferredSize(new Dimension(Config.MAX_X, Config.MAX_Y));
        panel.validate();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private static class DrawerRepository {
        private final Map<Class<? extends Element>, Drawer<? extends Element>> drawerMap =
                Map.of(
                        City.class, new CityDrawer(),
                        Road.class, new RoadDrawer(),
                        Solution.class, new SolutionDrawer()
                );

        @SuppressWarnings("unchecked")
        private <E extends Element> Drawer<E> drawer(E key) {
            return (Drawer<E>) drawerMap.get(key.getClass());
        }
    }
}
