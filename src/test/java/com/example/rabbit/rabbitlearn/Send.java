package com.example.rabbit.rabbitlearn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @Author: liangjiaqi
 * @Date: 2020/6/16 1:28 PM
 */
public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("basic.gz.cvte.cn");
        factory.setPort(5682);
        factory.setUsername("maxhub");
        factory.setPassword("maxhub");
        factory.setVirtualHost("maxhub_vhost");
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
