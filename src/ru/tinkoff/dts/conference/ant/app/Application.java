package ru.tinkoff.dts.conference.ant.app;

import ru.tinkoff.dts.conference.ant.app.algorithm.AlgConfig;
import ru.tinkoff.dts.conference.ant.app.algorithm.AntAlgorithm;
import ru.tinkoff.dts.conference.ant.app.console.Canvas;
import ru.tinkoff.dts.conference.ant.app.model.City;
import ru.tinkoff.dts.conference.ant.app.model.Road;
import ru.tinkoff.dts.conference.ant.app.model.Solution;
import ru.tinkoff.dts.conference.ant.app.model.World;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

import static ru.tinkoff.dts.conference.ant.app.model.WorldHelper.init;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        List<City> cities = init(AlgConfig.NUMBER_OF_CITIES);
        LinkedBlockingQueue<Solution> queue = new LinkedBlockingQueue<>();

        AntAlgorithm algorithm = new AntAlgorithm(cities, queue);
        List<Road> roads = Arrays.stream(algorithm.getWays()).flatMap(Arrays::stream).filter(Objects::nonNull).toList();

        World world = new World(cities, roads);
        Canvas canvas = new Canvas(world);
        canvas.repaint();

        canvas.setVisible(true);

        Callable<Solution> algorithmTask = algorithm::findSolution;
        ExecutorService pool = Executors.newFixedThreadPool(2);

//        System.out.println(algorithm.findSolution());

        Future<Solution> solutionFuture = pool.submit(algorithmTask);

        int iter = 0;
        while (!solutionFuture.isDone()) {
            world.setSolution(queue.take());

            if (iter++ % Config.DRAW_EVERY_ITERATION == 0) {
                canvas.repaint();
            }
        }

    }
}
