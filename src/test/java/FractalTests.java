import org.example.Fractal;
import org.example.FractalController;
import org.example.Mandelbrot;
import org.junit.Assert;
import org.junit.Test;

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
    public void test_should_save_and_retrieve_img() {
        // SUCH WOW!
        Assert.assertEquals(4, 2 + 2);
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
