package com.example.rabbit.rabbitlearn.example;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @Author: liangjiaqi
 * @Date: 2020/9/19 11:05 AM
 */
public class Consumer {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws InterruptedException {
        boolean autoAck = false;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("basic.gz.cvte.cn");
        factory.setPort(5682);
        factory.setUsername("maxhub");
        factory.setPassword("maxhub");
        factory.setVirtualHost("maxhub_vhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            System.out.println(declareOk);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(QUEUE_NAME, false, "2",new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body)
                        throws IOException
                {
                    System.out.println(new String(body));
                    long deliveryTag = envelope.getDeliveryTag();
                    // positively acknowledge a single delivery, the message will
                    // be discarded
                    channel.basicAck(deliveryTag, false);
                }
            });

        }catch (Exception e){

        }
        Thread.sleep(100000000);
    }

}
