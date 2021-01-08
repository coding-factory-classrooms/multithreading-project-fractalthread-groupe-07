package org.example;

import org.example.core.Template;

import java.io.IOException;
import java.util.HashMap;

public class MandelController {
    final int pas = 50;
    double zoom = 200;
    int posX = -400;
    int posY = -400;
    public String MandelInitialise() {
        //DO the thing
        RenderImage(200,-400, -400);
        return Template.render("home.html", new HashMap<>());
    }

    public void switchDirection(String direction) {

        switch (direction) {
            case "up" :
                RenderImage(zoom, posX, posY-pas);
                this.posY = posY-pas;
                break;
            case "down" :
                RenderImage(zoom, posX, posY+pas);
                this.posY = posY+pas;
                break;
            case "left" :
                RenderImage(zoom, posX-pas, posY);
                this.posX = posX-pas;
                break;
            case "right" :
                RenderImage(zoom, posX+pas, posY);
                this.posX = posX+pas;
                break;
            case "zoom" :
                RenderImage(zoom+pas, posX, posY);
                this.zoom = zoom+pas;
                break;
            case "unzoom" :
                if (zoom-pas < 0) {
                    break;
                } else {
                    RenderImage(zoom-pas, posX, posY);
                    this.zoom = zoom-pas;
                }
                break;
            default :
                RenderImage(zoom, posX, posY);
                break;
        }

    }

    public byte[] mandelRefresh(String direction) {
       // direction = direction.substring(1,direction.length()-1);
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
