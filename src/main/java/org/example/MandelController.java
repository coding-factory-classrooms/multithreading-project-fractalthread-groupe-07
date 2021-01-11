package org.example;

import org.example.core.Template;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

public class MandelController {
    final int pas = 100;
    double zoom = 200;
    int posX = -400;
    int posY = -400;
    public String MandelInitialise() {
        //DO the thing
        try {
            RenderImage(200, -400, -400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Template.render("home.html", new HashMap<>());
    }

    public void switchDirection(String direction) throws IOException {

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
            default:
                RenderImage(zoom, posX, posY);
                break;
        }

    }

    public byte[] mandelRefresh(String direction) throws IOException {
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

    private static void RenderImage(double zoom, int posX, int posY) throws IOException {
        //BOOLEAN BELOW MUST BE SWITCH TO TRUE IF YOU WANT TO WRITE YOUR TIME IN stats.md FILE

        boolean statsTenToWrite = true;
        long stepMS = 0;

        int runs = 10;

        if (statsTenToWrite) {
            for (int i = 0; i < runs; i++) {
                long start = System.currentTimeMillis();
                Mandelbrot mandelbrot = new Mandelbrot(zoom, posX, posY); // initialize at -250 and then user moves
                new FractalDesigner(mandelbrot).designFractal();
                mandelbrot.saveFileAsJpg(mandelbrot.image);
                long elapsed = System.currentTimeMillis() - start;
                stepMS = stepMS + elapsed;
            }

            Date dateNow = new Date();
            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.MEDIUM,
                    DateFormat.SHORT);
            try {
                FileWriter writer = new FileWriter("stats.md", true);
                writer.write("Génération du fractal sur "+runs+" runs *avec threads* par membre d'équipe: " + "\r\n");
                writer.close();

                String dateAndTimeToSave = shortDateFormat.format(dateNow) + " - " + stepMS / runs;

                saveTimeInFile(dateAndTimeToSave);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Mandelbrot mandelbrot = new Mandelbrot(zoom, posX, posY); // initialize at -250 and then user moves
            new FractalDesigner(mandelbrot).designFractal();
            mandelbrot.saveFileAsJpg(mandelbrot.image);
        }
    }

    public static void writeStats(double zoom, int posX, int posY) throws IOException {
            //BOOLEAN BELOW MUST BE SWITCH TO TRUE IF YOU WANT TO WRITE YOUR TIME IN stats.md FILE
            boolean statsTenToWrite = true;

            long stepMS = 0;
            int runs = 10;

            for (int i = 0; i < runs; i++) {
                long start = System.currentTimeMillis();
                Mandelbrot mandelbrot = new Mandelbrot(zoom, posX, posY); // initialize at -250 and then user moves
                new FractalDesigner(mandelbrot).designFractal();
                mandelbrot.saveFileAsJpg(mandelbrot.image);
                long elapsed = System.currentTimeMillis() - start;
                stepMS = stepMS + elapsed;
            }

            Date dateNow = new Date();
            DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.MEDIUM,
                    DateFormat.SHORT);
            try {
                FileWriter writer = new FileWriter("stats.md", true);
                writer.write("Génération du fractal sur "+runs+" runs *avec threads* par membre d'équipe: " + "\r\n");
                writer.close();

                String dateAndTimeToSave = shortDateFormat.format(dateNow) + " - " + stepMS / runs;

                saveTimeInFile(dateAndTimeToSave);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void saveTimeInFile(String timeToWrite) {
        try {
            FileWriter writer = new FileWriter("stats.md", true);
            writer.write(System.getProperty("user.name") + " - " + timeToWrite + " " + "average" + " ms" + "\r\n");
            writer.write("-------------------------------------------------------------------------");
            writer.write("\r\n");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
