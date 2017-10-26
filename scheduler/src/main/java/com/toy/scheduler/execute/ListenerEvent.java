package com.toy.scheduler.execute;

/**
 * Created by Administrator on
 * 2017/9/21.
 */
public interface ListenerEvent {
    final class ListenerTaskStart implements ListenerEvent {
    }

    final class ListenerJobStart implements ListenerEvent {
    }
}
