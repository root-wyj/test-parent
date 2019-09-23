package com.wyj.shorturl;

import lombok.extern.slf4j.Slf4j;

/**
 * 经测试 最佳 长度是 46bit 39bit 时间戳=34年 3bit random 4bit seq
 * Author: wyj
 * Date: 2019/9/11
 */
@Slf4j
public class SimpleSnowId {

    private static final int timestampLength = 39;
    public static final int REMAIN_BIT= 7;
    public static final int DEFAULT_DEVICE_BIT = 3;

    private int deviceLength;
    private int seqLength;

    private long lastTimestamp = System.currentTimeMillis();
    private long seq = 0;
    private long deviceId;

    public SimpleSnowId(int deviceBit, int deviceId) {
        this.deviceId = deviceId;
        if (deviceBit >= REMAIN_BIT) {
            throw new RuntimeException("deviceId 最多只能占"+(REMAIN_BIT-1)+"bit");
        }
        this.deviceLength = deviceBit;
        this.seqLength = REMAIN_BIT - deviceLength;
    }

    private static final String redisHashKey = "deviceMap";

    private void generateDeviceId() {
    }

    /**
     * 长度是 46bit 39bit 时间戳=34年 3bit random 4bit seq
     * @return
     */
    public synchronized long nextId() {
        // seq 一定在 timestamp 之前
        long seq = getSeq();
        lastTimestamp = getTimestamp();

        return ((lastTimestamp << (deviceLength + seqLength))
                | (deviceId << seqLength)
                | seq);

    }

    private long getTimestamp() {
        // 取 timestampLength 长度的 时间戳，再最高位置0
        return (System.currentTimeMillis() << (64-timestampLength) >>> (64-timestampLength));
    }

    private long getSeq() {
        if (getTimestamp() == lastTimestamp) {
            seq ++;
            if (seq >= (1 << seqLength)) {
                for (;;) {
                    if (getTimestamp() != lastTimestamp) {
                        seq = 0;
                        break;
                    }
                }
            }
        }
        return seq;
    }

    /**
     * 长度8 的 str
     * @return
     */
    public String nextUniqueStr() {
        long num = nextId();

        //创建数组，32位
        String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
                "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
                "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
                "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
                "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
                "U" , "V" , "W" , "X" , "Y" , "Z"

        };

        int radix = chars.length;

        //指针，从数组最后开始
        int pos = 32;
        StringBuffer result = new StringBuffer();

        //开始循环计算num和radix的商和余数
        while(num > 0)
        {
            result.append(chars[(int)(num % radix)]);
            num /= radix;

            /*
             * 这里是针对二进制、八进制和十六进制进行的移位运算
            arr[--pos] = ch[num&(radix-1)];
            if(radix == 2)
                num >>= 1;
            else if(radix == 8)
                num >>= 3;
            else if(radix == 16)
                num >>= 4;
            */
        }

        return result.reverse().toString();
    }

}
