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
        Date fromDate, toDate;
        if (messageRequestDTO.fromDate().isPresent() && messageRequestDTO.toDate().isPresent()) {
            fromDate = messageRequestDTO.fromDate().get();
            toDate = messageRequestDTO.toDate().get();
            if (toDate.before(fromDate) || Objects.equals(fromDate.getTime(), toDate.getTime())) {
                throw new InvalidDateRangeException("Invalid date range");
            }
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            toDate = calendar.getTime();

            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            fromDate = calendar.getTime();
        }

        Pageable page;
        if (messageRequestDTO.getLimit() <= 0) {
            page = Pageable.unpaged();
        } else {
            page = PageRequest.of(0, messageRequestDTO.getLimit(), messageRequestDTO.getSortDirection());
        }

        return messageRepository.findLocationMessages(messageRequestDTO.getDeviceId(), fromDate.getTime(), toDate.getTime(), page);
    }
}
