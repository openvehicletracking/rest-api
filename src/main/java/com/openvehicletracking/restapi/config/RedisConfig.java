package com.openvehicletracking.restapi.config;


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
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(environment.getProperty("redis.host", "localhost"), Integer.parseInt(environment.getProperty("redis.port", "6379")));
        return new JedisConnectionFactory(config);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(jedisConnectionFactory());
    }

}
