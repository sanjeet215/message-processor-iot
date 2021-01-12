package com.asiczen.iot.processor.service;

import org.springframework.stereotype.Service;

import com.asiczen.iot.processor.model.TransformedMessage;

@Service
public interface MessagePublisherService {

	public void publishMessages(TransformedMessage message);

	public void publishEodMessages(TransformedMessage message);
}
