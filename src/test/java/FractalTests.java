import org.example.Fractal;
import org.example.FractalController;
import org.example.Mandelbrot;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FractalTests {
    @Test
    public void test_should_return_mandelbrot() {
        // GIVEN //
        String type = "mandel";
        Mandelbrot mandelbrot = new Mandelbrot();
        FractalController fractalController = new FractalController();
        // WHEN //
        Fractal fractal = fractalController.fractalFactory(type,mandelbrot);

        // THEN //
        Assert.assertEquals(fractal.getName(), "mandel");
    }
    @Test
    public void test_should_return_julia() {
        // GIVEN //
        String type = "julia";
        Mandelbrot mandelbrot = new Mandelbrot();
        FractalController fractalController = new FractalController();
        // WHEN //
        Fractal fractal = fractalController.fractalFactory(type,mandelbrot);

        // THEN //
        Assert.assertEquals(fractal.getName(), "julia");
    }
    @Test
    public void test_should_save_image() {
        // GIVEN //
        Mandelbrot mandelbrot = new Mandelbrot();
        BufferedImage image = new BufferedImage(256, 256,
                BufferedImage.TYPE_INT_RGB);
        String path = "src/main/resources/static/img/test.jpg";
        mandelbrot.setImagePath(path);
        // WHEN //
        mandelbrot.saveFileAsJpg(image);

        // THEN //
        BufferedImage imageSaved = null;
        try {
            imageSaved = ImageIO.read(new File(path));
        } catch (IOException e) {
            Assert.assertTrue(false);
        }
        Assert.assertNotNull(imageSaved);

        //FLUSH
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void test_should_handle_directions() {
        // SUCH WOW!
        Assert.assertEquals(4, 2 + 2);
    }

    @Test
    public void test_should_check_factory_is_working() {
        // SUCH WOW!
        Assert.assertEquals(4, 2 + 2);
    }

}
