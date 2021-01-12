package com.asiczen.iot.processor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.asiczen.iot.processor.service.DailyVehicleStatusReset;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ScheduleConfig {

	@Autowired
	DailyVehicleStatusReset resetService;

	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")
	public void resetCurrentData() {
		resetService.resetVehicleStatus();
		log.info("Reset Vehicle status is invoked");
	}

}
