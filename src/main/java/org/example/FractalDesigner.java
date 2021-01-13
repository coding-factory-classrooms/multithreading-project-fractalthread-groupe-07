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
            int wantedChunks = 100;
            int linesByChunk = fractal.getHorizontalSide()/wantedChunks;
            createFractions(linesByChunk);
            threadPool.shutdown();
            mergeFractions();
        }
        else {
            System.out.println("draw julia");
            fractal.draw(0,0,0, fractal.getImage()); //make it work ok mais crado
        }
    }

    private void createFractions(int linesByChunk) {
        for (int i = 0; i < fractal.getVerticalSide()/linesByChunk; i++) {
            futures.add(threadPool.submit(new ImageFractionTask(i, linesByChunk, fractal)));
        }
    }

    private void mergeFractions() {
        Graphics g = fractal.getImage().getGraphics();
        try {
            for (int i = 0; i < futures.size(); i++) {
                g.drawImage(futures.get(i).get().fraction, 0, futures.get(i).get().fraction.getHeight() * i, null);
            }
        }  catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        g.dispose();
        fractal.saveFileAsJpg(fractal.getImage());
    }
}
