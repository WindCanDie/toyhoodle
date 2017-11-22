package com.toy.cache;

import com.toy.cache.lru.LRULinkedHashMap;
import com.toy.cache.lru.Lru;

public class LRUCache<K, V> implements Cache<K, V> {
    private Source<K, V> source;
    private Lru lru;
    private int timeout = 500;

    public LRUCache(Source<K, V> source, int maxCapacity) {
        this.source = source;
        this.lru = new LRULinkedHashMap(maxCapacity);
    }


    @Override
    public int size() {
        return lru.size();
    }

    @Override
    public void clear() {
        lru.clear();
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public void remove(Object key) {

    }

    @Override
    public void setCapacity(int size) {
        this.lru.setCapacity(size);
    }

    @Override
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void setSource(Source<K,V> source) {
        this.source = source;
    }

    private class Node<K, V> {
        final long createTime;
        K key;
        private Node(long createTime) {
            this.createTime = createTime;
        }
    }
}
