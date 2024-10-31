package com.wyj.test.test;

public class TestBitCompute {

    public static void main(String[] args) {
        zuoyi(4);
        youyi(-4);

        wufuhaoyouyi(4);
        wufuhaoyouyi(-4);
    }

    /**
     * 无符号 右移
     */
    private static void wufuhaoyouyi(int tmp) {
        System.out.println("无符号 右移:" + tmp);
        int i=tmp;
        System.out.printf("%-10d %32s\n", i, Integer.toBinaryString(i));
        // 无符号 右移
        i >>>= 1;
        System.out.printf("%-10d %32s\n", i, Integer.toBinaryString(i));

    }

    private static void youyi(int tmp) {
        System.out.println("右移:" + tmp);
        int i=tmp;
        System.out.printf("%-10d %32s\n", i, Integer.toBinaryString(i));
        // 无符号 右移
        i >>= 1;
        System.out.printf("%-10d %32s\n", i, Integer.toBinaryString(i));

    }

    private static void zuoyi(int tmp) {
        System.out.println("左移:" + tmp);
        int i=tmp;
        System.out.printf("%-10d %32s\n", i, Integer.toBinaryString(i));
        // 无符号 右移
        i <<= 1;
        System.out.printf("%-10d %32s\n", i, Integer.toBinaryString(i));

    }
}
