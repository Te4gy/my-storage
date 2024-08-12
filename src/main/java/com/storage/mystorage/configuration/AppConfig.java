package com.storage.mystorage.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableScheduling
@EnableAsync
public class AppConfig {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setJmxEnabled(false);
        poolConfig.setMaxTotal(20);    // Максимальное количество соединений
        poolConfig.setMaxIdle(10);     // Максимальное количество простаивающих соединений
        poolConfig.setMinIdle(2);      // Минимальное количество простаивающих соединений
        poolConfig.setMaxWaitMillis(2000); // Время ожидания соединения
        poolConfig.setTestOnBorrow(true); // Проверка подключения при получении из пула
        poolConfig.setTestOnReturn(true);
        return new JedisPool(poolConfig, host, port);
    }

//    @Bean
//    @Scope("prototype")
//    Jedis jedis(){
//        return new Jedis(host, port);
//    }

}
