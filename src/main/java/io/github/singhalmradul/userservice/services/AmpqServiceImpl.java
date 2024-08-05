package io.github.singhalmradul.userservice.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import io.github.singhalmradul.userservice.events.UserAccountCreationEvent;
import io.github.singhalmradul.userservice.model.UserAccountDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AmpqServiceImpl implements AmpqService {

	private final ApplicationEventPublisher eventPublisher;

	@RabbitListener(queues = QUEUE_NAME)
	@Override
	public void receiveMessage(@Payload UserAccountDetails accountDetails) {

		log.info("Received message: {}", accountDetails);

		eventPublisher.publishEvent(new UserAccountCreationEvent(accountDetails));
	}

}
