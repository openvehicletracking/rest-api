package com.openvehicletracking.restapi.service;

import com.openvehicletracking.restapi.exception.InvalidDateRangeException;
import com.openvehicletracking.restapi.model.DeviceMessage;
import com.openvehicletracking.restapi.model.dto.device.MessageRequestDTO;
import com.openvehicletracking.restapi.repository.DeviceMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
public class DeviceService {

    private final DeviceMessageRepository messageRepository;

    @Autowired
    public DeviceService(DeviceMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Iterable<DeviceMessage> getLocationMessages(MessageRequestDTO messageRequestDTO) throws InvalidDateRangeException {
        Date fromDate = null, toDate = null;
        if (messageRequestDTO.fromDate().isPresent() && messageRequestDTO.toDate().isPresent()) {
            fromDate = messageRequestDTO.fromDate().get();
            toDate = messageRequestDTO.toDate().get();
            if (toDate.before(fromDate) || Objects.equals(fromDate.getTime(), toDate.getTime())) {
                throw new InvalidDateRangeException("Invalid date range");
            }
        }

        Pageable page;
        if (messageRequestDTO.getLimit() <= 0) {
            page = Pageable.unpaged();
        } else {
            page = PageRequest.of(0, messageRequestDTO.getLimit(), messageRequestDTO.getSortDirection());
        }

        if (toDate != null && fromDate != null && messageRequestDTO.getStatus() != null) {
            return messageRepository.findLocationMessages(messageRequestDTO.getDeviceId(), fromDate.getTime(), toDate.getTime(), messageRequestDTO.getStatus(), page);
        } else if (toDate != null && fromDate != null && messageRequestDTO.getStatus() == null) {
            return messageRepository.findValidLocationMessagesByDateRange(messageRequestDTO.getDeviceId(), fromDate.getTime(), toDate.getTime(), page);
        } else if (messageRequestDTO.getStatus() != null) {
            return messageRepository.findLocationMessages(messageRequestDTO.getDeviceId(), messageRequestDTO.getStatus(), page);
        }

        return messageRepository.findValidLocationMessages(messageRequestDTO.getDeviceId(), page);
    }
}
