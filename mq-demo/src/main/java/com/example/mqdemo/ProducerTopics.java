package com.example.mqdemo;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProducerTopics {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1. 创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2. 设置参数
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("testhost");
        //3. 创建连接
        Connection connection = factory.newConnection();
        //4. 创建管道
        Channel channel = connection.createChannel();
        //5. 创建队列
        String queueName1 = "proinfo_topic1";
        String queueName2 = "proinfo_topic";
        channel.queueDeclare(queueName1,true,false,false,null);
        channel.queueDeclare(queueName2,true,false,false,null);
        //6. 创建交换机
        String  exchangeName = "Pm_Platform";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC,true,false,false,null);
        //7. 绑定队列和交换机
        /*
        queueBind(String queue, String exchange, String routingKey)
        参数：
            1. queue：队列名称
            2. exchange：交换机名称
            3. routingKey：路由键，绑定规则
                如果交换机的类型为fanout ，routingKey设置为""
         */

        // routing key  系统的名称.日志的级别。
        //需求： 所有error级别的日志存入数据库，所有order系统的日志存入数据库
        channel.queueBind(queueName1, exchangeName, "#.error");
        channel.queueBind(queueName2, exchangeName, "order.*");


        //8. 发送消息
        String body = "日志信息：张三调用了findAll方法...日志级别：info...";
        channel.basicPublish(exchangeName, "order.ss", null, body.getBytes());

        //9. 释放资源
        channel.close();
        connection.close();
    }
}
