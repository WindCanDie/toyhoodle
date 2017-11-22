package com.toy.cache;

@FunctionalInterface
public interface Source<K, V> {
    V getData(K k);
}
