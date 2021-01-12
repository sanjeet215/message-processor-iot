package com.asiczen.iot.processor.service.impl;

import com.asiczen.iot.processor.model.OrganizationView;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.asiczen.iot.processor.channels.MessagePublisherChannel;
import com.asiczen.iot.processor.model.AlertMessage;
import com.asiczen.iot.processor.model.AlertType;
import com.asiczen.iot.processor.model.TransformedMessage;
import com.asiczen.iot.processor.service.AlertServices;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableBinding(MessagePublisherChannel.class)
@Slf4j
public class AlertServicesImpl implements AlertServices {

    private static final int FUEL_UPPER_LIMIT = 10;

    @Autowired
    MessagePublisherChannel publisherService;

    @Override
    public void checkForSpeed(TransformedMessage message, OrganizationView organizationView) {
        if (message.getCalculatedSpeed() > organizationView.getOverSpeedLimit()) {
            AlertMessage alertMessage = new AlertMessage();

            alertMessage.setVehicleNumber(message.getVehicleNumber());
            alertMessage.setOrgRefName(message.getOrgRefName());
            alertMessage.setAlertType(AlertType.OVER_SPEED);

            if (message.getDriverName() != null) {
                alertMessage.setDriverName(message.getDriverName());
            }

            message.setSpeedViolation(true);
            message.setAlertFlag(true);

            publisherService.alertMessageChannel().send(MessageBuilder.withPayload(alertMessage).build());
        }
    }

    @Override
    public void checkForFuel(TransformedMessage message, TransformedMessage oldMessage, OrganizationView organizationView) {

        AlertMessage alertMessage = new AlertMessage();

        alertMessage.setVehicleNumber(message.getVehicleNumber());
        alertMessage.setOrgRefName(message.getOrgRefName());
        alertMessage.setAlertType(AlertType.RUNNING_OUT_OF_FUEL);

        if (message.getDriverName() != null) {
            alertMessage.setDriverName(message.getDriverName());
        }

        if (message.getFuel() <= FUEL_UPPER_LIMIT && oldMessage.getFuel() >= FUEL_UPPER_LIMIT) {
            publisherService.alertMessageChannel().send(MessageBuilder.withPayload(alertMessage).build());
            message.setAlertFlag(true);
        }

        if (message.getFuel() <= organizationView.getFuelLimit() && oldMessage.getFuel() >= organizationView.getFuelLimit()) {
            publisherService.alertMessageChannel().send(MessageBuilder.withPayload(alertMessage).build());
            message.setAlertFlag(true);
        }

        if (message.getFuel() == 0) {
            message.setFuel(oldMessage.getFuel());
        }
    }

    @Override
    public void engineOnOffVehicle(TransformedMessage message, TransformedMessage oldmessage) {
        // Alert will be triggered when engine goes off or on

        if (message.isKeyOn() != oldmessage.isKeyOn()) {

            AlertMessage alertMessage = new AlertMessage();

            alertMessage.setVehicleNumber(message.getVehicleNumber());
            alertMessage.setOrgRefName(message.getOrgRefName());
            alertMessage.setAlertType(message.isKeyOn() ? AlertType.ENGINE_ON : AlertType.ENGINE_OFF);

            if (message.getDriverName() != null) {
                alertMessage.setDriverName(message.getDriverName());
            }
            publisherService.alertMessageChannel().send(MessageBuilder.withPayload(alertMessage).build());
            message.setAlertFlag(true);

        }

    }

    @Override
    public void geoFenceViolation(TransformedMessage message) {
        // TODO Logic tobe implemented...
    }

    @Override
    public void engineOnVehicleNotMovingCheck(TransformedMessage message, TransformedMessage oldmessage) {

        AlertMessage alertMessage = new AlertMessage();
        alertMessage.setVehicleNumber(message.getVehicleNumber());
        alertMessage.setOrgRefName(message.getOrgRefName());
        if (message.getDriverName() != null) {
            alertMessage.setDriverName(message.getDriverName());
        }

        if ((oldmessage.getIdleKeyOnTime() != message.getIdleKeyOnTime()) && message.getIdleKeyOnTime() > (10 * 60)) {
            alertMessage.setAlertType(AlertType.ENGINE_ON_VEHICLE_NOT_MOVING);
            publisherService.alertMessageChannel().send(MessageBuilder.withPayload(alertMessage).build());
            message.setAlertFlag(true);
            message.setIdleEngineOn(true);
        }
    }

    @Override
    public void setDelayedMessageFlag(TransformedMessage message, TransformedMessage oldmessage) {
        long timeDifference = Math.abs(message.getTimestamp().getTime() - oldmessage.getTimestamp().getTime());
        message.setDelayedMessageFlag((TimeUnit.MILLISECONDS.toSeconds(timeDifference) > 300) ? true : false);
    }

}
