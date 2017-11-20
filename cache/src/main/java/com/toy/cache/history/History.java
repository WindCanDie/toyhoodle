package com.toy.cache.history;

public interface History<K> {
    boolean putAndisCache(K key);

    int putAndCount(K key);

    boolean clear();
}
