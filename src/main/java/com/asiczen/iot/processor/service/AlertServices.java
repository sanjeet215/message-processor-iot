package com.asiczen.iot.processor.service;

import com.asiczen.iot.processor.model.OrganizationView;
import org.springframework.stereotype.Service;

import com.asiczen.iot.processor.model.TransformedMessage;

@Service
public interface AlertServices {

	public void checkForSpeed(TransformedMessage message, OrganizationView organizationView);

	public void checkForFuel(TransformedMessage message,TransformedMessage oldmessage, OrganizationView organizationView);

	public void engineOnOffVehicle(TransformedMessage message,TransformedMessage oldmessage);

	public void geoFenceViolation(TransformedMessage message);
	
	public void engineOnVehicleNotMovingCheck(TransformedMessage message,TransformedMessage oldmessage);

	public void setDelayedMessageFlag(TransformedMessage message,TransformedMessage oldmessage);
}
