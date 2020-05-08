package com.ansari.movies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ApplicationConfiguration {

  @Value("${app.movies.http.client.thread.count}")
  Integer httpClientThreadPoolCount;

  @Value("${app.movies.http.client.connections.timeout.sec}")
  Integer httpClientConnectTimeoutSec;

  @Value("${redis.hostname}")
  String redisHostname;

  @Value("${redis.port}")
  Integer redisPort;

  @Bean
  ExecutorService httpThreadPool() {

    return Executors.newFixedThreadPool(httpClientThreadPoolCount);
  }

  @Bean
  HttpClient httpClient() throws NoSuchAlgorithmException {

    return HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(httpClientConnectTimeoutSec))
        .sslContext(SSLContext.getDefault())
        .executor(httpThreadPool())
        .build();
  }

  @Bean
  public JedisConnectionFactory jedisconnectionFactory() {


    RedisStandaloneConfiguration redisStandaloneConfiguration =
            new RedisStandaloneConfiguration();

    redisStandaloneConfiguration.setHostName(redisHostname);
    redisStandaloneConfiguration.setPort(redisPort);

    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);

    return jedisConnectionFactory;
  }

  @Bean
  public StringRedisSerializer stringRedisSerializer() {

    return new StringRedisSerializer();
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {

    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisconnectionFactory());

    redisTemplate.setKeySerializer(stringRedisSerializer());
    redisTemplate.setHashKeySerializer(stringRedisSerializer());
    redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

    return redisTemplate;
  }
}
