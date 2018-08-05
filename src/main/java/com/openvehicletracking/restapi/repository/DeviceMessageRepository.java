package com.openvehicletracking.restapi.repository;

import com.openvehicletracking.core.GpsStatus;
import com.openvehicletracking.restapi.model.DeviceMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import java.util.List;

public interface DeviceMessageRepository extends MongoRepository<DeviceMessage, String> {

    String FIELD_DATETIME = "datetime";

    @Query("{ 'device.deviceId': ?0, datetime: { $gte: ?1, $lte: ?2 }, position: { $exists: true }, $or: [{ gpsStatus: 'VALID' }, { gpsStatus: { $exists: false } }]  }")
    List<DeviceMessage> findValidLocationMessagesByDateRange(String deviceId, long from, long to, Pageable pageable);

    @Query("{ 'device.deviceId': ?0, position: { $exists: true }, gpsStatus: 'VALID'}")
    List<DeviceMessage> findValidLocationMessages(String deviceId, Pageable pageable);

    @Query("{ 'device.deviceId': ?0, position: { $exists: true }, gpsStatus: '?1'}")
    List<DeviceMessage> findLocationMessages(String deviceId, GpsStatus gpsStatus, Pageable pageable);

    @Query("{ 'device.deviceId': ?0, datetime: { $gte: ?1, $lte: ?2 }, position: { $exists: true }, gpsStatus: '?3'}")
    List<DeviceMessage> findLocationMessages(String deviceId, long from, long to, GpsStatus gpsStatus, Pageable pageable);

}
