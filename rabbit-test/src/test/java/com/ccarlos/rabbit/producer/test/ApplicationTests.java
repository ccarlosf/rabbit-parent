package com.ccarlos.rabbit.producer.test;

import com.ccarlos.rabbit.api.Message;
import com.ccarlos.rabbit.api.MessageType;
import com.ccarlos.rabbit.producer.broker.ProducerClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private ProducerClient producerClient;

    @Test
    public void testProducerClient() throws Exception {

        for (int i = 0; i < 1; i++) {
            String uuiqueId = UUID.randomUUID().toString();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "张三");
            attributes.put("age", "18");
            Message message = new Message(
                    uuiqueId,
                    "exchange-1234567",
                    "springboot.abc",
                    attributes,
                    0
            );
            message.setMessageType(MessageType.RELIANT);
            message.setDelayMills(5000);
            producerClient.send(message);
        }
        Thread.sleep(100000);
    }

    @Test
    public void testProducerClient2() throws Exception {

        for (int i = 0; i < 1; i++) {
            String uuiqueId = UUID.randomUUID().toString();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "张三");
            attributes.put("age", "18");
            Message message = new Message(
                    uuiqueId,
                    "delay-exchange",
                    "delay.abc",
                    attributes,
                    15000
            );
            message.setMessageType(MessageType.RELIANT);
            message.setDelayMills(5000);
            producerClient.send(message);
        }
        Thread.sleep(100000);
    }
}

