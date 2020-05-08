package com.ansari.movies.utils;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Objects;


public final class HttpClientUtils {

  public static HttpRequest getHttpRequest(String partialUri, String apiKey, String movieName) {

    Objects.requireNonNull(partialUri, "partialUri must not be null");
    Objects.requireNonNull(apiKey, "apiKey must not be null");
    Objects.requireNonNull(movieName, "movieName must not be null");

    return HttpRequest.newBuilder()
        .GET()
        .setHeader("User-Agent", "HttpClient Bot")
        .setHeader("Accept", "text/json")
        .setHeader("X-RapidAPI-Key", apiKey)
        .uri(URI.create(partialUri + movieName))
        .build();
  }
}
