package com.asiczen.iot.processor.channels;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public interface MessagePublisherChannel {

	public static final String IOT_MESSAGE_OUTPUTCHANNEL = "transformed-messages";
	public static final String IOT_MESSAGE_ALERT_OUTPUTCHANNEL = "alert-message-queue";
	
	public static final String IOT_EOD_MESSAGES = "eod-messages";
	

	@Output(MessagePublisherChannel.IOT_MESSAGE_OUTPUTCHANNEL)
	MessageChannel transformedMessageChannel();

	@Output(MessagePublisherChannel.IOT_MESSAGE_ALERT_OUTPUTCHANNEL)
	MessageChannel alertMessageChannel();

	@Output(MessagePublisherChannel.IOT_EOD_MESSAGES)
	MessageChannel eodMessageChannel();
}
