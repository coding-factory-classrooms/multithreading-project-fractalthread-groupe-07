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
        ExecutorService threadPool = Executors.newFixedThreadPool(coreNumber);

        int linesByChunk = 100;

        FractalTask.setFractal(fractal);
        FractalTask.setLinesByChunk(linesByChunk);

        for (int i = 0; i < fractal.getSide()/linesByChunk; i++) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            FractalTask task = new FractalTask(i);
            threadPool.execute(task);
        }

        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
