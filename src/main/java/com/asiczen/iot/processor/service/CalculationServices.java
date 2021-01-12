package com.asiczen.iot.processor.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.asiczen.iot.processor.model.TransformedMessage;

@Service
public interface CalculationServices {

	public double calculateSpeedInKmHour(double distanceInMeter, int second);

	public double calculateDistanceInKM(TransformedMessage oldMessage, TransformedMessage newMessage);

	public int differenceBetweenTwoTimeStampsInSeconds(Date timeStampFrom, Date timeStampTo);

	public void updateStationaryVehicleCoOrdinates(TransformedMessage transformedMessage,TransformedMessage originalMessage, double distanceInKm);
	
	public void updateCalculatedDailyDistanceAndDistance(TransformedMessage transformedMessage, TransformedMessage oldMessage, double distanceInKm);

	public void checkEngineOnVehicleIdle(TransformedMessage transformedMessage, TransformedMessage oldMessage, double distanceInKm);
	
	public void checkEngineOffAndVehicleNotMoving(TransformedMessage transformedMessage, TransformedMessage oldMessage, double distanceInKm);
	
	public void checkEngineOnAndVehicleMovingTime(TransformedMessage transformedMessage, TransformedMessage oldMessage, double distanceInKm);

	public void checkForTopSpeed(TransformedMessage transformedMessage, double oldMaxSpeed,	double currentSpeed);

	public void calculateAvgSpeed(TransformedMessage transformedMessage, double oldAvgSpeed);
	
	public void updateCalculatedSpeed(TransformedMessage transformedMessage,double calculatedSpeed);

}
