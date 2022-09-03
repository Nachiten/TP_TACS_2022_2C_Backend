package com.tacs.backend.config;

import com.tacs.backend.domain.Match;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

  @Value("${spring.redis.host}")
  private String REDIS_HOST;

  @Value("${spring.redis.port}")
  private Integer REDIS_PORT;

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    // JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(REDIS_HOST,
    // REDIS_PORT);

    // se usan los valores por default si no hay que poner los siguientes datos
    //    jedisConnectionFactory.setHostName("localhost");
    //    jedisConnectionFactory.setConvertPipelineAndTxResults(puerto);

    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(REDIS_HOST, REDIS_PORT);

    return new JedisConnectionFactory(config);
  }

  @Bean
  public RedisTemplate<String, Match> redisTemplate() {
    final RedisTemplate<String, Match> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    return redisTemplate;
  }
}
