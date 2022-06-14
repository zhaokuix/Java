//package com.example.mqdemo.rabbitmq.sender;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class TopicRabbitConfig {
//    /**
//     * 定义队列名称
//     */
//    final static String message = "topic.message";
//    final static String messages = "topic.messages";
//    final static String proinfo_topic = "proinfo_topic";
//
//    @Bean
//    public Queue queueMessage() {
//        return new Queue(TopicRabbitConfig.message);
//    }
//
//    @Bean
//    public Queue queueMessages() {
//        return new Queue(TopicRabbitConfig.messages);
//    }
//
//    @Bean
//    public Queue queueProInfo() {
//        return new Queue(TopicRabbitConfig.proinfo_topic);
//    }
//
//    /**
//     * 这里的exchange是交换机的名称字符串和发送消息时的名称必须相同
//     * this.rabbitTemplate.convertAndSend("exchange", "topic.1", context);
//     */
//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange("Pm_Platform");
//    }
//
////    /**
////     * @param queueMessage 队列
////     * @param exchange     交换机
////     *                     bindings 绑定交换机队列信息
////     */
////    @Bean
////    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
////        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
////    }
////
////    @Bean
////    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
////        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
////    }
//
//    @Bean
//    Binding bindingExchangeQueueProInfo(Queue queueProInfo, TopicExchange exchange) {
//        return BindingBuilder.bind(queueProInfo).to(exchange).with("topic.#");
//    }
//}
