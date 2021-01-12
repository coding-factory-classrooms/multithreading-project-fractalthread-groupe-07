package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public abstract class Fractal extends JFrame {
    private String imagePath;
    private BufferedImage image;
    private int verticalSide = 1000;
    private int horizontalSide = 1000;
    private double zoom = 5000;
    private int posX = 2000;
    private int posY = -2600;

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
        this.image = new BufferedImage(verticalSide, horizontalSide, BufferedImage.TYPE_INT_RGB);
    }

    public void saveFileAsJpg(BufferedImage bufferedImage) {
        try {
            File outputFile = new File(this.imagePath);
            ImageIO.write(bufferedImage, "jpg", outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return switch (imagePath) {
            case "src/main/resources/static/img/mandelbrot.jpg" -> "mandel";
            case "src/main/resources/static/img/julia.jpg" -> "julia";
            default -> "none";
        };
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setVerticalSide(int verticalSide) {
        this.verticalSide = verticalSide;
    }

    public void setHorizontalSide(int horizontalSide) {
        this.horizontalSide = horizontalSide;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    public int getVerticalSide() {
        return this.verticalSide;
    }
    public int getHorizontalSide() {
        return this.horizontalSide;
    }
}
