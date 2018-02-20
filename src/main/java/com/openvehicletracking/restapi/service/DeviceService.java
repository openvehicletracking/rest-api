package com.openvehicletracking.restapi.service;

import com.openvehicletracking.restapi.model.DeviceMessage;
import com.openvehicletracking.restapi.model.dto.device.MessageRequestDTO;
import com.openvehicletracking.restapi.repository.DeviceMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceMessageRepository messageRepository;

    @Autowired
    public DeviceService(DeviceMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Iterable<DeviceMessage> getMessages(MessageRequestDTO messageRequestDTO) {
        return messageRepository.findByDeviceId(messageRequestDTO.getDeviceId(), messageRequestDTO.getSortDirection());
    }
}
