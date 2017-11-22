package com.toy.cache.lru;

import java.util.LinkedHashMap;

public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> implements Lru<K, V> {
    private int maxCapacity;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public LRULinkedHashMap(int maxCapacity) {
        super(maxCapacity, DEFAULT_LOAD_FACTOR, true);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        return size() > maxCapacity;
    }

    @Override
    public void setCapacity(int size) {
        this.maxCapacity = size;
    }
}
