package com.openvehicletracking.restapi.repository;

import com.openvehicletracking.restapi.model.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RouteRepository extends MongoRepository<Route, String> {

    Route findByDeviceId(String deviceId);
}
