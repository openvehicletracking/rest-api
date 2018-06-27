package com.openvehicletracking.restapi.config;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.openvehicletracking.restapi.repository")
@PropertySource("file:${CONFIG_PATH}/mongodb.properties")
public class MongoConfig extends AbstractMongoConfiguration {

    private final Environment environment;

    @Autowired
    public MongoConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        ServerAddress serverAddress = new ServerAddress(environment.getRequiredProperty("mongodb.host"), Integer.parseInt(environment.getRequiredProperty("mongodb.port")));
        return new MongoClient(serverAddress);
    }

    @Override
    protected String getDatabaseName() {
        return environment.getRequiredProperty("mongodb.database");
    }

}
