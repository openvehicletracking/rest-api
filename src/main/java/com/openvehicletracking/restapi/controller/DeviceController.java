package com.openvehicletracking.restapi.controller;

import com.openvehicletracking.restapi.exception.InvalidDateRangeException;
import com.openvehicletracking.restapi.model.DeviceMessage;
import com.openvehicletracking.restapi.model.dto.device.MessageRequestDTO;
import com.openvehicletracking.restapi.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{deviceId}/messages")
    @PreAuthorize("@deviceAuthorityChecker.check(authentication.authorities, #deviceId)")
    public Iterable<DeviceMessage> getAll(@PathVariable String deviceId, MessageRequestDTO messageRequestDTO) throws InvalidDateRangeException {
        messageRequestDTO.setDeviceId(deviceId);
        return deviceService.getLocationMessages(messageRequestDTO);
    }
}
