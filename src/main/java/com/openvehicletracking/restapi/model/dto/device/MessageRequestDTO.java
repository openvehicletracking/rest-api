package com.openvehicletracking.restapi.model.dto.device;

import com.openvehicletracking.core.GpsStatus;
import com.openvehicletracking.restapi.repository.DeviceMessageRepository;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class MessageRequestDTO {

    private static final String DATE_FORMAT = "yyyyMMddHHmmss";
    private final ThreadLocal<SimpleDateFormat> dateFormatter = ThreadLocal.withInitial(() -> new SimpleDateFormat(DATE_FORMAT));

    private String deviceId;
    private Date from;
    private Date to;
    private GpsStatus status;
    private int limit = 0;
    private Sort sortDirection = Sort.by(Sort.Direction.DESC, DeviceMessageRepository.FIELD_DATETIME);

    public Optional<Date> fromDate() {
        return Optional.of(from);
    }

    public void setFrom(String from) {
        try {
            this.from = dateFormatter.get().parse(from);
        } catch (ParseException ignored) { }
    }

    public Optional<Date> toDate() {
        return Optional.of(to);
    }

    public void setTo(String to) {
        try {
            this.to = dateFormatter.get().parse(to);
        } catch (ParseException ignored) { }
    }

    public GpsStatus getStatus() {
        return (status == null) ? GpsStatus.VALID : status;
    }

    public void setStatus(GpsStatus status) {
        this.status = status;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setSortDirection(String direction) {
        try {
            this.sortDirection = Sort.by(direction, DeviceMessageRepository.FIELD_DATETIME);
        } catch (IllegalArgumentException ignored) {}

    }

    public Sort getSortDirection() {
        return sortDirection;
    }
}
