package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {
    private final int MAX_ITER = 5000;
    private final double ZOOM;
    public BufferedImage I;
    private double zx, zy, cX, cY, tmp;
    private int posX, posY;
    private static final String IMAGE_MANDELBROT_PATH = "src/main/resources/static/img/mandelbrot.jpg";
    private static final int BEAUTIFUL_COLORS = 1000000;


    public Mandelbrot(double zoom, int posX, int posY) {
        super("Mandelbrot Set");
        ZOOM = zoom;
        this.posX = posX;
        this.posY = posY;
        setBounds(0, 0, 1000, 1000);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
//TODO : run 1 thread by processor
//              FirstTask task = new FirstTask();
//            threadPool.execute(task);

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
                I.setRGB(x, y, iter*BEAUTIFUL_COLORS);
            }
        }
    }



    public static byte[] getFractalFromBuffer() throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(IMAGE_MANDELBROT_PATH));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( originalImage, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        return imageInByte;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
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
}
