package org.example;

import javax.swing.*;
import java.awt.image.BufferedImage;

public abstract class Fractal extends JFrame {

    abstract int getSide();
    abstract BufferedImage getImage();
    abstract void draw(int x, int y);

    public Fractal(String title) {
        super(title);
    }
}
