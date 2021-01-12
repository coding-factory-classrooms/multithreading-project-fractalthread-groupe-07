package org.example;

import org.example.core.Template;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class RefreshController {
    final int pas = 100;
    double zoom = 5800; //200
    int posX = 2000; //400
    int posY = -2600; //-400
    private int side;

    public RefreshController(int side) {
        this.side = side;
    }

    public String mandelInitialise() {
        try {
            Mandelbrot mandelbrot = new Mandelbrot(side, zoom, posX, posY);
            RenderImage(mandelbrot);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Template.render("home.html", new HashMap<>());
    }

    public byte[] fractalRefresh(String direction, String type) throws IOException {

        System.out.println("fractal refresh");
        Fractal fractal = fractalFactory(type);
        switchDirection(direction, fractal);
        byte[] response = null;
        try {
            response = fractal.getFractalFromBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static void RenderImage(Fractal fractal) throws IOException {
        fractal.makeImage();
        new FractalDesigner(fractal).designFractal();
        fractal.saveFileAsJpg(fractal.image);
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

    public Fractal fractalFactory(String name) {
        switch (name) {
            case "mandel" :
                return new Mandelbrot(side, zoom, posX, posY);
            case "julia" :
                System.out.println("Julia ! ");
                return new Julia();
            default: return new Mandelbrot(side, zoom, posX, posY);
        }
    }

    public void switchDirection(String direction,Fractal fractal) throws IOException {

        switch (direction) {
            case "up" :
                RenderImage(fractal);
                this.posY = posY-pas;
                break;
            case "down" :
                RenderImage(fractal);
                this.posY = posY+pas;
                break;
            case "left" :
                RenderImage(fractal);
                this.posX = posX-pas;
                break;
            case "right" :
                RenderImage(fractal);
                this.posX = posX+pas;
                break;
            case "zoom" :
                RenderImage(fractal);
                this.zoom = zoom+pas;
                break;
            case "unzoom" :
                if (zoom-pas < 0) {
                    break;
                } else {
                    RenderImage(fractal);
                    this.zoom = zoom-pas;
                }
                break;
            default:
                RenderImage(fractal);
                System.out.println("direction default");
                break;
        }
    }

    /**
     * Just for stats
     */
    public static void main(String[] args) {
        try {
            int side = 1000;
            RefreshController controller = new RefreshController(side);
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

    public void writeStats(boolean withThreading, int runs, int side, double zoom, int posX, int posY) throws IOException {
        long stepMS = 0;
        long startTotal = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            long start = System.currentTimeMillis();
            Mandelbrot mandelbrot = new Mandelbrot(side, zoom, posX, posY); // initialize at -250 and then user moves
            mandelbrot.makeImage();
            if (withThreading) {
                new FractalDesigner(mandelbrot).designFractal();
            } else {
                mandelbrot.generateImageWithoutThreading();
            }
            mandelbrot.saveFileAsJpg(mandelbrot.image);
            long elapsed = System.currentTimeMillis() - start;
            stepMS = stepMS + elapsed;

            FileWriter writer = new FileWriter("stats.md", true);
            writer.write("multithread: " + String.valueOf(withThreading).toUpperCase(Locale.ROOT) + " Run " + i + " sur " + runs +" avec un fractal de "+side + "px sur " +  "TPS " + elapsed + "MS"+ "\r\n");
            writer.close();
        }
        long elapsedTotal = System.currentTimeMillis() - startTotal;

        Date dateNow = new Date();
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.SHORT);
        try {
            FileWriter writer = new FileWriter("stats.md", true);
            writer.write("Total ELapsed Time:  " + elapsedTotal + " MS" + "\r\n");
            writer.write("Génération du fractal de "+side+" pixels de côté sur "+runs+" runs *avec multi-threads="+withThreading+"* par membre d'équipe: " + "\r\n");
            writer.close();

            String dateAndTimeToSave = shortDateFormat.format(dateNow) + " - " + stepMS / runs;

            saveTimeInFile(dateAndTimeToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
