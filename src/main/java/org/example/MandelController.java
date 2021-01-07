package org.example;

import org.example.core.Template;

import java.io.IOException;
import java.util.HashMap;

public class MandelController {
    public String MandelInitialise() {
        //DO the thing
        RenderImage(200,-400, -400);
        return Template.render("home.html", new HashMap<>());
    }



    public void switchDirection(String direction) {
        final int pas = 50;
        double zoom = 600;
        int posX = -400;
        int posY = -400;

        switch (direction) {
            case "up" :
                RenderImage(zoom, posX, posY+pas);
                break;
            case "down" :
                RenderImage(zoom, posX, posY-pas);
                break;
            case "left" :
                RenderImage(zoom, posX-pas, posY);
                break;
            case "right" :
                RenderImage(zoom, posX+pas, posY);
                break;
            case "zoom" :
                RenderImage(zoom+pas, posX, posY);
                break;
            case "unzoom" :
                RenderImage(zoom-pas, posX, posY);
                break;
            default :
                RenderImage(zoom, posX, posY);
                System.out.println("ça marche pâaaa "+direction);
                break;

        }

    }

    public byte[] mandelRefresh(String direction) {
        direction = direction.substring(1,direction.length()-1);
        switchDirection(direction);
        byte[] response = null;
        try {
            response = Mandelbrot.getFractalFromBuffer();
        } catch (IOException e) {
            e.printStackTrace();
            //return error img byte
        }
        return response;
    }

    private static void RenderImage(double zoom, int posX, int posY) {
        Mandelbrot mandelbrot = new Mandelbrot(zoom,posX, posY); // iniialize at -250 and then user moves
        mandelbrot.saveFileAsJpg(mandelbrot.I);
    }




}
