package com.asiczen.iot.processor.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.asiczen.iot.processor.model.VehicleInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

// Imei number is mapped as the key

@Repository
@Slf4j
public class RedisVehicleInfoRepository {

	private static final String KEY = "VINFO";

	private HashOperations<String, String, VehicleInfo> hashOperations;

	private RedisTemplate<String, VehicleInfo> redisTemplate;

	public RedisVehicleInfoRepository(RedisTemplate<String, VehicleInfo> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.hashOperations = this.redisTemplate.opsForHash();
	}

	public void save(VehicleInfo vehicleInfo) {
		hashOperations.put(KEY, vehicleInfo.getImei(), vehicleInfo);
	}

	public VehicleInfo getVehicleinfo(String imei) {

		log.trace("Looking for key/imei {} ", imei);

		ObjectMapper tstmapper = new ObjectMapper();
		VehicleInfo testingo = tstmapper.convertValue(hashOperations.get(KEY, imei), VehicleInfo.class);

		log.trace("Message converted successfully");

		return testingo;
	}

}
