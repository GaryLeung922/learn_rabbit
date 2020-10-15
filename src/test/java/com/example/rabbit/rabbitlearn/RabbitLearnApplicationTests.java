package com.example.rabbit.rabbitlearn;

import com.example.rabbit.rabbitlearn.configuration.RabbitConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest
class RabbitLearnApplicationTests {

    @Test
    void contextLoads() {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(RabbitConfiguration.class);
        AmqpTemplate template = context.getBean(AmqpTemplate.class);
        Message foo = template.receive("queue_up_iot-platform-app-demo3");
        System.out.println(foo);
    }

}
