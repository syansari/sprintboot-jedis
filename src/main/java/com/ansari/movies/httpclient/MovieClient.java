package com.ansari.movies.httpclient;

import com.ansari.movies.model.Movie;
import com.ansari.movies.repository.MovieRepository;
import com.ansari.movies.utils.HttpClientUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Range;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.Objects;

@Component
public final class MovieClient {

  @Value("${app.movies.http.client.key}")
  private String apiKey;

  @Value("${app.movies.partial.uri}")
  private String partialUri;

  private final HttpClient httpClient;
  private final MovieRepository movieRepository;

  private static final Range<Integer> GOOD_STATUS_CODE = Range.closedOpen(200, 300);
  private static final Logger LOGGER = Logger.getLogger(MovieClient.class);

  MovieClient(HttpClient httpClient, MovieRepository movieRepository) {

    this.httpClient = httpClient;
    this.movieRepository = movieRepository;
  }

  public Movie getMovie(String movieName) {

    Objects.requireNonNull(movieName, "movieName must not be null");

    Movie movie = movieRepository.findByName(movieName.toUpperCase().replaceAll("\\s+", "").trim());

    if (!Objects.isNull(movie)) {

      LOGGER.info(String.format("Movie: %s is returned from redis cache", movie.getTitle()));
      return movie;
    }

    try {

      HttpResponse<String> response =
          httpClient.send(
              HttpClientUtils.getHttpRequest(partialUri, apiKey, movieName),
              HttpResponse.BodyHandlers.ofString());

      return handleResponse(response);

    } catch (InterruptedException | IOException ex) {

      LOGGER.error("http request interrupted or IO error", ex);
    }

    return null;
  }

  private Movie handleResponse(HttpResponse<String> httpResponse) {

    var status = httpResponse.statusCode();

    if (GOOD_STATUS_CODE.contains(status)) {

      ObjectMapper mapper = new ObjectMapper();

      try {

        Movie movie = mapper.readValue(httpResponse.body(), Movie.class);
        movieRepository.saveMovie(movie);

        return movie;

      } catch (JsonProcessingException ex) {

        LOGGER.error("Error encountered during Json parsing, ex");
        return null;
      }
    } else {

      LOGGER.error(
          String.format("Invalid response code: %s for uri: %s", status, httpResponse.uri()));
    }

    return null;
  }
}
