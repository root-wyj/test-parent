package com.wyj.test.jvm;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created
 * Author: wyj
 * Date: 2019/12/2
 */
public class TLeeCode {

    public static int[] _1_sum(int[] nums, int target) {

        int[] ret={-1, -1};

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i=0; i<nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i=0; i<nums.length; i++) {
            Integer integer = map.get(target - nums[i]);
            if (integer!=null && integer!=i) {
                ret[0] = i;
                ret[1] = integer;
                return ret;
            }
        }

        return ret;
    }

    public static void main(String[] args) {
//        int[] a = {3,2,4};
//        System.out.println(Arrays.toString(_1_sum(a, 6)));

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"-111:"+Thread.currentThread().getState());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        System.out.println(Thread.currentThread().getName()+"-222:"+Thread.currentThread().getState());
        System.out.println(t1.getName()+"-333:"+t1.getState());

    }


}
