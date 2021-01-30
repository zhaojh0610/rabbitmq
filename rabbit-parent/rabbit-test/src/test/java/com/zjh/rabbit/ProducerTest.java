package com.zjh.rabbit;

import com.zjh.rabbit.api.Message;
import com.zjh.rabbit.api.MessageBuilder;
import com.zjh.rabbit.api.MessageType;
import com.zjh.rabbit.producer.broker.ProducerClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ProducerTest  测试rabbit组件封装
 *
 * @author zhaojh
 * @date 2021/1/28 18:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {

    @Autowired
    private ProducerClient producerClient;

    @Test
    public void testProducerClient() throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            String messageId = UUID.randomUUID().toString();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "张三");
            attributes.put("age", 18);
            Message message = MessageBuilder.create()
                    .withMessageId(messageId)
                    .withAttributes(attributes)
                    .withTopic("exchange-producer-client")
                    .withRoutingKey("springboot.*")
                    .withRoutingKey(MessageType.RELIANT)
                    .withDelayMills(5000)
                    .withMessageType(MessageType.RELIANT)
                    .build();
            producerClient.send(message);
        }
        Thread.sleep(10000);

    }
}
