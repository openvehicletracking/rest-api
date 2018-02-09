package com.openvehicletracking.restapi.repository;

import com.openvehicletracking.restapi.model.DeviceMessage;
import org.springframework.data.repository.CrudRepository;

public interface DeviceMessageRepository extends CrudRepository<DeviceMessage, String> {


}
