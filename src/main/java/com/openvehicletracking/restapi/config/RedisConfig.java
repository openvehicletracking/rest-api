package com.openvehicletracking.restapi.config;


import com.openvehicletracking.restapi.repository.DeviceStateRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@PropertySource("file:${CONFIG_PATH}/redis.properties")
public class RedisConfig {

    private final Environment environment;

    @Autowired
    public RedisConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        String host = environment.getProperty("redis.host", "localhost");
        int port = 6379;

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(jedisConnectionFactory());
    }

    @Bean
    public DeviceStateRedisTemplate deviceStateRedisTemplate() {
        return new DeviceStateRedisTemplate(jedisConnectionFactory());
    }


}
