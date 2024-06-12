package com.bikerackapp.bike_rack_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    static final String queueName = "bikeRackQueue";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }
}