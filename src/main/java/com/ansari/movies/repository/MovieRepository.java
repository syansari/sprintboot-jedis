package com.ansari.movies.repository;

import com.ansari.movies.model.Movie;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class MovieRepository {

  private static final String SPACE_OR_NON_ASCII_CHARS = "\\s+|[^\\x00-\\x7F]";
  private static final String HASH_NAME = "MOVIES";
  private static final Logger LOGGER = Logger.getLogger(MovieRepository.class);

  private RedisTemplate<String, Object> redisTemplate;
  private HashOperations hashOperations;

  public MovieRepository(RedisTemplate<String, Object> redisTemplate) {

    Objects.requireNonNull(redisTemplate, "redisTemplate must not be null");

    this.redisTemplate = redisTemplate;
    hashOperations = redisTemplate.opsForHash();
  }

  public void saveMovie(Movie movie) {

    Objects.requireNonNull(movie, "movie must not be null");

    String key = movie.getTitle().toUpperCase().replaceAll(SPACE_OR_NON_ASCII_CHARS, "").trim();

    hashOperations.put(HASH_NAME, key, movie);
    LOGGER.info(String.format("Movie %s successfully added to redis", movie.getTitle()));
  }

  public Movie findByName(String movieKey) {

    Objects.requireNonNull(movieKey, "movieName must not be null");

    return (Movie) hashOperations.get(HASH_NAME, movieKey);
  }
}
