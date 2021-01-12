package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public abstract class Fractal extends JFrame {

    String imagePath;
    public BufferedImage image;
    public int side = 1000;

    abstract int getSide();
    abstract BufferedImage getImage();
    abstract int getVerticalSide();
    abstract int getHorizontalSide();
    abstract void draw(int x, int y);

    public Fractal(String title, String imagePath) {
        super(title);
        this.imagePath = imagePath;
    }

    public byte[] getFractalFromBuffer() throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(this.imagePath));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( originalImage, "jpg", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        return imageInByte;
    }

    public void makeImage() {
        this.image = new BufferedImage(side, side, BufferedImage.TYPE_INT_RGB);
    }

    public void saveFileAsJpg(BufferedImage bufferedImage) {
        try {
            BufferedImage bi = bufferedImage;
            File outputFile = new File(imagePath);
            ImageIO.write(bi, "jpg", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        if (imagePath.equals("src/main/resources/static/img/mandelbrot.jpg")) {
            return "mandel";
        }
        else if (imagePath.equals("src/main/resources/static/img/julia.jpg")) {
            return "julia";
        }
        else
            return "none";
    }

    public BufferedImage getImage() {
        return image;
    }
}
