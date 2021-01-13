package org.example;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class ImageFractionTask implements Callable<ImageFraction> {
    private BufferedImage fraction;
    private int id;
    private int linesByChunk;
    private Fractal fractal;

    public ImageFractionTask(int id, int linesByChunk, Fractal fractal) {
        this.id = id;
        this.linesByChunk = linesByChunk;
        fraction = new BufferedImage(fractal.getHorizontalSide(),linesByChunk, BufferedImage.TYPE_INT_RGB);
        this.fractal = fractal;
    }

    @Override
    public ImageFraction call() throws Exception {
        for (int y = 0; y < linesByChunk; y++) {
            for (int x = 0; x < fractal.getHorizontalSide(); x++) {
                fractal.draw(x, y, fraction);
            }
        }
        return new ImageFraction(id, fraction);
    }
}
