package com.bikerackapp.bike_rack_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @RabbitListener(queues = "bikeRackQueue")
    public void receiveMessage(String message) {
        // todo process the message
        System.out.println("Received message: " + message);
    }
}