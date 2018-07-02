package com.openvehicletracking.restapi.controller;

import com.openvehicletracking.core.DeviceState;
import com.openvehicletracking.restapi.repository.DeviceStateRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/test-resource")
public class HelloController {



    private final DeviceStateRedisTemplate deviceStateRedisTemplate;

    @Autowired
    public HelloController(DeviceStateRedisTemplate deviceStateRedisTemplate) {
        this.deviceStateRedisTemplate = deviceStateRedisTemplate;
    }

    @RequestMapping(path = "/hello/{name}", method = RequestMethod.GET)
    public Map sayHello(@PathVariable String name) {
        Map<String, String> m = new HashMap<>();
        m.put("hello", name);
        return m;
    }


    @RequestMapping(path = "/redis/{key}", method = RequestMethod.GET)
    public DeviceState test(@PathVariable(name = "key") String redisKey) {
        return deviceStateRedisTemplate.boundValueOps(redisKey).get();
    }

}
