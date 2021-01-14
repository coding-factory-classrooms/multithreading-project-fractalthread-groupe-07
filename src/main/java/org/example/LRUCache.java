package org.example;

import java.util.*;

public class LRUCache<K, V> {

    private LinkedHashMap<K, V> lruCacheMap;
    private final int capacity;
    private final boolean SORT_BY_ACCESS = false;
    private final float LOAD_FACTOR = 0.75F;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.lruCacheMap = new LinkedHashMap<>(capacity, LOAD_FACTOR, SORT_BY_ACCESS);
        Set entrySet = lruCacheMap.entrySet();
        Iterator it = entrySet.iterator();
    }

    public int checkIfEmpty(int key) {
        V value = this.lruCacheMap.get(key);
        if (value == null) {
            return -1;
        } else {
            return 1;
        }
    }

    public K getKeyByValue(V v) {
        for (Map.Entry<K, V> entry : lruCacheMap.entrySet()) {
            if (entry.getValue().equals(v)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public V getValue(K k) {
        for (Map.Entry<K, V> entry : lruCacheMap.entrySet()) {
            if (entry.getKey().equals(k)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean containValue(V v) {
        for (Map.Entry<K, V> entry : lruCacheMap.entrySet()) {
            if (entry.getValue().equals(v)) {
                return true;
            }
        }
        return false;
    }

    public void put(K k, V v) {
        if (lruCacheMap.containsKey(k)) {
            lruCacheMap.remove(k);
        } else if (lruCacheMap.size() >= capacity) {
            lruCacheMap.remove(lruCacheMap.keySet().iterator().next());
        }
        lruCacheMap.put(k, v);
    }

    public void printSequence() {
        System.out.println(lruCacheMap.keySet());
        System.out.println(lruCacheMap.values());
    }

    public Integer keyEntriesInit() {
        int count = 0;
        if (lruCacheMap != null) {
            for (Map.Entry<K, V> ignored : lruCacheMap.entrySet()) {
                count++;
            }
            return count;
        } else {
            return -1;
        }

    }


}