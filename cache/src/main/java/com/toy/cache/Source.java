package com.toy.cache;

@FunctionalInterface
public interface Source<V> {
    V getData(Object k);
}
