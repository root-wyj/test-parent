package com.wyj.test.interview;

import java.util.Stack;

/**
 * 加减法计算器 有括号 可嵌套
 * 10-(12+(100 + 50))
 */
public class Compute {

    private static final char OP_MIN = '-';
    private static final char OP_ADD = '+';
    private static final char OP_UNIT_LEFT = '(';
    private static final char OP_UNIT_RIGHT = ')';

    public int caculate(String express) {
        int i=0;
        // 转换为数据 存储到栈
        while (i<express.length()) {
            char c = express.charAt(i);
            int left = 0;
            if (c >= '0' && c <= '9') {
                while ( express.charAt(i) >= '0' && express.charAt(i) <= '9' && i<express.length()) {
                    left = left * 10 + express.charAt(i) - '0';
                    i++;
                }
            } else if (c == OP_ADD){
                return left + caculate(express.substring(i+1));
            } else if (c == OP_MIN) {
                return left - caculate(express.substring(i+1));
            } else if (c == OP_UNIT_LEFT) {
                Stack<Integer> stack = new Stack<>();
                stack.push(i);
                String subExpress = "";
                int nextIndex = i+1;
                while (nextIndex < express.length()) {
                    if (express.charAt(nextIndex) == OP_UNIT_LEFT) {
                        stack.push(nextIndex);
                    } else if (express.charAt(nextIndex) == OP_UNIT_RIGHT) {
                        Integer startIndex = stack.pop();
                        if (stack.isEmpty()) {
                            subExpress = express.substring(startIndex, nextIndex + 1);
                            int caculate = caculate(subExpress);
                            // 再算
                        }
                    }
                }
            }

        }

        return 0;

    }


}
