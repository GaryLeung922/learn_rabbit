package com.example.rabbit.rabbitlearn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * @Author: liangjiaqi
 * @Date: 2020/6/16 1:38 PM
 */
public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("basic.gz.cvte.cn");
        factory.setPort(5682);
        factory.setUsername("maxhub");
        factory.setPassword("maxhub");
        factory.setVirtualHost("maxhub_vhost");

        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            channel.basicNack(delivery.getEnvelope().getDeliveryTag(),false, true);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
