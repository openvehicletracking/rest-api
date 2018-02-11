package com.openvehicletracking.restapi.repository;

import com.openvehicletracking.restapi.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);
}
