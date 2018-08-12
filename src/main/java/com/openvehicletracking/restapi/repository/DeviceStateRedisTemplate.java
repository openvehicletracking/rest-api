package com.openvehicletracking.restapi.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openvehicletracking.core.DeviceState;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;

public class DeviceStateRedisTemplate extends RedisTemplate<String, DeviceState> {

    private static final String STATE_CACHE_KEY = "state_%s";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public DeviceStateRedisTemplate() {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        DeviceStateSerializer deviceStateSerializer = new DeviceStateSerializer();

        setKeySerializer(stringRedisSerializer);
        setValueSerializer(deviceStateSerializer);
        setHashKeySerializer(stringRedisSerializer);
        setHashValueSerializer(deviceStateSerializer);
    }

    public DeviceStateRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }

    public DeviceState getStateOfDevice(String deviceId) {
        return this.boundValueOps(String.format(STATE_CACHE_KEY, deviceId)).get();
    }

    protected class DeviceStateSerializer implements RedisSerializer<DeviceState> {

        @Override
        public byte[] serialize(DeviceState deviceState) {
            return deviceState.asJson().getBytes();
        }

        @Override
        public DeviceState deserialize(byte[] bytes) {
            try {
                if (bytes != null) {
                    return DeviceStateRedisTemplate.this.objectMapper.readValue(bytes, DeviceState.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }



}
