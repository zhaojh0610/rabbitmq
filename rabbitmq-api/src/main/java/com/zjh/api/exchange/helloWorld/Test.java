package com.zjh.api.exchange.helloWorld;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhaojh
 * @date 2021/1/6 20:40
 * @desc
 */
public class Test {
    public static void main(String[] args) {
//        method(null);
        test1("1,2,2,3,4,5");
    }

    public static void method(String param) {
        switch (param) {
            // 肯定不是进入这里
            case "sth":
                System.out.println("it's sth");
                break;
            // 也不是进入这里
            case "null":
                System.out.println("it's null");
                break;
            // 也不是进入这里
            default:
                System.out.println("default");
        }
    }

    public static void test1(String var) {
        String[] arry = new String[]{var};
        List list = Arrays.asList(arry);
    }
}
