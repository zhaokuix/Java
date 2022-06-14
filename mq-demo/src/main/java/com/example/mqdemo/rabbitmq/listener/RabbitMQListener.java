//package com.example.mqdemo.rabbitmq.listener;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.ExchangeTypes;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@RabbitListener(
//        bindings = {
//                @QueueBinding(
//                        value = @Queue(value = "${hq.queueName:proinfo_topic}"),
//                        exchange = @Exchange(value = "${hq.exchangeName:Pm_Platform}", type = ExchangeTypes.TOPIC)
//
//                )
//        },
//        ackMode = "AUTO",
//        containerFactory = "hqConsumerContainer")
//@Slf4j
//public class RabbitMQListener {
//
//    @Value("${hq.exchangeName:Pm_Platform}")
//    private String exchangeName;
//
//    @Value("${hq.queueName:proinfo_topic}")
//    private String queueName;
//
//    @Value("${hq.tableName:PM_PROJECT_INFO}")
//    private String tableName;
//
//    @RabbitHandler
//    public void process(Object testMessage) {
//        String body = new String(((Message) testMessage).getBody());
//        log.info("exchange = {}, queue = {},监听到的消息为：{}",exchangeName, queueName, body);
//    }
//
//}
