package ca.jrvs.apps.twitter.model;

import java.util.List;

public class Entities {

  private List<Hashtag> hashtags;
  private List<UserMention> userMentions;

  public Entities(List<Hashtag> hashtags, List<UserMention> userMentions) {
    this.hashtags = hashtags;
    this.userMentions = userMentions;
  }

  public List<Hashtag> getHashtags() {
    return hashtags;
  }

  public List<UserMention> getUserMentions() {
    return userMentions;
  }
}
