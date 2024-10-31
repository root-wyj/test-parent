package com.wyj.test.interview;

/**
 * 最长回文子串
 * aaabccbabb
 * abccba
 */
public class B {


    public static String fun(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }

        String maxSubStr = "";

        for (int i=1; i<str.length()-1; i++) {
            // 以i为中心去找
            String tmp = subStr(str, i-1, i+1);
            if (tmp.length() > maxSubStr.length()) {
                maxSubStr = tmp;
            }
            // str[i]==str[i+1] 以i,i+1为中心去找
            if (str.charAt(i) == str.charAt(i+1)) {
                tmp = subStr(str, i, i+1);
                if (tmp.length() > maxSubStr.length()) {
                    maxSubStr = tmp;
                }
            }
        }

        return maxSubStr;
    }

    private static String subStr(String str, int start, int end) {
        while (start >= 0 && end < str.length() && str.charAt(start) == str.charAt(end)) {
            start--;
            end++;
        }
        return str.substring(start+1, end);
    }

    public static void main(String[] args) {
        System.out.println(fun("aaabccbabb"));
        System.out.println(fun("aaabcbabb"));
    }



}
