package com.openvehicletracking.restapi.repository;

import com.openvehicletracking.restapi.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account, String> {

    List<Account> findAccountsByOwner(String owner);

}
