package com.wyj.test.oom;

/**
 * -Xss=1m 默认就是1M
 * @author wuyingjie <13693653307@163.com>
 * Created on 2021-07-26
 */
public class StackOOM {

    public static void main(String[] args) {
        stackOom();
    }

    private static void stackOom() {
        stackOom();
    }
}
