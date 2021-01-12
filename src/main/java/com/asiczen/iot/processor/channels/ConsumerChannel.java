package com.asiczen.iot.processor.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ConsumerChannel {

	String IOT_MESSAGE_INPUTCHANNEL = "original-message";

	@Input(ConsumerChannel.IOT_MESSAGE_INPUTCHANNEL)
	SubscribableChannel messageInputChannel();
}
