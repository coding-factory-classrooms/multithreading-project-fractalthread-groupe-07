package org.example;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class FractalDesigner {
    private ArrayList<Future<ImageFraction>> futures;
    private Fractal fractal;
    private ExecutorService threadPool;
    private static final String EASTER_EGG_PATH = "src/main/resources/static/img/easter_egg_equipe.png";

    public FractalDesigner(Fractal fractal, ExecutorService threadPool) {
        this.fractal = fractal;
        this.threadPool = threadPool;
        this.futures = new ArrayList<Future<ImageFraction>>();
    }

    public void designFractal () {
        if (fractal.getName().equals("mandel")) {
            int wantedChunks = 10;
            int linesByChunk = fractal.getHorizontalSide()/wantedChunks;
            createFractions(linesByChunk);
            threadPool.shutdown();
            mergeFractions();
            addEasterEggOrNot();
        }
        else {
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

    private void addEasterEggOrNot() {
        if (fractal.getPosX() == 2000 && fractal.getPosY() == -2500) {
            System.out.println("display easter egg");
            drawEasterEgg();
        }
    }

    private void drawEasterEgg() {
        Graphics g = fractal.getImage().getGraphics();
        try {
            BufferedImage original = ImageIO.read(new File(fractal.getImagePath()));
            BufferedImage overlay = ImageIO.read(new File(EASTER_EGG_PATH));
            g.drawImage(original, 0, 0, null);
            g.drawImage(overlay, fractal.getHorizontalSide()/4, fractal.getVerticalSide()/4, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.dispose();
        fractal.saveFileAsJpg(fractal.getImage());
    }
}
