package ru.tinkoff.dts.conference.ant.app;

import ru.tinkoff.dts.conference.ant.app.algorithm.AlgConfig;
import ru.tinkoff.dts.conference.ant.app.algorithm.AntAlgorithm;
import ru.tinkoff.dts.conference.ant.app.console.Canvas;
import ru.tinkoff.dts.conference.ant.app.model.City;
import ru.tinkoff.dts.conference.ant.app.model.Solution;
import ru.tinkoff.dts.conference.ant.app.model.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import static ru.tinkoff.dts.conference.ant.app.model.WorldHelper.init;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        List<City> cities = init(AlgConfig.NUMBER_OF_CITIES);
        ExecutorService pool = Executors.newFixedThreadPool(2);

        boolean exit = false;
        while (!exit) {
            int iter = 0;
            LinkedBlockingQueue<Solution> queue = new LinkedBlockingQueue<>();

            World world = new World(cities);
            Canvas canvas = new Canvas(world);
            canvas.repaint();
            canvas.setVisible(true);
            Future<Solution> solutionFuture = pool.submit(new AntAlgorithm(cities, queue)::findSolution);

            while (!solutionFuture.isDone()) {
                if (!queue.isEmpty()) {
                    LinkedList<Solution> solutions = new LinkedList<>();
                    queue.drainTo(solutions);
                    world.setSolution(solutions.getLast());
                }
                Thread.sleep(100);
                if (iter++ % Config.DRAW_EVERY_ITERATION == 0) {
                    canvas.repaint();
                }
            }
            if ("q".equals(scanner.nextLine())) {
                exit = true;
            }
        }
        System.exit(0);
    }
}
