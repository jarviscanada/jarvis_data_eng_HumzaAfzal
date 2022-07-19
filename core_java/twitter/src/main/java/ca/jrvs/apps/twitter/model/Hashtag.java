package ca.jrvs.apps.twitter.model;

public class Hashtag {

  private String text;
  private int[] indicies = new int[2];

  public Hashtag() {
    super();
  }

  public Hashtag(String text, int startIndex, int endIndex) {
    this.text = text;
    indicies[0] = startIndex;
    indicies[1] = endIndex;
  }

  public String getText() {
    return text;
  }

  public int[] getIndicies() {
    return indicies;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setIndicies(int[] indicies) {
    this.indicies = indicies;
  }
}
