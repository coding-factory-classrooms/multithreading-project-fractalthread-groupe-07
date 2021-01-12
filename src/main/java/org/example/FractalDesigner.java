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

        if (fractal.getName().equals("mandel")) {
            int coreNumber = Runtime.getRuntime().availableProcessors();
            ExecutorService threadPool = Executors.newFixedThreadPool(coreNumber);

            int linesByChunk = 100;

            FractalTask.setFractal(fractal);
            FractalTask.setLinesByChunk(linesByChunk);

            for (int i = 0; i < fractal.getVerticalSide()/linesByChunk; i++) {
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
        else {
            System.out.println("draw julia");
            fractal.draw(0,0); //make it work ok mais crado
        }
    }
}
