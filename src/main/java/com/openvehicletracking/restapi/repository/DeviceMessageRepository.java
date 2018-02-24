package com.openvehicletracking.restapi.repository;

import com.openvehicletracking.restapi.model.DeviceMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

public interface DeviceMessageRepository extends CrudRepository<DeviceMessage, String> {

    String FIELD_DATETIME = "datetime";

    @Query("{ 'device.deviceId': ?0, datetime: { $gte: ?1, $lte: ?2 }, position: { $exists: true }, gpsStatus: 'VALID'}")
    List<DeviceMessage> findLocationMessages(String deviceId, long from, long to, Pageable pageable);
}
