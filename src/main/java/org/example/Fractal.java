package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public abstract class Fractal extends JFrame {

    String imagePath;
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
}
