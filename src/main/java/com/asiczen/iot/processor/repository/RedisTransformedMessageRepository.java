package com.asiczen.iot.processor.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.asiczen.iot.processor.model.TransformedMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class RedisTransformedMessageRepository {

	private static final String KEY = "LASTVINFO";

	private HashOperations<String, String, TransformedMessage> hashOperations;

	private RedisTemplate<String, TransformedMessage> redisTemplate;

	public RedisTransformedMessageRepository(RedisTemplate<String, TransformedMessage> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.hashOperations = this.redisTemplate.opsForHash();
	}

	public void save(TransformedMessage covertedMessage) {
		hashOperations.put(KEY, covertedMessage.getVehicleNumber(), covertedMessage);
	}

	public TransformedMessage get(String vehicleNumber) {

		log.trace("Looking for vehicle Number ", vehicleNumber);
		ObjectMapper objMapper = new ObjectMapper();
		log.trace("Message converted successfully");

		return objMapper.convertValue(hashOperations.get(KEY, vehicleNumber), TransformedMessage.class);
	}

	// Method is to reset all vehicle data.
	public List<TransformedMessage> getAllVehicleData() {

		ObjectMapper objMapper = new ObjectMapper();

		return hashOperations.keys(KEY).parallelStream().map(item -> objMapper.convertValue(item, String.class))
				.collect(Collectors.toList()).stream().map(this::get).collect(Collectors.toList());
	}
}
