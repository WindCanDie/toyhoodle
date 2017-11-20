package com.toy.cache.history;

import com.toy.cache.StatueException;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;

public class TimeReelHistory<K> implements History<K> {
    private final ConcurrentHashMap<K, Node> hashMap = new ConcurrentHashMap<>();
    private int validTime;
    private int hitNum;
    private ScheduledExecutorService clear;
    private int clearPeriod;

    public TimeReelHistory() {
        this(500, 3, 1);
    }

    public TimeReelHistory(int validTime, int hitNum, int clearPeriod) {
        this.validTime = validTime;
        this.hitNum = hitNum;
        this.clearPeriod = clearPeriod;
        this.clear = Executors.newScheduledThreadPool(1, r -> {
            Thread thread = new Thread(r, "HistoryClear");
            thread.setDaemon(true);
            return thread;
        });
        init();
    }

    private void init() {
        clear.scheduleWithFixedDelay(() -> {
            Set<K> keySet = hashMap.keySet();
            long newTime = System.currentTimeMillis();
            for (K key : keySet) {
                Node node = hashMap.get(key);
                if (node != null) {
                    synchronized (node) {
                        try {
                            if (newTime - node.getEffectiveTime(newTime) > validTime) {
                                node.remove();
                            }
                        } catch (StatueException ignored) {
                        }
                        hashMap.remove(key);
                    }
                }
            }
        }, 1, clearPeriod, TimeUnit.SECONDS);
    }

    private Node getNode(K key) {
        Node node;
        synchronized (hashMap) {
            node = hashMap.get(key);
            if (node == null) {
                node = new Node();
                hashMap.put(key, node);
            }
        }
        return node;
    }

    @Override
    public boolean putAndisCache(K key) {
        Node node = getNode(key);
        boolean v;
        synchronized (node) {
            try {
                v = node.putAndisCache();
            } catch (StatueException e) {
                v = putAndisCache(key);
            }
        }
        return v;
    }

    @Override
    public int putAndCount(K key) {
        Node node = getNode(key);
        int v;
        synchronized (node) {
            try {
                v = node.addAndgetCount();
            } catch (StatueException e) {
                v = putAndCount(key);
            }
        }
        return v;
    }

    @Override
    public boolean clear() {
        synchronized (hashMap) {
            hashMap.clear();
        }
        return true;
    }

    private class Node {
        private long lastValidTime;
        private Queue<Long> visitTimeQueue = new ConcurrentLinkedQueue<>();
        private boolean remove = false;

        Node() {
            lastValidTime = System.currentTimeMillis();
        }

        void remove() {
            remove = true;
        }

        private void remveoeExamine() throws StatueException {
            if (remove) {
                throw new StatueException("node remove");
            }
        }

        long getEffectiveTime(long newTime) throws StatueException {
            remveoeExamine();
            while (true) {
                if (newTime - lastValidTime > validTime) {
                    Long time = visitTimeQueue.poll();
                    if (time == null) {
                        break;
                    }
                    if (newTime - time < validTime) {
                        lastValidTime = time;
                        break;
                    }
                }
            }
            return lastValidTime;
        }

        int addAndgetCount() throws StatueException {
            remveoeExamine();
            long newTime = System.currentTimeMillis();
            visitTimeQueue.add(newTime);
            if (newTime - lastValidTime > validTime) {
                getEffectiveTime(newTime);
            }
            return visitTimeQueue.size();
        }

        boolean putAndisCache() throws StatueException {
            remveoeExamine();
            long newTime = System.currentTimeMillis();
            visitTimeQueue.add(newTime);
            if (newTime - lastValidTime > validTime) {
                getEffectiveTime(newTime);
            }
            return visitTimeQueue.size() > hitNum;
        }
    }
}
