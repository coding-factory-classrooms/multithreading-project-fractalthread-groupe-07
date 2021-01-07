package org.example;

import org.example.core.Template;

import java.util.HashMap;

public class MandelController {
    public String MandelRefresh() {
        //DO the thing
        RenderImage();
        return Template.render("home.html", new HashMap<>());
    }

    public static void RenderImage() {
        Mandelbrot mandelbrot = new Mandelbrot(150);
        mandelbrot.saveFileAsJpg(mandelbrot.I);
    }
}
