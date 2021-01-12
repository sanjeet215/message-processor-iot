package com.asiczen.iot.processor.service.impl;

import com.asiczen.iot.processor.model.OrganizationView;
import com.asiczen.iot.processor.repository.RedisOrganizationParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asiczen.iot.processor.mapper.OriginalMessageMapper;
import com.asiczen.iot.processor.mapper.VehicleInfoMapper;
import com.asiczen.iot.processor.model.OriginalMessage;
import com.asiczen.iot.processor.model.TransformedMessage;
import com.asiczen.iot.processor.model.VehicleInfo;
import com.asiczen.iot.processor.repository.RedisTransformedMessageRepository;
import com.asiczen.iot.processor.repository.RedisVehicleInfoRepository;
import com.asiczen.iot.processor.service.AlertServices;
import com.asiczen.iot.processor.service.CalculationServices;
import com.asiczen.iot.processor.service.MessageProcessorService;
import com.asiczen.iot.processor.service.MessagePublisherService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MessageProcessorServiceImpl implements MessageProcessorService {

    @Autowired
    RedisVehicleInfoRepository redisRepo;

    @Autowired
    RedisOrganizationParametersRepository organizationRepository;

    @Autowired
    RedisTransformedMessageRepository msgRepo;

    @Autowired
    CalculationServices calcService;

    @Autowired
    MessagePublisherService publisher;

    @Autowired
    AlertServices alertService;

    @Override
    public void processMessages(OriginalMessage incomingMessage) {

        TransformedMessage transformedMessage = new TransformedMessage();
        OriginalMessageMapper.Mapper.updateTransformedMessagewithOrginalMsgData(incomingMessage, transformedMessage);
        transformedMessage.setKeyOn(incomingMessage.isKeyOn());

        VehicleInfo response = getVehicleInfoFromRedis(incomingMessage.getImei());

        if (response != null) {
            VehicleInfoMapper.Mapper.updateVehcileInfoToTransformedMessage(response, transformedMessage);
            transformedMessage.setOrgRefName((response.getOrgRefName() != null) ? response.getOrgRefName().toLowerCase() : "na");
        }

        if (transformedMessage.getVehicleNumber() != null) {
            TransformedMessage oldMessage = msgRepo.get(transformedMessage.getVehicleNumber());

            if (oldMessage != null) {

                transformedMessage.setMessageCounter(oldMessage.getMessageCounter() + 1);
                double distanceInKm = calcService.calculateDistanceInKM(oldMessage, transformedMessage);
                int durationInSecond = Math.abs(calcService.differenceBetweenTwoTimeStampsInSeconds(oldMessage.getTimestamp(), transformedMessage.getTimestamp()));
                double speedInKmPerHour = calcService.calculateSpeedInKmHour((distanceInKm * 1000d), durationInSecond);

                if (distanceInKm <= 50d && speedInKmPerHour <= 200) {
                    calcService.updateCalculatedDailyDistanceAndDistance(transformedMessage, oldMessage, distanceInKm);
                    calcService.updateCalculatedSpeed(transformedMessage, speedInKmPerHour);
                    calcService.updateStationaryVehicleCoOrdinates(transformedMessage, oldMessage, distanceInKm);
                    calcService.checkEngineOnVehicleIdle(transformedMessage, oldMessage, distanceInKm);
                    calcService.calculateAvgSpeed(transformedMessage, oldMessage.getAverageSpeed());
                    calcService.checkForTopSpeed(transformedMessage, oldMessage.getTopSpeed(), speedInKmPerHour);
                    calcService.checkEngineOnVehicleIdle(transformedMessage, oldMessage, distanceInKm);
                    calcService.checkEngineOffAndVehicleNotMoving(transformedMessage, oldMessage, distanceInKm);
                    calcService.checkEngineOnAndVehicleMovingTime(transformedMessage, oldMessage, distanceInKm);

                    //TODO before check may not be required in future , based on requirement this can be removed.
                    if (!transformedMessage.getOrgRefName().equalsIgnoreCase("na")) {
                        OrganizationView organizationView = organizationRepository.getOrganizationInformation(transformedMessage.getOrgRefName()).orElse(new OrganizationView());
                        alertService.engineOnOffVehicle(transformedMessage, oldMessage);
                        alertService.checkForSpeed(transformedMessage, organizationView);
                        alertService.checkForFuel(transformedMessage, oldMessage, organizationView);
                        alertService.engineOnVehicleNotMovingCheck(transformedMessage, oldMessage);
                        alertService.setDelayedMessageFlag(transformedMessage, oldMessage);
                    }


                    msgRepo.save(transformedMessage);
                    publisher.publishMessages(transformedMessage);
                } else {
                    log.warn("Special processing is required.Rejecting the message for now");
                    log.warn(incomingMessage.toString());
                }

            } else {

                transformedMessage.setMessageCounter(1);
                msgRepo.save(transformedMessage);
            }

        } else {
            log.warn("Message is rejected ." + transformedMessage.toString());
        }

        // Get the distance traveled

    }

    private VehicleInfo getVehicleInfoFromRedis(String imei) {
        return redisRepo.getVehicleinfo(imei);
    }
}
