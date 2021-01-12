package com.asiczen.iot.processor.repository;

import com.asiczen.iot.processor.model.OrganizationView;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
public class RedisOrganizationParametersRepository {

    private final String KEY = "ORGPARAMETERS";

    private HashOperations<String, String, OrganizationView> hashOperations;

    private RedisTemplate<String, OrganizationView> redisTemplate;

    public RedisOrganizationParametersRepository(RedisTemplate<String, OrganizationView> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
    }

    public void save(OrganizationView organizationView) {
        hashOperations.put(KEY, String.valueOf(organizationView.getOrgName()), organizationView);
    }

    public Optional<OrganizationView> getOrganizationInformation(String orgRefName) {
        ObjectMapper orgMapper = new ObjectMapper();
        if(!orgRefName.isEmpty()){
            OrganizationView organization =  orgMapper.convertValue(hashOperations.get(KEY, orgRefName), OrganizationView.class);
            return Optional.of(organization);
        } else {
            log.error("Organization info can't be retrieved. Please check...");
            return Optional.empty();
        }

    }
}
