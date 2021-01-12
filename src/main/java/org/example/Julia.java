package org.example;

import org.example.ComplexNumber;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import static java.lang.System.out;

public class Julia extends Fractal
{
    final static String imagePath = "src/main/resources/static/img/julia.jpg";

    public Julia() {
       super("Julia Set",imagePath);
    }

    @Override
    void draw(int x, int y) {
        // Taking the Image WIDTH and HEIGHT variables. Increasing or decreasing the value will affect computation time.
        double WIDTH = 1000;
        double HEIGHT = 1000;
        System.out.println("it is ");

        // Setting the Saturation of every pixel to maximum
        // This can be played with to get different results
        float Saturation = 1f;

        // Creating a new blank RGB Image to draw the fractal on
        BufferedImage img = new BufferedImage((int)WIDTH, (int)HEIGHT,BufferedImage.TYPE_3BYTE_BGR);

        // Getting the constant ComplexNumber as input from the user for use in the function f(z) = z + c
        out.print("Re(c): -0.62772"); //-0.835 -0.2321 is cool too
        double cReal = -0.62772;  // cuz its stylé
        out.print("Im(c): 0.42193i");
        double cImag = 0.42193;

        // Creating the constant complex number from input real and imaginary values
        ComplexNumber constant = new ComplexNumber(cReal,cImag);

        // Setting the maximum iterations to 256. This can be increased if you suspect an escapee set may be found beyond this value.
        // Increasing or decreasing the value will affect computation time.

        int max_iter = 256;

        // Looping through every pixel of image
        for(int X=0; X<WIDTH; X++)
        {
            //System.out.println("it is widg");

            for(int Y=0; Y<HEIGHT; Y++)
            {
                //System.out.println("it is heig");

                // Creating an empty complex number to hold the value of last z
                ComplexNumber oldz = new ComplexNumber();

                // Setting the value of z0 for every pixel
                // z0 is a function of (x,y) i.e. the pixel co-ordinates.
                // The function can be anything, but should depend on (x,y) in some way and should lie between [-1,1]
                // I use the function,
                // Re(z) = 2*(X-WIDTH/2)/(WIDTH/2)
                // Im(z) = 1.33*(Y-HEIGHT/2)/(HEIGHT/2)
                // This gives a good centered fractal.You can play around with the function to get better results.

                ComplexNumber newz = new ComplexNumber(2.0*(X-WIDTH/2)/(WIDTH/2), 1.33*(Y-HEIGHT/2)/(HEIGHT/2) );

                // Iterating till the orbit of z0 escapes the radius 2 or till maximum iterations are completed
                int i;
                for(i=0;i<max_iter; i++)
                {
                    // Saving the current z in oldz
                    oldz = newz;

                    // Applying the function newz = newz^2 + c, where c is the constant ComplexNumber user input
                    newz = newz.square();
                    newz.add(constant);

                    // Checking if the modulus/magnitude of complex number has exceeded the radius of 2
                    // If yes, the pixel is an escapee and we break the loop
                    if(newz.mod() > 2)
                        break;
                }

                // Checking if the pixel is an escapee
                // If yes, setting the brightness to the maximum
                // If no, setting the brightness to zero since the pixel is a prisoner
                float Brightness = i < max_iter ? 1f : 0;

                // Setting Hue to a function of number of iterations (i) taken to escape the radius 2
                // Hue = (i%256)/255.0f;
                // i%256 to bring i in range [0,255]
                // Then dividing by 255.0f to bring it in range [0,1] so that we can pass it to Color.getHSBColor(H,S,B) function
                float Hue = (i%256)/255.0f;

                // Creating the color from HSB values and setting the pixel to the computed color
                Color color = Color.getHSBColor(Hue, Saturation, Brightness);
                img.setRGB(X,Y,color.getRGB());
                image = img;
            }
        }
        // Saving the image
        System.out.println("it is donerino");
    }
    public BufferedImage getImage() {
        return image;
    }
    public int getHorizontalSide() {
        return horizontalSide;
    }

    public void setHorizontalSide(int verticalSide) {
        this.horizontalSide = verticalSide;
    }

    public int getVerticalSide() {
        return verticalSide;
    }

    public void setVerticalSide(int horizontalSide) {
        this.horizontalSide = horizontalSide;
    }

}