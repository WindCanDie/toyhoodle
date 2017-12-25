package com.toy.cache;

import com.toy.cache.lru.LRULinkedHashMap;
import com.toy.cache.lru.Lru;

import java.util.concurrent.Callable;

public class LRUCache<K, V> implements Cache<K, V> {
    private Source<V> source;
    private Lru<K, Node<K, V>> lru;
    private int timeout = 500;

    public LRUCache(Source<V> source, int maxCapacity) {
        this.source = source;
        this.lru = new LRULinkedHashMap<>(maxCapacity);
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
        if (key == null)
            throw new NullPointerException();
        lru.get(key);
        Node node = getNode(key);
        synchronized (node) {
            if (System.currentTimeMillis() - node.createTime > timeout) {
                remove(key);
                get(key);
            }
        }
        return null;
    }

    private void putNode(K key, Node<K, V> node) {
        synchronized (lru) {
            lru.put(key, node);
        }
    }

    private Node<K, V> getNode(Object key) {
        Node<K, V> r = null;
        synchronized (lru) {
            r = lru.get(key);
            if (r == null) {
                Node<K, V> node = new Node(System.currentTimeMillis(), key);
                putNode((K) key, node);
                r = lru.get(key);
            }

        }
        return r;
    }

    private void removeNode(Object key) {
        synchronized (lru) {
            lru.remove(key);
        }
    }

    @Override
    public void remove(Object key) {
        removeNode(key);
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
    public void setSource(Source<V> source) {
        this.source = source;
    }

    private class Node<K, V> {
        final long createTime;
        final K key;
        QueryTask<V> task;

        private Node(long createTime, K key) {
            this.key = key;
            this.createTime = createTime;
            Callable callable = (Callable<V>) () -> (V) source.getData(key);
            this.task = new QueryTask(callable);
        }

        private V getResutl() {
            return task.exe();
        }
    }
}
