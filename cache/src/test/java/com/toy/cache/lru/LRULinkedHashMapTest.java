package com.toy.cache.lru;

import org.junit.Test;

import java.util.Map;

public class LRULinkedHashMapTest {
    @Test
    public void LruTest() {
        LRULinkedHashMap<Integer, Integer> hashMap = new LRULinkedHashMap(10);
        for (int i = 0; i < 10; i++) {
            hashMap.put(i, 1);
        }
        hashMap.get(0);
        hashMap.put(10, 1);
        for (Map.Entry entry : hashMap.entrySet()) {
            System.out.println(entry.getKey());
        }
        hashMap.get(5);
        for (Map.Entry entry : hashMap.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
}
