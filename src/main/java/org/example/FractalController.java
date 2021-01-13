package org.example;

import org.example.core.Template;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Executors;

public class FractalController {
    final int pas = 100;
    final float pasZoomJulia = 2F;
/*
    double zoom = 5800; //200
    int posX = 2000; //400
    int posY = -2600; //-400
    private int verticalSide;
    private int horizontalSide;
*/
private Fractal fractal;

    public String fractalControllerInitialise() {
        try {
            fractal = new Mandelbrot();
            RenderImage(fractal);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Template.render("home.html", new HashMap<>());
    }

    public byte[] fractalRefresh(String direction, String type) throws IOException {
        fractal = fractalFactory(type,fractal);
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
        int coreNumber = Runtime.getRuntime().availableProcessors();
        new FractalDesigner(fractal, Executors.newFixedThreadPool(coreNumber)).designFractal();
        fractal.saveFileAsJpg(fractal.getImage());
    }

    public Fractal fractalFactory(String name, Fractal fractal) {
        switch (name) {
            case "mandel" :
                return new Mandelbrot(fractal);
            case "julia" :
                System.out.println("Julia ! ");
                return new Julia(fractal);
            default: return new Mandelbrot(fractal);
        }
    }

    public byte[] fractalSizing(String verticalSide, String horizontalSide) throws IOException {
        resize(verticalSide,horizontalSide);
        RenderImage(fractal);
        byte[] response = null;
        try {
            response = fractal.getFractalFromBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void resize(String verticalSide, String horizontalSide) throws IOException {
        fractal.setVerticalSide(Integer.parseInt(verticalSide));
        fractal.setHorizontalSide(Integer.parseInt(horizontalSide));
    }

    public void switchDirection(String direction,Fractal fractal) throws IOException {

        switch (direction) {
            case "up" :
                this.fractal.setPosY(this.fractal.getPosY()-pas);
                break;
            case "down" :
                this.fractal.setPosY(this.fractal.getPosY()+pas);
                break;
            case "left" :
                this.fractal.setPosX(this.fractal.getPosX()-pas);
                break;
            case "right" :
                this.fractal.setPosX(this.fractal.getPosX()+pas);
                break;
            case "zoom" :
                if (this.fractal.getName().equals("julia")) {
                    this.fractal.setZoom(this.fractal.getZoom()/pasZoomJulia);
                    break;
                }
                this.fractal.setZoom(this.fractal.getZoom()+pas);
                break;
            case "unzoom" :
                if (this.fractal.getZoom()-pas < 0) {
                    break;
                } else {
                    if (this.fractal.getName().equals("julia")) {
                        this.fractal.setZoom(this.fractal.getZoom()*pasZoomJulia);
                        break;
                    }
                    this.fractal.setZoom(this.fractal.getZoom()-pas);
                }
                break;
        }
        RenderImage(fractal);
    }

    public static void main(String[] args) {
//        try {
//            int horizontalSide = 1000;
//            int verticalSide = 1000;
//            FractalController controller = new FractalController();
//            int runs = 10;
//
//            System.out.println("_with threading_");
//            controller.writeStats(true,runs,horizontalSide,verticalSide,200, -400, -400);
//
//            System.out.println("_without threading_");
//            controller.writeStats(false,runs, horizontalSide,verticalSide,200, -400, -400);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        FractalController controller = new FractalController();
        int runs = 10;

        long elapsed = 0;
        System.out.println("with threading");
        Mandelbrot fractal = new Mandelbrot();
        fractal.makeImage();
        long start = System.currentTimeMillis();
        for (int i = 0; i < runs; i++) {
            new FractalDesigner(fractal, Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())).designFractal();
            elapsed +=  System.currentTimeMillis() - start;
            System.out.println(System.currentTimeMillis() - start);
        }

        System.out.println("average with threading "+(elapsed/runs));

//        System.out.println("without threading");
//        start = System.currentTimeMillis();
//        elapsed = 0;
//        for (int i = 0; i < runs; i++) {
//            fractal.generateImageWithoutThreading();
//            elapsed +=  System.currentTimeMillis() - start;
//            System.out.println(System.currentTimeMillis() - start);
//        }
//
//        System.out.println("average without threading "+(elapsed/runs));


        System.out.println("Stats OK");
    }

    /**
     * Just for stats
     */
    public void writeStats(boolean withThreading, int runs, int verticalSide, int horizontalSide, double zoom, int posX, int posY) throws IOException {
        long stepMS = 0;
        long startTotal = System.currentTimeMillis();
        int coreNumber = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < runs; i++) {
            long start = System.currentTimeMillis();
            Mandelbrot mandelbrot = new Mandelbrot(verticalSide,horizontalSide, zoom, posX, posY); // initialize at -250 and then user moves
            mandelbrot.makeImage();
            if (withThreading) {
                new FractalDesigner(mandelbrot, Executors.newFixedThreadPool(coreNumber)).designFractal();
            } else {
                mandelbrot.generateImageWithoutThreading();
            }
            mandelbrot.saveFileAsJpg(mandelbrot.getImage());
            long elapsed = System.currentTimeMillis() - start;
            stepMS = stepMS + elapsed;

            FileWriter writer = new FileWriter("stats.md", true);
            writer.write("multithread: " + String.valueOf(withThreading).toUpperCase(Locale.ROOT) + " Run " + (i+1) + " sur " + runs +" avec un fractal de "+verticalSide+"X"+horizontalSide+ "px sur " +  "TPS " + elapsed + "MS"+ "\r\n");
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
            writer.write("Génération du fractal  de "+verticalSide+"X"+horizontalSide+" pixels de côté sur "+runs+" runs *avec multi-threads="+withThreading+"* par membre d'équipe: " + "\r\n");
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
