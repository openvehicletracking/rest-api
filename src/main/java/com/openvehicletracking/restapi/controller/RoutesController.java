package com.openvehicletracking.restapi.controller;


import com.openvehicletracking.restapi.model.Route;
import com.openvehicletracking.restapi.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routes")
public class RoutesController {

    private RouteRepository routeRepository;
    private MongoTemplate mongoTemplate;

    @Autowired
    public RoutesController(RouteRepository routeRepository, MongoTemplate mongoTemplate) {
        this.routeRepository = routeRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{deviceId}")
    @PreAuthorize("@deviceAuthorityChecker.check(authentication.authorities, #deviceId)")
    public Route getRoutes(@PathVariable String deviceId) {
        return routeRepository.findByDeviceId(deviceId);
    }


    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("@deviceAuthorityChecker.check(authentication.authorities, #route.deviceId)")
    public Route save(@RequestBody Route route) {
        Query query = new Query(Criteria.where("deviceId").is(route.getDeviceId()));

        Update update = new Update();
        update.set("deviceId", route.getDeviceId());
        update.set("accounts", route.getAccounts());
        update.set("route", route.getRoute());

        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true).upsert(true), Route.class);
    }
}
