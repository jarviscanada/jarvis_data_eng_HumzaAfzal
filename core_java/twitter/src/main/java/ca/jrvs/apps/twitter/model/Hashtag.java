package ca.jrvs.apps.twitter.model;

public class Hashtag {

  private String text;
  private int[] indicies = new int[2];

  public Hashtag(String text, int startIndex, int endIndex) {
    this.text = text;
    indicies[0] =startIndex;
    indicies[1] = endIndex;
  }

  public String getText() {
    return text;
  }

  public int[] getIndicies() {
    return indicies;
  }
}
