package com.ansari.movies.model;

import java.io.Serializable;
import java.util.Objects;

public final class Trailer implements Serializable {

  private String id;
  private String link;

  // Getter Methods

  public String getId() {
    return id;
  }

  public String getLink() {
    return link;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Trailer trailer = (Trailer) o;
    return Objects.equals(id, trailer.id) && Objects.equals(link, trailer.link);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, link);
  }
}
