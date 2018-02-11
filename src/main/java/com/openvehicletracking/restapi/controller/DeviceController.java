package com.openvehicletracking.restapi.controller;

import com.openvehicletracking.restapi.model.DeviceMessage;
import com.openvehicletracking.restapi.repository.DeviceMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceMessageRepository deviceMessageRepository;

    @Autowired
    public DeviceController(DeviceMessageRepository deviceMessageRepository) {
        this.deviceMessageRepository = deviceMessageRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{deviceId}/messages")
    @PreAuthorize("@deviceAuthorityChecker.check(authentication.authorities, #deviceId)")
    public Iterable<DeviceMessage> getAll(@PathVariable String deviceId) {

        return deviceMessageRepository.findByDeviceId(deviceId, Sort.by(Sort.Direction.DESC, "datetime"));
    }
}
