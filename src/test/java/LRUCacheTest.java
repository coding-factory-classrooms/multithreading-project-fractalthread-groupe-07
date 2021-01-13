import org.example.LRUCache;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class TestLruCache {

    static class MyHardDrive {
        Map<String, Object> resources = new HashMap<>();

        MyHardDrive(){
            for(char i = 'A'; i<='Z'; i++){
                resources.put(Character.toString(i), new Object());
            }
        }

        Object loadResource(String name){
            return resources.get(name);
        }
    }

    @Test
    public static void main(String[] args) {
        MyHardDrive hd = new MyHardDrive();
        LRUCache<String,Object> cache = new LRUCache<>(4);

        for(String key: Arrays.asList("A","B","C","A","D","E","F","E","F","G","A")){
            Object object = cache.get(key);
            if(object==null){
                object = hd.loadResource(key);
                cache.put(key, object);
                System.out.println("key :" + key);
                System.out.println("object :" + hd.loadResource(key).toString());
            }
            cache.printSequence();
        }
    }
}