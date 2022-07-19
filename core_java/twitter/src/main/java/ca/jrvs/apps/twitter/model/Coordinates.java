package ca.jrvs.apps.twitter.model;

import java.util.ArrayList;

public class Coordinates {

  private ArrayList<Double> coordinates = new ArrayList<Double>();
  private String type;

  public Coordinates() {
    super();
  }

  public Coordinates(double latitude, double longitude) {
    this.coordinates.add(latitude);
    this.coordinates.add(longitude);
    this.type = null;
  }

  public double getLatitude() {
    return coordinates.get(0);
  }

  public double getLongitude() {
    return coordinates.get(1);
  }

  public void setCoordinates(ArrayList<Double> coordinates) {
    this.coordinates = coordinates;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
