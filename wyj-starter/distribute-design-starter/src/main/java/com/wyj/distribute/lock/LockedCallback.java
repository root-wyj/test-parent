package com.wyj.distribute.lock;


@FunctionalInterface
public interface LockedCallback<R> {

    R callback();
}