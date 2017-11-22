package com.toy.cache.history;

/**
 * 记录有效时长内的历史请求次数
 * @param <K>
 */
public interface History<K> {
    boolean putAndisCache(K key);

    int putAndCount(K key);

    boolean clear();
}
