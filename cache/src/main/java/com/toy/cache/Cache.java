package com.toy.cache;

public interface Cache<K, V> {
    int size();

    void clear();

    V get(Object key);

    void remove(Object key);

    void setCapacity(int size);

    void setTimeout(int timeout);

    void setSource(Source<V> source);
}
