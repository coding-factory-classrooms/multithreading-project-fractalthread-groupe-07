package org.example;

import java.awt.image.BufferedImage;

public interface Fractal {
    int getSide();
    BufferedImage getImage();
    void draw(int x, int y);
}
