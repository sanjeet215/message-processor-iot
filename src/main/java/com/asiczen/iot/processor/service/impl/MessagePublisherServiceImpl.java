package com.asiczen.iot.processor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.asiczen.iot.processor.channels.MessagePublisherChannel;
import com.asiczen.iot.processor.model.TransformedMessage;
import com.asiczen.iot.processor.service.MessagePublisherService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@EnableBinding(MessagePublisherChannel.class)
public class MessagePublisherServiceImpl implements MessagePublisherService {

	@Autowired
    MessagePublisherChannel publisherService;

	@Override
	public void publishMessages(TransformedMessage message) {
		log.info("Before sending to socket: {}",message);
		publisherService.transformedMessageChannel().send(MessageBuilder.withPayload(message).build());
	}

	@Override
	public void publishEodMessages(TransformedMessage message) {
		publisherService.eodMessageChannel().send(MessageBuilder.withPayload(message).build());

	}

}
