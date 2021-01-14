import org.example.LRUCache;
import org.junit.Assert;
import org.junit.Test;

public class LRUTest {

    private final LRUCache<String,Object> cache = new LRUCache<>(4);

    @Test
    public void testCacheStartsEmpty() {
        Assert.assertEquals(cache.checkIfEmpty(1), -1);
    }
}
