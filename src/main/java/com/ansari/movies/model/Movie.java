package com.ansari.movies.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie implements Serializable {

  private String id;
  private String title;
  private String year;
  private String length;
  private String rating;
  private String rating_votes;
  private String poster;
  private String plot;

  Trailer TrailerObject;

  ArrayList<Object> cast = new ArrayList<Object>();

  ArrayList<Object> technical_specs = new ArrayList<Object>();

  // Getter Methods

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getYear() {
    return year;
  }

  public String getLength() {
    return length;
  }

  public String getRating() {
    return rating;
  }

  public String getRating_votes() {
    return rating_votes;
  }

  public String getPoster() {
    return poster;
  }

  public String getPlot() {
    return plot;
  }

  public Trailer getTrailer() {
    return TrailerObject;
  }

  // Setter Methods

  public void setId(String id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setYear(String year) {
    this.year = year;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public void setRating_votes(String rating_votes) {
    this.rating_votes = rating_votes;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public void setPlot(String plot) {
    this.plot = plot;
  }

  public void setTrailer(Trailer trailerObject) {
    this.TrailerObject = trailerObject;
  }
}
