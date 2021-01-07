package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {
    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    public BufferedImage I;
    private double zx, zy, cX, cY, tmp;

    public Mandelbrot() {
        super("Mandelbrot Set");
        setBounds(100, 100, 500, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = (x - 500) / ZOOM;
                cY = (y - 500) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                I.setRGB(x, y, iter | (iter << 8));
            }
        }
        invertBlackAndWhite(I);
    }

    public void generateMandelbrot() {

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public void saveFileAsJpg(BufferedImage bufferedImage) {
        try {
            BufferedImage bi = bufferedImage;
            File outputFile = new File("src/main/resources/static/img/mandelbrot.jpg");
            ImageIO.write(bi, "jpg", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage invertBlackAndWhite(BufferedImage image) {
        BufferedImage imageOut = image;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color c = new Color(image.getRGB(x, y));
                //invert white into black
                if (c.equals(Color.white)) {
                    I.setRGB(x, y, Color.black.getRGB());
                } else {
                    I.setRGB(x, y, Color.white.getRGB());
                }
            }
        }

        return imageOut;
    }

    public static void saveTimeInFile(String timeToWrite) {
        try {
            FileWriter writer = new FileWriter("stats.md", true);
            writer.write("\r\n");
            writer.write(timeToWrite);
            writer.write("-------------------------------------------------------------------------");
            writer.write("\r\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
