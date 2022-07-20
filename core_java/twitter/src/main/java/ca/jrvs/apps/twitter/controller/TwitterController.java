package ca.jrvs.apps.twitter.controller;

import static ca.jrvs.apps.twitter.dao.helper.TwitterUtils.buildTweet;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import java.util.List;
import org.springframework.util.StringUtils;

public class TwitterController implements Controller {

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  public TwitterController(Service service) {
    this.service = service;
  }

  @Override
  public Tweet postTweet(String[] args) {

    if (args.length > 3) {
      throw new IllegalArgumentException(
          "Usage: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }

    String tweetText = args[1];
    String coordinates = args[2];
    String[] coordinateArray = coordinates.split(COORD_SEP);

    if (coordinateArray.length != 2 || StringUtils.isEmpty(tweetText)) {
      throw new IllegalArgumentException(
          "Invalid format. Usage: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }

    Double lat = null;
    Double lon = null;

    try {
      lat = Double.parseDouble(coordinateArray[0]);
      lon = Double.parseDouble(coordinateArray[1]);
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Invalid location format. Usage: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    }

    Tweet postTweet = buildTweet(tweetText, lon, lat);
    return service.postTweet(postTweet);
  }

  @Override
  public Tweet showTweet(String[] args) {

    if (args.length > 2) {
      throw new IllegalArgumentException("Usage: TwitterCLIApp show tweetId");
    }

    String tweetId = args[1];
    return service.showTweet(tweetId, null);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if (args.length > 2) {
      throw new IllegalArgumentException("Usage: TwitterCLIApp delete [tweetId1,tweetId2,...]");
    }

    String ids = args[1];
    String[] idArray = ids.split(COMMA);

    if (idArray.length == 0) {
      throw new IllegalArgumentException(
          "Invalid format. Usage: TwitterCLIApp delete [tweetId1,tweetId2,...]");
    }

    return service.deleteTweets(idArray);

  }
}
