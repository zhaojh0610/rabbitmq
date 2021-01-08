package com.zjh.rabbit.producer;

import com.zjh.rabbit.producer.component.RabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaojh
 * @date 2021/1/8 18:19
 * @desc
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private RabbitSender rabbitSender;

    @Test
    public void testSender() throws Exception {
        Map map = new HashMap();
        map.put("arrt1", "12345");
        map.put("arrt2", "asdfg");
        rabbitSender.sender("hello rabbit", map);
    }
}
