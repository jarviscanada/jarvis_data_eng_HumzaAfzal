package ca.jrvs.apps.twitter.model;

public class Coordinates {

  private float longitude;
  private float latitude;
  private String type;

  public Coordinates(float longitude, float latitude, String type) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.type = type;
  }

  public float getLongitude() {
    return longitude;
  }

  public float getLatitude() {
    return latitude;
  }

  public String getType() {
    return type;
  }
}
