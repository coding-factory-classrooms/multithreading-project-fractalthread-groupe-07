package org.example;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FractalDesigner {
    private Fractal fractal;
    private ExecutorService threadPool;
    public FractalDesigner(Fractal fractal, ExecutorService threadPool) {
        this.fractal = fractal;
        this.threadPool = threadPool;
    }

    public void designFractal () {
        // à mettre en amont
        int coreNumber = Runtime.getRuntime().availableProcessors();
        threadPool = //new OurThreadPoolExecutor(coreNumber);
        Executors.newFixedThreadPool(coreNumber);
        // end amont

        int linesByChunk = 100;

        FractalTask.setFractal(fractal);
        FractalTask.setLinesByChunk(linesByChunk);

        for (int i = 0; i < fractal.getSide()/linesByChunk; i++) {
            try{
                Thread.sleep(20); // waiting 15ms to prevent having bugged stripes when proco is burning
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
