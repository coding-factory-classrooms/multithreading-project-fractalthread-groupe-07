package org.example;


import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.*;

public class FractalDesigner {
    private ArrayList<Future<ImageFraction>> futures;
    private Fractal fractal;
    private ExecutorService threadPool;
    public FractalDesigner(Fractal fractal, ExecutorService threadPool) {
        this.fractal = fractal;
        this.threadPool = threadPool;
        this.futures = new ArrayList<Future<ImageFraction>>();
    }

    public void designFractal () {
        if (fractal.getName().equals("mandel")) {

            int linesByChunk = 100;

            FractalTask.setFractal(fractal);
            FractalTask.setLinesByChunk(linesByChunk);

            for (int i = 0; i < fractal.getVerticalSide()/linesByChunk; i++) {
                futures.add(threadPool.submit(new ImageFractionTask(i, linesByChunk, fractal)));
                /* try{
                    Thread.sleep(20); // waiting 15ms to prevent having bugged stripes when proco is burning
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                FractalTask task = new FractalTask(i);
                threadPool.execute(task);*/
            }

            mergeFractions();

            threadPool.shutdown();
            try {
                threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("draw julia");
            fractal.draw(0,0, fractal.getImage()); //make it work ok mais crado
        }
    }

    private void mergeFractions() {
        Graphics g = fractal.getImage().getGraphics();
        try {
            for(Future<ImageFraction> f : futures) {
                g.drawImage(f.get().fraction, f.get().fraction.getWidth(), f.get().fraction.getHeight() * f.get().id, null);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        g.dispose();
        fractal.saveFileAsJpg(fractal.getImage());
    }
}
