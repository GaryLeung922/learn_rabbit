package com.example.rabbit.rabbitlearn.configuration;

import jdk.nashorn.internal.objects.annotations.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @Author: liangjiaqi
 * @Date: 2020/5/25 11:55 AM
 */
@Slf4j
@Configuration
@PropertySource("classpath:/app.properties")
public class RabbitConfiguration {
    @Autowired
    private Environment env;

    @Bean
    public ConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(env.getProperty("rabbitmq.host"));
        cachingConnectionFactory.setPort(Integer.parseInt(env.getProperty("rabbitmq.port")));
        cachingConnectionFactory.setUsername(env.getProperty("rabbitmq.username"));
        cachingConnectionFactory.setPassword(env.getProperty("rabbitmq.password"));
        cachingConnectionFactory.setVirtualHost(env.getProperty("rabbitmq.vhost"));
        log.info("Connecting RabbitMQ ==> {}:{}", cachingConnectionFactory.getHost(), cachingConnectionFactory.getPort());
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(cachingConnectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(cachingConnectionFactory());
        return rabbitTemplate;
    }
}
