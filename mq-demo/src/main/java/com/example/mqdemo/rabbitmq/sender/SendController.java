package com.example.mqdemo.rabbitmq.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SendController {

    @Resource(name = "hqRabbitTemplate")
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("/send")
    public void send() throws JsonProcessingException {
        Map<String,String> map = new HashMap<>();
        map.put("UPDATE_TIME","20220317");
        map.put("TABLE","PM_PROJECT_INFO");
        map.put("MOF_DIV_CODE","360000000");
        rabbitTemplate.convertAndSend("proinfo_topic","topic.message", objectMapper.writeValueAsString(map));
    }
}
