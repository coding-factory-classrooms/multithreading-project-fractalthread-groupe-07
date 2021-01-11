package org.example;

public class FractalTask implements Runnable {
    private static Fractal fractal;
    private int posX;
    private int posY;
    private static int linesByChunk;
    private int chunkIteration;

    public FractalTask(int theChunkIteration) {
        chunkIteration = theChunkIteration;
    }
    public FractalTask(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public static void setFractal(Fractal theFractal) {
        fractal = theFractal;
    }

    public static void setLinesByChunk(int theLinesByChunk) {
        linesByChunk = theLinesByChunk;
    }

    @Override
    public void run() {
        for (int y = 0; y < linesByChunk; y++) {
            for (int x = 0; x < fractal.getSide(); x++) {
                if (x % 100 == 0) {
                    //System.out.println("x="+x+" y="+y+chunkIteration);
                    //System.out.println(y);
                }
                fractal.draw(x,y+chunkIteration*linesByChunk);
            }
        }
    }
}
