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

    public Mandelbrot(int verticalSide, int horizontalSide, double zoom, int posX, int posY) {
        super("Mandelbrot Set", IMAGE_MANDELBROT_PATH);
        ZOOM = zoom;
        this.posX = posX;
        this.posY = posY;
        setBounds(0, 0, verticalSide, horizontalSide);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.horizontalSide = horizontalSide;
        this.verticalSide = verticalSide;
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

    public void saveFileAsJpg(BufferedImage bufferedImage) {
        try {
            BufferedImage bi = bufferedImage;
            File outputFile = new File(IMAGE_MANDELBROT_PATH);
            ImageIO.write(bi, "jpg", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void makeImage() {
        image = new BufferedImage(verticalSide, horizontalSide, BufferedImage.TYPE_INT_RGB);
    }
    public BufferedImage getImage() {
        return image;
    }

    public int getHorizontalSide() {
        return horizontalSide;
    }

    public void setHorizontalSide(int verticalSide) {
        this.horizontalSide = verticalSide;
    }

    public int getVerticalSide() {
        return verticalSide;
    }

    public void setVerticalSide(int horizontalSide) {
        this.horizontalSide = horizontalSide;
    }

}
