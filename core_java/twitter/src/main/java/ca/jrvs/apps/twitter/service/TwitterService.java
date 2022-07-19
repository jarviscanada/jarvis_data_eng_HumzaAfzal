package ca.jrvs.apps.twitter.service;


import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.List;

public class TwitterService implements Service {

  private CrdDao dao;

  public TwitterService(CrdDao dao) {
    this.dao = dao;
  }

  @Override
  public Tweet postTweet(Tweet tweet) {

    validatePostTweet(tweet);
    return (Tweet) dao.create(tweet);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {

    validateId(id);
    return (Tweet) dao.findById(id);
  }

  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    List<Tweet> tweets = new ArrayList<Tweet>();
    Tweet tweet;

    for (String id : ids) {

      validateId(id);
      tweet = (Tweet) dao.deleteById(id);
      tweets.add(tweet);
    }
    return tweets;
  }

  private void validatePostTweet(Tweet tweet) {

    // check text length
    if (tweet.getText().length() > 140) {
      throw new RuntimeException("Tweet exceeded 140 characters");
    }
    // check if lon and lat are out of range
    if (Math.abs(tweet.getCoordinates().getLatitude()) > 90) {
      throw new RuntimeException("latitude exceeds bounds");
    }
    if (Math.abs(tweet.getCoordinates().getLongitude()) > 180) {
      throw new RuntimeException("longitude exceeds bounds");
    }
  }

  private void validateId(String id) {
    // check if id is in correct format
    try {
      double d = Double.parseDouble(id);
    } catch (NumberFormatException nfe) {
      throw new RuntimeException("id not correct format");
    }
  }

}
