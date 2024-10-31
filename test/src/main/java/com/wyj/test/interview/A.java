package com.wyj.test.interview;

/*
数组中是1-7的任意数字，随机组合排列，1-7分别代表星期一到星期日。
要求：如果日期是连续的，则输出xx至yy生效，否则输出 xx/yy/zz生效
eg.[1,3,2,4,1] 输出【星期一至星期四生效】
eg.[1,3,5] 输出【星期一/星期三/星期五生效】
*/

import java.util.*;

public class A {

    static Map<Integer, String> printMap = new HashMap();
    static {
        printMap.put(1, "星期一");
        printMap.put(2, "星期二");
        printMap.put(3, "星期三");
        printMap.put(4, "星期四");
        printMap.put(5, "星期五");
        printMap.put(6, "星期六");
        printMap.put(7, "星期日");
    }

    public static void print(int[] array) {
        Set<Integer> set = new HashSet<>();
        for (int i=0; i<array.length; i++) {
            set.add(array[i]);
        }
        int min = array[0];
        int max = array[0];
        for (Integer tmp : set) {
            min = Math.min(min, tmp);
            max = Math.max(max, tmp);
        }


        if(max - min + 1 == set.size()) {
            // 连续
            System.out.println(printMap.get(min) + "至" + printMap.get(max) + "生效");
        } else {
            // 不连续
            StringBuilder str = new StringBuilder();
            for (int i=min; i<=max; i++) {
                if (set.contains(i)) {
                    str.append(printMap.get(i)).append("/");
                }
            }
            str.deleteCharAt(str.length()-1);
            str.append("生效");
            System.out.println(str.toString());
        }

    }

    public static void main(String[] args) {
        int[] array = {1,3,2,4,1};
        print(array);
        int[] array2 = {1,3,5};
        print(array2);
    }

}
