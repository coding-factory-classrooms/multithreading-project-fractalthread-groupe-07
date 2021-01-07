package org.example;

import org.example.core.Template;

import java.io.IOException;
import java.util.HashMap;

public class MandelController {
    private boolean isGenerate = false;

    public String MandelInitialise() {
        //DO the thing
        if (!isGenerate) {
            RenderImage();
        }
        isGenerate = true;
        return Template.render("home.html", new HashMap<>());
    }

    public String MandelRefresh() {
        //DO the thing
        if (!isGenerate) {
            RenderImage();
        }
        String response = new String("");
        try {
            isGenerate = true;
            response = Mandelbrot.getFractalFromBuffer();
        } catch (IOException e) {
            e.printStackTrace();
            //return error img byte
        }
        return response;
    }
    public byte[] convertFileContentToBlob() {
        try {
            return Mandelbrot.convertFileContentToBlob();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void RenderImage() {
        Mandelbrot mandelbrot = new Mandelbrot(150);
        mandelbrot.saveFileAsJpg(mandelbrot.I);
    }
}
