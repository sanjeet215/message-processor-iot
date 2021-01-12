package com.asiczen.iot.processor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asiczen.iot.processor.model.TransformedMessage;
import com.asiczen.iot.processor.repository.RedisTransformedMessageRepository;
import com.asiczen.iot.processor.service.DailyVehicleStatusReset;
import com.asiczen.iot.processor.service.MessagePublisherService;

@Component
public class DailyVehicleStatusResetImpl implements DailyVehicleStatusReset {

	@Autowired
	RedisTransformedMessageRepository redisRepo;

	@Autowired
	MessagePublisherService publishService;

	@Override
	public void resetVehicleStatus() {

		redisRepo.getAllVehicleData().parallelStream().forEach(record -> {
			publishService.publishEodMessages(record);
			resetRecord(record);
			redisRepo.save(record);
		});
	}

	private void resetRecord(TransformedMessage record) {
		record.setSpeed(0);
		record.setCalculatedDailyDistance(0d);
		record.setAverageSpeed(0d);
		record.setCalculatedSpeed(0d);
		record.setCalulatedDistance(0d);
		record.setTopSpeed(0);

		record.setAlertFlag(false);
		record.setCurrentFlag(false);
		record.setIdleEngineOff(false);
		record.setIdleEngineOn(false);
		record.setVehicleMovingFlag(false);

		record.setIdleKeyOffTime(0);
		record.setIdleKeyOnTime(0);
		record.setGeoViolation(false);
		record.setMessageCounter(0);
		record.setVehicleMovingTime(0);

	}

}
