package com.openvehicletracking.restapi.controller;

import com.openvehicletracking.restapi.model.DeviceMessage;
import com.openvehicletracking.restapi.repository.DeviceMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

    private final DeviceMessageRepository deviceMessageRepository;

    @Autowired
    public MessagesController(DeviceMessageRepository deviceMessageRepository) {
        this.deviceMessageRepository = deviceMessageRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "messages")
    public Iterable<DeviceMessage> getAll() {
        return deviceMessageRepository.findAll();
    }
}
