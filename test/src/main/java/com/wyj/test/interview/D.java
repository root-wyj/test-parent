package com.wyj.test.interview;


import java.util.Stack;

/**
 *
 示例 1：

 输入："/home/" 输出："/home" 解释：注意，最后一个目录名后面没有斜杠。

 示例 2：

 输入："/../" 输出："/" 解释：从根目录向上一级是不可行的，因为根是你可以到达的最高级。

 示例 3：

 输入："/a/./b/../../c/" 输出："/c"
 */
public class D {

    private static final String CUR_DIR = ".";
    private static final String PARENT_DIR = "..";

    private static final String DIR_SPILT = "/";

    public static String path(String path) {
        if (!path.startsWith(DIR_SPILT)) {
            throw new RuntimeException("非绝对路径");
        }

        String[] pathArray = path.split(DIR_SPILT);
        Stack<String> dirStack = new Stack<>();
        for (String pathItem : pathArray) {
            if (pathItem.isEmpty()) {
                continue;
            }
            if (CUR_DIR.equals(pathItem)) {
                continue;
            } else if (PARENT_DIR.equals(pathItem)) {
                // 回退到上级目录，根目录的话 不变
                if (dirStack.isEmpty()) {
                    continue;
                } else {
                    dirStack.pop();
                }
            } else {
                // 进入某一目录
                dirStack.push(pathItem);
            }
        }

        StringBuilder absPath = new StringBuilder();
        if (dirStack.isEmpty()) {
            absPath.append(DIR_SPILT);
        } else {
            while (!dirStack.isEmpty()) {
                absPath.append(DIR_SPILT);
                absPath.insert(0, dirStack.pop()).insert(0, DIR_SPILT);
            }
        }

        return absPath.toString();
    }

    public static void main(String[] args) {
        System.out.println(path("/home/"));
        System.out.println(path("/../"));
        System.out.println(path("/a/./b/../../c/"));
    }

}
