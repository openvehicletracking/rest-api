package com.openvehicletracking.restapi.service;

import com.openvehicletracking.restapi.exception.InvalidDateRangeException;
import com.openvehicletracking.restapi.model.dto.device.MessageRequestDTO;
import com.openvehicletracking.restapi.repository.DeviceMessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.Date;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.anyLong;

public class DeviceServiceTest {

    @Mock
    DeviceMessageRepository messageRepository;

    @InjectMocks
    DeviceService deviceService;

    private String deviceId;

    @Before
    public void setUp() {
        deviceId = "1234567";
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getLocationMessagesSuccess() throws Exception {
        MessageRequestDTO messageRequestDTO = new MessageRequestDTO();
        messageRequestDTO.setDeviceId(deviceId);
        deviceService.getLocationMessages(messageRequestDTO);
        verify(messageRepository).findLocationMessages(eq(deviceId), anyLong(), anyLong(), eq(Pageable.unpaged()));
    }

    @Test
    public void getLocationMessagesWithCustomDate() throws Exception {
        MessageRequestDTO messageRequestDTO = new MessageRequestDTO();
        String fromDate = "20180101000000";
        String endDate = "20180101235959";

        messageRequestDTO.setFrom(fromDate);
        messageRequestDTO.setTo(endDate);
        messageRequestDTO.setDeviceId(deviceId);


        SimpleDateFormat sdf = new SimpleDateFormat(MessageRequestDTO.DATE_FORMAT);


        deviceService.getLocationMessages(messageRequestDTO);

        Date dateFrom = sdf.parse(fromDate);
        Date dateTo = sdf.parse(endDate);


        verify(messageRepository).findLocationMessages(eq(deviceId), eq(dateFrom.getTime()), eq(dateTo.getTime()), eq(Pageable.unpaged()));
    }

    @Test(expected = InvalidDateRangeException.class)
    public void getLastMessagesWithInvalidDateRange_EndDateBeforeFrom() throws Exception {
        MessageRequestDTO messageRequestDTO = new MessageRequestDTO();
        String fromDate = "20180101000000";
        String endDate = "20170101235959";

        messageRequestDTO.setFrom(fromDate);
        messageRequestDTO.setTo(endDate);
        messageRequestDTO.setDeviceId(deviceId);

        deviceService.getLocationMessages(messageRequestDTO);

    }

    @Test(expected = InvalidDateRangeException.class)
    public void getLastMessagesWithInvalidDate_DatesAreEqual() throws Exception {
        MessageRequestDTO messageRequestDTO = new MessageRequestDTO();
        String fromDate = "20180101000000";
        String endDate = "20180101000000";

        messageRequestDTO.setFrom(fromDate);
        messageRequestDTO.setTo(endDate);
        messageRequestDTO.setDeviceId(deviceId);

        deviceService.getLocationMessages(messageRequestDTO);
    }

    @Test
    public void getLastMessagesWithLimit() throws Exception {
        MessageRequestDTO messageRequestDTO = new MessageRequestDTO();
        messageRequestDTO.setDeviceId(deviceId);

        messageRequestDTO.setLimit(100);
        messageRequestDTO.setSortDirection("asc");

        Sort sort = Sort.by(Sort.Direction.ASC, DeviceMessageRepository.FIELD_DATETIME);
        PageRequest expectedPageRequest = PageRequest.of(0, 100, sort);

        deviceService.getLocationMessages(messageRequestDTO);

        verify(messageRepository).findLocationMessages(eq(deviceId), anyLong(), anyLong(), eq(expectedPageRequest));
    }

}