package com.asiczen.iot.processor.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import com.asiczen.iot.processor.channels.ConsumerChannel;
import com.asiczen.iot.processor.model.OriginalMessage;
import com.asiczen.iot.processor.service.MessageProcessorService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@EnableBinding(ConsumerChannel.class)
public class MessageConsumer {

	@Autowired
	MessageProcessorService service;

	@StreamListener(value = ConsumerChannel.IOT_MESSAGE_INPUTCHANNEL)
	public void receivedMessage(OriginalMessage message) {

		service.processMessages(message);

		log.info(message.toString());
	}

}
