package com.tacs.backend.config;

import com.tacs.backend.domain.Match;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    // se usan los valores por default si no hay que poner los siguientes datos
    // jedisConnectionFactory.setHostName("localhost");
    // jedisConnectionFactory.setConvertPipelineAndTxResults(puerto);
    return new JedisConnectionFactory();
  }

  @Bean
  public RedisTemplate<String, Match> redisTemplate() {
    final RedisTemplate<String, Match> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    return redisTemplate;
  }
}
