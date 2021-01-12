import org.example.OurThreadPoolExecutor;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;

public class OurThreadPoolTest {
    @Test
    public void runThreads() {
        int coreNumber = Runtime.getRuntime().availableProcessors();
        ExecutorService threadPool = new OurThreadPoolExecutor(coreNumber);
        //TO DO : continue...
        // SUCH WOW!
        Assert.assertEquals(4, 2 + 2);
    }
}
