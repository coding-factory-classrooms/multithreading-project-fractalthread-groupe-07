package org.example;

import java.awt.image.BufferedImage;

public interface Fractal {
    int getHorizontalSide();
    int getVerticalSide();
    BufferedImage getImage();
    void draw(int x, int y);
}
