package com.bikerackapp.bike_rack_service.service;

import com.bikerackapp.bike_rack_service.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    private final BikeRackService bikeRackService;

    public MessageConsumer(BikeRackService bikeRackService) {
        this.bikeRackService = bikeRackService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        bikeRackService.processMessage(message);
    }
}