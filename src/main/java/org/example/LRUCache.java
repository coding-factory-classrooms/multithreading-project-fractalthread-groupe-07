package org.example;

import java.util.*;

public class LRUCache<K, V>{

    private LinkedHashMap<K, V> lruCacheMap;
    private final int capacity;
    private final boolean SORT_BY_ACCESS = false;
    private final float LOAD_FACTOR = 0.75F;



    public LRUCache(int capacity){
        this.capacity = capacity;
        this.lruCacheMap = new LinkedHashMap<>(capacity, LOAD_FACTOR, SORT_BY_ACCESS);
        Set entrySet = lruCacheMap.entrySet();
        Iterator it = entrySet.iterator();
    }

    public K getKey(V v){
        for(Map.Entry<K, V> entry : lruCacheMap.entrySet()){
            if (entry.getValue().equals(v)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public V getValue(K k){
        for(Map.Entry<K, V> entry : lruCacheMap.entrySet()){
            if (entry.getKey().equals(k)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean containValue(V v){
        for(Map.Entry<K, V> entry : lruCacheMap.entrySet()){
            if (entry.getValue().equals(v)) {
                return true;
            }
        }
        return false;
    }

    public void printSequence(){
        System.out.println(lruCacheMap.keySet());
    }


}