package io.github.singhalmradul.userservice.services;

import org.springframework.messaging.handler.annotation.Payload;

import io.github.singhalmradul.userservice.model.UserAccountDetails;

public interface AmpqService {

    String QUEUE_NAME = "regiser-user-queue";
    String EXCHANGE_NAME = "register-user-exchange";
    String ROUTING_KEY = "register-user-routing-key";

    void receiveMessage(@Payload UserAccountDetails accountDetails);

}