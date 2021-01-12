package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Mandelbrot extends Fractal {
    private final int MAX_ITER = 5000;
    private final double ZOOM;
    private double zx, zy, cX, cY, tmp;
    private int posX, posY;
    private static final String IMAGE_MANDELBROT_PATH = "src/main/resources/static/img/mandelbrot.jpg";
    private static final int BEAUTIFUL_COLORS = 6868;

    public Mandelbrot(int side, double zoom, int posX, int posY) {
        super("Mandelbrot Set", IMAGE_MANDELBROT_PATH);
        ZOOM = zoom;
        this.posX = posX;
        this.posY = posY;
        setBounds(0, 0, side, side);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.side = side;
    }

    public void generateImageWithoutThreading() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                draw(x,y);
            }
        }
    }

    public void draw(int x, int y) {
        zx = zy = 0;
        cX = (x + posX) / ZOOM;
        cY = (y + posY) / ZOOM;
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
        g.drawImage(image, 0, 0, this);
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

}
