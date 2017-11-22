package com.toy.cache.lru;

public interface Lru<K, V> {
    V put(K k, V v);

    V get(Object k);

    V remove(Object k);

    int size();

    void setCapacity(int size);

    void clear();
}
