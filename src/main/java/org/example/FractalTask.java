package org.example;

import java.awt.image.BufferedImage;

public class FractalTask implements Runnable {
    private static Fractal fractal;
    private int x;
    private int y;

    public FractalTask(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void setFractal(Fractal theFractal) {
        fractal = theFractal;
    }

    @Override
    public void run() {
        fractal.draw(x,y);
    }
}
