package com.openvehicletracking.restapi.repository;

import com.openvehicletracking.restapi.model.DeviceMessage;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DeviceMessageRepository extends CrudRepository<DeviceMessage, String> {


    @Query("{ 'device.deviceId': ?0 }")
    Iterable<DeviceMessage> findByDeviceId(String deviceId, Sort sort);

}
