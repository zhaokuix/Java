
package com.example.mqdemo.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HqListenerConfiguration {
    private final SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Bean(name = "hqConsumerContainer")
    public SimpleRabbitListenerContainerFactory init(@Autowired(required = false) @Qualifier("hqConnectionFactory") ConnectionFactory hqConnectionFactory){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, hqConnectionFactory);
        //手动确认
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //默认消费者数量
        //factory.setConcurrentConsumers(5);
        //最大消费者数量
        //factory.setMaxConcurrentConsumers(10);
        //每次给消费者发送的消息数量
        factory.setPrefetchCount(1);
        return factory;
    }

    @Bean(name = "hqRabbitAdmin")
    public RabbitAdmin hqRabbitAdmin(@Autowired(required = false)
                                       @Qualifier("hqConnectionFactory")
                                               ConnectionFactory hqConnectionFactory) {
        return new RabbitAdmin(hqConnectionFactory);
    }

}

