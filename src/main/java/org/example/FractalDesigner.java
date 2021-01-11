package org.example;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FractalDesigner {
    private Fractal fractal;
    public FractalDesigner(Fractal fractal) {
        this.fractal = fractal;
    }

    public void designFractal () {
        int coreNumber = Runtime.getRuntime().availableProcessors();
        ExecutorService threadPool = Executors.newFixedThreadPool(coreNumber+4);

        FractalTask.setFractal(fractal);

        for (int y = 0; y < fractal.getSide(); y++) {
            for (int x = 0; x < fractal.getSide(); x++) {
                FractalTask task = new FractalTask(x, y);
                threadPool.execute(task);
            }
        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
