package com.openvehicletracking.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openvehicletracking.core.DeviceState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/test-resource")
public class HelloController {

    private final StringRedisTemplate redisTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public HelloController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @RequestMapping(path = "/hello/{name}", method = RequestMethod.GET)
    public Map sayHello(@PathVariable String name) {
        Map<String, String> m = new HashMap<>();
        m.put("hello", name);
        return m;
    }


    @RequestMapping(path = "/redis", method = RequestMethod.GET)
    public DeviceState test() {
        try {
            return objectMapper.readValue(redisTemplate.boundValueOps("state_86307101991574012").get(), DeviceState.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
