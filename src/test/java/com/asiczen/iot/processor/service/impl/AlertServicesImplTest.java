package com.asiczen.iot.processor.service.impl;

import com.asiczen.iot.processor.model.OrganizationView;
import com.asiczen.iot.processor.model.TransformedMessage;
import com.asiczen.iot.processor.service.AlertServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class AlertServicesImplTest {

    private final String token = "randomtoken";

//    @InjectMocks
//    AlertServicesImpl alertServicesI;


    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void checkForSpeed() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        TransformedMessage transformedMessage = objectMapper.readValue(new File("src/test/resources/AlertServicesImpl/input/TransformedMessage.json"), TransformedMessage.class);
        OrganizationView organizationView = objectMapper.readValue(new File("src/test/resources/AlertServicesImpl/input/OrganizationView.json"), OrganizationView.class);

        Assertions.assertEquals("867584035018865", transformedMessage.getImei());

    }

}
