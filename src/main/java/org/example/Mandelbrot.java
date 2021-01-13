package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Mandelbrot extends Fractal {
    private final int MAX_ITER = 5000;
    private double zx, zy, cX, cY, tmp;
    private static final String IMAGE_MANDELBROT_PATH = "src/main/resources/static/img/mandelbrot.jpg";
    private static final int BEAUTIFUL_COLORS = 6868;

    public Mandelbrot(int verticalSide, int horizontalSide, double zoom, int posX, int posY) {
        super("Mandelbrot Set", IMAGE_MANDELBROT_PATH);
        setZoom(zoom);
        setPosX(posX);
        setPosY(posY);
        setHorizontalSide(horizontalSide);
        setVerticalSide(verticalSide);
        setBounds(0, 0, getHorizontalSide(), getVerticalSide());
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public Mandelbrot() {
        super("Mandelbrot Set", IMAGE_MANDELBROT_PATH);
        setBounds(0, 0, getHorizontalSide(), getVerticalSide());
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public Mandelbrot(Fractal fractal) {
        super("Mandelbrot Set", IMAGE_MANDELBROT_PATH);
        setZoom(fractal.getZoom());
        setPosX(fractal.getPosX());
        setPosY(fractal.getPosY());
        setHorizontalSide(fractal.getHorizontalSide());
        setVerticalSide(fractal.getVerticalSide());
        setBounds(0, 0, fractal.getHorizontalSide(), fractal.getVerticalSide());
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void generateImageWithoutThreading() {
        int linesByChunk = 1000;
        System.out.println(getHeight() + " et lbc "+linesByChunk);
        for (int i = 0; i < getHeight()/linesByChunk; ++i) {
            for (int y = 0; y < getHeight(); y++) {
                for (int x = 0; x < getWidth(); x++) {
                    draw(x,y,i,getImage());
                }
            }
        }
    }

    public void draw(int x, int y, int fractionId, BufferedImage image) {
        zx = zy = 0;
        cX = (x + getPosX()) / getZoom();
        cY = (y + getPosY()) / getZoom();
        int iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        image.setRGB(x, y, iter*BEAUTIFUL_COLORS);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(getImage(), 0, 0, this);
    }
}
