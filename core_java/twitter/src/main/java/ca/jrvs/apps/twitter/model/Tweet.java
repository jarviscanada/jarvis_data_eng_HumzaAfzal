package ca.jrvs.apps.twitter.model;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Tweet {

  private String createdAt;
  private int id;
  private String idString;
  private String text;
  private Entities entities;
  private Coordinates coordinates;
  private int retweetCount;
  private int favoriteCount;
  private boolean favorited;
  private boolean retweeted;

  public Tweet(String createdAt, int id, String idString, String text, Entities entities,
      Coordinates coordinates, int retweetCount, int favoriteCount, boolean favorited, boolean retweeted) {
    this.createdAt = createdAt;
    this.id = id;
    this.idString = idString;
    this.text = text;
    this.entities = entities;
    this.coordinates = coordinates;
    this.retweetCount = retweetCount;
    this.favoriteCount = favoriteCount;
    this.favorited = favorited;
    this.retweeted = retweeted;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public int getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public Entities getEntities() {
    return entities;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public String getIdString() {
    return idString;
  }

  public int getRetweetCount() {
    return retweetCount;
  }

  public int getFavoriteCount() {
    return favoriteCount;
  }

  public boolean isFavorited() {
    return favorited;
  }

  public boolean isRetweeted() {
    return retweeted;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIdString(String idString) {
    this.idString = idString;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public void setRetweetCount(int retweetCount) {
    this.retweetCount = retweetCount;
  }

  public void setFavoriteCount(int favoriteCount) {
    this.favoriteCount = favoriteCount;
  }

  public void setFavorited(boolean favorited) {
    this.favorited = favorited;
  }

  public void setRetweeted(boolean retweeted) {
    this.retweeted = retweeted;
  }
}
