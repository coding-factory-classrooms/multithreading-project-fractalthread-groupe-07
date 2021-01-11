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
    private int side;

    public MandelController(int side) {
        this.side = side;
    }

    public String mandelInitialise() {
        try {
            RenderImage(side,200, -400, -400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Template.render("home.html", new HashMap<>());
    }

    public void switchDirection(String direction) throws IOException {

        switch (direction) {
            case "up" :
                RenderImage(side, zoom, posX, posY-pas);
                this.posY = posY-pas;
                break;
            case "down" :
                RenderImage(side, zoom, posX, posY+pas);
                this.posY = posY+pas;
                break;
            case "left" :
                RenderImage(side, zoom, posX-pas, posY);
                this.posX = posX-pas;
                break;
            case "right" :
                RenderImage(side, zoom, posX+pas, posY);
                this.posX = posX+pas;
                break;
            case "zoom" :
                RenderImage(side,zoom+pas, posX, posY);
                this.zoom = zoom+pas;
                break;
            case "unzoom" :
                if (zoom-pas < 0) {
                    break;
                } else {
                    RenderImage(side,zoom-pas, posX, posY);
                    this.zoom = zoom-pas;
                }
                break;
            default:
                RenderImage(side, zoom, posX, posY);
                break;
        }
    }

    public byte[] mandelRefresh(String direction) throws IOException {
        switchDirection(direction);
        byte[] response = null;
        try {
            response = Mandelbrot.getFractalFromBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static void RenderImage(int side, double zoom, int posX, int posY) throws IOException {
        Mandelbrot mandelbrot = new Mandelbrot(side, zoom, posX, posY); // initialize at -250 and then user moves
        new FractalDesigner(mandelbrot).designFractal();
        mandelbrot.saveFileAsJpg(mandelbrot.image);
    }

    public void writeStats(boolean withThreading, int runs, int side, double zoom, int posX, int posY) throws IOException {
            long stepMS = 0;
            for (int i = 0; i < runs; i++) {
                long start = System.currentTimeMillis();
                Mandelbrot mandelbrot = new Mandelbrot(side, zoom, posX, posY); // initialize at -250 and then user moves
                if (withThreading) {
                    new FractalDesigner(mandelbrot).designFractal();
                } else {
                    mandelbrot.generateImageWithoutThreading();
                }
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
                writer.write("Génération du fractal sur "+runs+" runs *avec multi-threads="+withThreading+"* par membre d'équipe: " + "\r\n");
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

    /**
     * Just for stats
     */
    public static void main(String[] args) {
        try {
            int side = 500;
            MandelController controller = new MandelController(side);
            int runs = 10;

            System.out.println("_without threading_");
            controller.writeStats(false,runs, side,200, -400, -400);
            System.out.println("_with threading_");
            controller.writeStats(true,runs,side,200, -400, -400);
            System.out.println("Stats OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
