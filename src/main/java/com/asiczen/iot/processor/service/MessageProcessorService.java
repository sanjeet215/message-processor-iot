package com.asiczen.iot.processor.service;

import org.springframework.stereotype.Service;
import com.asiczen.iot.processor.model.OriginalMessage;

@Service
public interface MessageProcessorService {

	public void processMessages(OriginalMessage message);

}
