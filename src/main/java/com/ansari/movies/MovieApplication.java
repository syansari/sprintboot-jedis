package com.ansari.movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO
// 1. Cache the Json fields into Redis
// 2. Subsequent call to get the photos and cache them to Redis
// 3. Create Rest API endpoints.
// 4. Expose the API to internal LAN network.
// 5. Package and deploy the app using docker

@SpringBootApplication
public class MovieApplication {

  public static void main(String[] args) {

    SpringApplication.run(MovieApplication.class);
  }
}
