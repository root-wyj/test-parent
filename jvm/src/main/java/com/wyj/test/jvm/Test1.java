package com.wyj.test.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created
 * Author: wyj
 * Date: 2019/10/9
 */
public class Test1 {

    /**
     * -Xmx1024m -Xms1024m -XX:NewRatio=4 -XX:SurvivorRatio=6 -XX:+PrintGCDetails -Xloggc:./logs_gc.log
     */
    public static void main(String[] args) {
        List<Object> objects = new ArrayList<>();
        while(true) {
            Object o = new byte[1024*1024];
        }
    }

}
