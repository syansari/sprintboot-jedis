package com.ansari.movies.controller;

import com.ansari.movies.httpclient.MovieClient;
import com.ansari.movies.model.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/movie")
public class MovieController {

  MovieClient movieClient;

  public MovieController(MovieClient movieClient) {

    Objects.requireNonNull(movieClient, "movieClient must not be null");
    this.movieClient = movieClient;
  }

  @GetMapping(path = "{name}")
  public Movie fetchMovie(@PathVariable("name") String name) {

    Objects.requireNonNull(name, "name must not be null");
    return movieClient.getMovie(name);
  }
}
