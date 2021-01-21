package com.zjh.rabbit.producer;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * $Test
 *
 * @author zhaojh
 * @date 2021/1/21 9:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @org.junit.Test
    public void test() {
        for (int i = 0; i < 20; i++) {
            System.out.println("i--------------------------->" + i);
            for (int j = 10; j >= 0; j--) {
                if (j == i) {
                    System.out.println("j----->" + j);
                    break;
                }
                System.out.println("j----->" + j);
            }
        }
    }
}
