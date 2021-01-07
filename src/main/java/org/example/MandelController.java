package org.example;

import org.example.core.Template;

import java.io.IOException;
import java.util.HashMap;

public class MandelController {
    public String MandelInitialise() {
        //DO the thing
        RenderImage();
        return Template.render("home.html", new HashMap<>());
    }

    public byte[] mandelRefresh() {
        //DO the thing
        byte[] response = null;
        try {
            response = Mandelbrot.getFractalFromBuffer();
        } catch (IOException e) {
            e.printStackTrace();
            //return error img byte
        }
        return response;
    }
//    public byte[] convertFileContentToBlob() {
//        try {
//            return Mandelbrot.convertFileContentToBlob();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private static void RenderImage() {
        Mandelbrot mandelbrot = new Mandelbrot(150);
        mandelbrot.saveFileAsJpg(mandelbrot.I);
    }
}
