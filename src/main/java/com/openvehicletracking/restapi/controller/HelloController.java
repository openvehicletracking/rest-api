package com.openvehicletracking.restapi.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @RequestMapping(path = "/hello/{name}", method = RequestMethod.GET)
    public Map sayHello(@PathVariable String name) {
        Map<String, String> m = new HashMap<>();
        m.put("hello", name);
        return m;
    }

}
